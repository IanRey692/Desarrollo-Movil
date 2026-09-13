import os
import datetime
from functools import wraps
from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt
import jwt

app = Flask(__name__)

# Configuración de BD y Llave Secreta para los Tokens
app.config['SECRET_KEY'] = 'mi_llave_super_secreta_para_jwt'
app.config['SQLALCHEMY_DATABASE_URI'] = 'postgresql://postgres:postgres@postgres:5432/postgres'
app.config['SQLALCHEMY_TRACK_MODIFICATIONS'] = False

db = SQLAlchemy(app)
bcrypt = Bcrypt(app)

# ================= MODELOS =================
class User(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    username = db.Column(db.String(50), unique=True, nullable=False)
    password = db.Column(db.String(255), nullable=False)
    is_admin = db.Column(db.Boolean, default=False)
    alumnos = db.relationship('Alumno', backref='registrador', lazy=True)

class Alumno(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    nombre = db.Column(db.String(100), nullable=False)
    matricula = db.Column(db.String(50), nullable=False)
    inscrito = db.Column(db.Boolean, default=True)
    user_id = db.Column(db.Integer, db.ForeignKey('user.id'), nullable=False)

# Crear tablas
with app.app_context():
    db.create_all()

# ================= SEGURIDAD (MIDDLEWARE) =================
def token_required(f):
    @wraps(f)
    def decorated(*args, **kwargs):
        token = None
        if 'Authorization' in request.headers:
            # Formato esperado: "Bearer <token>"
            token = request.headers['Authorization'].split(" ")[1]
        
        if not token:
            return jsonify({'message': 'Falta el token de sesión'}), 401
        
        try:
            data = jwt.decode(token, app.config['SECRET_KEY'], algorithms=["HS256"])
            current_user = User.query.get(data['user_id'])
        except:
            return jsonify({'message': 'Token inválido o expirado'}), 401
        
        return f(current_user, *args, **kwargs)
    return decorated

# ================= RUTAS DE AUTENTICACIÓN =================
@app.route('/register', methods=['POST'])
def register():
    data = request.get_json()
    if User.query.filter_by(username=data['username']).first():
        return jsonify({'message': 'El usuario ya existe'}), 400
    
    hashed_pw = bcrypt.generate_password_hash(data['password']).decode('utf-8')
    
    # Leer el rol que manda la app de Android (por defecto False si no lo envían)
    es_admin = data.get('is_admin', False)
    
    new_user = User(username=data['username'], password=hashed_pw, is_admin=es_admin)
    
    db.session.add(new_user)
    db.session.commit()
    return jsonify({'message': 'Usuario creado exitosamente'}), 201

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    user = User.query.filter_by(username=data['username']).first()
    
    if user and bcrypt.check_password_hash(user.password, data['password']):
        # Validar que el rol coincida con el portal desde el que intenta entrar
        requested_is_admin = data.get('is_admin', False)
        if user.is_admin != requested_is_admin:
            tipo_real = "profesor" if user.is_admin else "alumno"
            return jsonify({'status': 'error', 'message': f'Acceso denegado: Esta cuenta pertenece al portal de {tipo_real}'}), 403
        
        token = jwt.encode({
            'user_id': user.id,
            'is_admin': user.is_admin,
            'exp': datetime.datetime.utcnow() + datetime.timedelta(hours=24)
        }, app.config['SECRET_KEY'], algorithm="HS256")
        
        return jsonify({
            'status': 'success',
            'message': 'Login exitoso',
            'token': token,
            'username': user.username,
            'is_admin': user.is_admin
        }), 200
        
    return jsonify({'status': 'error', 'message': 'Credenciales inválidas'}), 401




# ================= RUTAS CRUD (PROTEGIDAS) =================
@app.route('/alumnos', methods=['POST'])
@token_required
def crear_alumno(current_user):
    if not current_user.is_admin:
        existente = Alumno.query.filter_by(user_id=current_user.id).first()
        if existente:
            return jsonify({'message': 'Ya tienes un perfil de alumno registrado'}), 400
            
    data = request.get_json()
    
    # Validar si la matrícula ya existe en la base de datos
    if Alumno.query.filter_by(matricula=data['matricula']).first():
        return jsonify({'message': 'La matrícula ya se encuentra registrada'}), 400

    nuevo = Alumno(
        nombre=data['nombre'],
        matricula=data['matricula'],
        inscrito=data.get('inscrito', True),
        user_id=current_user.id
    )
    db.session.add(nuevo)
    db.session.commit()
    return jsonify({'message': 'Registro creado exitosamente'}), 201

@app.route('/alumnos', methods=['GET'])
@token_required
def leer_alumnos(current_user):
    if current_user.is_admin:
        alumnos = Alumno.query.all()
    else:
        alumnos = Alumno.query.filter_by(user_id=current_user.id).all()
        
    output = []
    for a in alumnos:
        output.append({
            'id': a.id,
            'nombre': a.nombre,
            'matricula': a.matricula,
            'inscrito': a.inscrito,
            'owner_id': a.user_id
        })
    return jsonify({'alumnos': output}), 200

@app.route('/alumnos/<int:id>', methods=['PUT'])
@token_required
def actualizar_alumno(current_user, id):
    alumno = Alumno.query.get_or_404(id)
    if not current_user.is_admin and alumno.user_id != current_user.id:
        return jsonify({'message': 'No tienes permiso'}), 403
        
    data = request.get_json()
    nueva_matricula = data.get('matricula', alumno.matricula)
    
    # Validar si la nueva matrícula ya pertenece a otro alumno
    duplicado = Alumno.query.filter_by(matricula=nueva_matricula).first()
    if duplicado and duplicado.id != id:
        return jsonify({'message': 'La matrícula ya pertenece a otro registro'}), 400

    alumno.nombre = data.get('nombre', alumno.nombre)
    alumno.matricula = nueva_matricula
    alumno.inscrito = data.get('inscrito', alumno.inscrito)
    
    db.session.commit()
    return jsonify({'message': 'Alumno actualizado'}), 200


@app.route('/alumnos/<int:id>', methods=['DELETE'])
@token_required
def borrar_alumno(current_user, id):
    # Bloquear la eliminación a los alumnos
    if not current_user.is_admin:
        return jsonify({'message': 'Solo el profesor puede borrar registros'}), 403
        
    alumno = Alumno.query.get_or_404(id)
    db.session.delete(alumno)
    db.session.commit()
    return jsonify({'message': 'Registro eliminado'}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
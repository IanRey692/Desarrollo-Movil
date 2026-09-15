import os
import datetime
from functools import wraps
from flask import Flask, request, jsonify
from flask_sqlalchemy import SQLAlchemy
from flask_bcrypt import Bcrypt
import jwt

app = Flask(__name__)

# Configuración de BD y Llave para los Tokens
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
    juegos = db.relationship('Videojuego', backref='vendedor', lazy=True)

class Videojuego(db.Model):
    id = db.Column(db.Integer, primary_key=True)
    titulo = db.Column(db.String(100), nullable=False)
    plataforma = db.Column(db.String(50), nullable=False)
    precio = db.Column(db.Float, nullable=False)
    descripcion = db.Column(db.String(100), nullable=False)
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
    
    new_user = User(username=data['username'], password=hashed_pw, is_admin=False)
    
    db.session.add(new_user)
    db.session.commit()
    return jsonify({'message': 'Usuario creado exitosamente'}), 201

@app.route('/login', methods=['POST'])
def login():
    data = request.get_json()
    user = User.query.filter_by(username=data['username']).first()
    
    if user and bcrypt.check_password_hash(user.password, data['password']):
        # El servidor ya no te exige que le digas qué rol eres, él solito lo detecta
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
            'is_admin': user.is_admin # Aquí le avisa a Android tu rol real
        }), 200
        
    return jsonify({'status': 'error', 'message': 'Credenciales inválidas'}), 401

# ================= RUTAS CRUD (PROTEGIDAS) =================
@app.route('/juegos', methods=['GET'])
@token_required
def leer_juegos(current_user):
    juegos = Videojuego.query.all()
    output = []
    for j in juegos:
        output.append({
            'id': j.id,
            'titulo': j.titulo,
            'plataforma': j.plataforma,
            'precio': j.precio,
            'descripcion': j.descripcion,
            'vendedor_nombre': j.vendedor.username if j.vendedor else 'Desconocido' # <--- Agregamos esto
        })
    return jsonify({'juegos': output}), 200

@app.route('/juegos', methods=['POST'])
@token_required
def crear_juego(current_user):
    if not current_user.is_admin:
        return jsonify({'message': 'Solo el administrador puede agregar juegos a la tienda'}), 403
        
    data = request.get_json()
    nuevo = Videojuego(
        titulo=data['titulo'],
        plataforma=data['plataforma'],
        precio=float(data.get('precio', 0.0)),
        descripcion=data['descripcion'],
        user_id=current_user.id
    )
    db.session.add(nuevo)
    db.session.commit()
    return jsonify({'message': 'Juego agregado al catálogo'}), 201

@app.route('/juegos/<int:id>', methods=['PUT'])
@token_required
def actualizar_juego(current_user, id):
    if not current_user.is_admin:
        return jsonify({'message': 'No tienes permiso para editar el catálogo'}), 403
        
    juego = Videojuego.query.get_or_404(id)
    data = request.get_json()
    
    juego.titulo = data.get('titulo', juego.titulo)
    juego.plataforma = data.get('plataforma', juego.plataforma)
    juego.descripcion = data.get('descripcion', juego.descripcion)
    if 'precio' in data:
        juego.precio = float(data['precio'])
    
    db.session.commit()
    return jsonify({'message': 'Juego actualizado'}), 200

@app.route('/juegos/<int:id>', methods=['DELETE'])
@token_required
def borrar_juego(current_user, id):
    if not current_user.is_admin:
        return jsonify({'message': 'No tienes permiso para borrar juegos'}), 403
        
    juego = Videojuego.query.get_or_404(id)
    db.session.delete(juego)
    db.session.commit()
    return jsonify({'message': 'Juego eliminado'}), 200

if __name__ == '__main__':
    app.run(host='0.0.0.0', port=5000, debug=True)
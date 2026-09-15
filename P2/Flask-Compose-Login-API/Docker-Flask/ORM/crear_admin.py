from app import app, db, User, bcrypt

with app.app_context():
    usuario_admin = 'REYI'
    password_admin = '190905'
    
    # Verificamos que no exista ya para no duplicarlo
    existente = User.query.filter_by(username=usuario_admin).first()
    if existente:
        print(f"El usuario '{usuario_admin}' ya existe.")
    else:
        hashed_pw = bcrypt.generate_password_hash(password_admin).decode('utf-8')
        nuevo_admin = User(username=usuario_admin, password=hashed_pw, is_admin=True)
        db.session.add(nuevo_admin)
        db.session.commit()
        print(f"¡Cuenta de Administrador '{usuario_admin}' creada con éxito!")
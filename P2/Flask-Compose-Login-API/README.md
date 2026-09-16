# Flask Login API + Android Client

Repositorio con dos componentes principales:

-  **Backend REST** construido con **Flask**, dockerizado y con autenticación JWT.
-  **App Android** nativa en **Kotlin + Jetpack Compose** que consume la API.

---

## Backend — `Docker-Flask/ORM/`

API REST para **registro**, **inicio de sesión** y operaciones **CRUD** de videojuegos, con autenticación basada en tokens **JWT**.

| Tecnología | Uso |
|---|---|
| Flask-SQLAlchemy | ORM sobre PostgreSQL |
| PostgreSQL | Persistencia vía Docker Compose |
| Flask-Bcrypt | Hasheo seguro de contraseñas |
| PyJWT | Emisión y verificación de tokens |

### Endpoints

| Método | Ruta | Descripción | Acceso |
|---|---|---|---|
| `GET` | `/` | Verifica que la API está activa | Público |
| `POST` | `/register` | Registra un nuevo usuario cliente | Público |
| `POST` | `/login` | Autentica un usuario y retorna token JWT con rol | Público |
| `GET` | `/juegos` | Lista completa de videojuegos |  JWT |
| `POST` | `/juegos` | Crea un nuevo videojuego |  Admin / Vendedor |
| `PUT` | `/juegos/{id}` | Actualiza un videojuego existente |  Admin / Vendedor |
| `DELETE` | `/juegos/{id}` | Elimina un videojuego del catálogo |  Admin / Vendedor |

### Ejemplos de uso

#### `POST /register`

```json
// Request
{
  "username": "alice",
  "password": "secreto123"
}
```

```json
// Response 201 - Registro exitoso
{ "message": "Usuario creado exitosamente" }
```

```json
// Response 400 - Usuario duplicado
{ "message": "El usuario ya existe" }
```

#### `POST /login`

```json
// Request
{
  "username": "alice",
  "password": "secreto123"
}
```

```json
// Response 200 - Login exitoso
{
  "status": "success",
  "message": "Login exitoso",
  "token": "eyJhbGciOi...",
  "username": "alice",
  "is_admin": false
}
```

```json
// Response 401 - Credenciales inválidas
{ "status": "error", "message": "Credenciales inválidas" }
```

### Levantar con Docker

```bash
cd Docker-Flask/ORM
docker compose up --build
```

El servicio queda disponible en **`http://localhost:5000`**. Utiliza un contenedor de **PostgreSQL** con bind mounts locales para asegurar la persistencia de la base de datos.

> 💡 **Crear cuenta de administrador inicial:**
> ```bash
> docker-compose exec web python crear_admin.py
> ```

### Probar con curl

```bash
# Verificar que la API está activa
curl http://localhost:5000/

# Registrar un nuevo usuario
curl -X POST http://localhost:5000/register \
     -H "Content-Type: application/json" \
     -d "{\"username\":\"android_dev\",\"password\":\"mi_password_secreto\"}"

# Iniciar sesión
curl -X POST http://localhost:5000/login \
     -H "Content-Type: application/json" \
     -d "{\"username\":\"android_dev\",\"password\":\"mi_password_secreto\"}"
```

### Stack tecnológico

-  Python 3.10
-  Flask · Flask-SQLAlchemy · Flask-Bcrypt · PyJWT
-  PostgreSQL (contenedor Docker con persistencia local)
-  Docker Compose

---

##  App Android — `Android/FlaskLogin/`

Aplicación móvil nativa desarrollada con **Kotlin** y **Jetpack Compose** (Material 3). Incluye paleta de colores guinda, navegación completa, diseño responsivo y consumo de API mediante **Retrofit**.

**Configuración del proyecto:**

| Parámetro | Valor |
|---|---|
| `minSdk` | 24 |
| `targetSdk` | 36 |

### Conectar al backend desde el emulador

> ⚠️ El emulador AVD **no resuelve `localhost`** de la máquina host.

| Entorno | URL a usar |
|---|---|
| Emulador AVD | `http://10.0.2.2:5000` |
| Dispositivo físico (misma red) | IP local de tu PC, ej. `http://192.168.1.X:5000` |

**1. Agrega el permiso de Internet en `AndroidManifest.xml`:**

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

**2. Habilita tráfico HTTP en desarrollo dentro de `<application>`:**

```xml
android:usesCleartextTraffic="true"
```

---

## 🛠️ Actualizaciones realizadas

- **Migración de base de datos**
  Se reemplazó SQLite por un contenedor de **PostgreSQL** gestionado mediante Docker Compose, con persistencia de datos local.

- **Sistema de roles y JWT**
  Implementación de tokens de sesión con tiempo de expiración y detección automática de roles (**Cliente** / **Vendedor**) desde el backend.

- **CRUD**
  Conexión de las operaciones para listar, crear, modificar y eliminar videojuegos, restringidas al perfil de administrador.

- **Mejoras de interfaz y experiencia de usuario (UI/UX)**
  - Pantalla de bienvenida con logotipo y botones directos de acceso.
  - Validaciones estrictas de campos en formularios 
  - Control de navegación y cierre de sesión mediante alertas de confirmación

---



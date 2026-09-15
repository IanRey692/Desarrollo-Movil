# Práctica 2: Aplicación móvil básica para operaciones CRUD con un servicio REST

* Instituto Politécnico Nacional
* Escuela Superior de Cómputo (ESCOM)
* Ingeniería en Sistemas Computacionales (Plan 2020)
* Desarrollo de Aplicaciones Móviles Nativas
* Ian Gael Reyna Mendoza

---

## **1. Introducción**
El objetivo de este proyecto es el desarrollo de una aplicación móvil nativa en Android utilizando **Jetpack Compose**, conectada a un backend robusto basado en **Python (Flask)** y **PostgreSQL**, ambos dirigidos mediante **Docker Compose**. 

La aplicación implementa un sistema de control de acceso seguro mediante tokens **JWT (JSON Web Tokens)** y encriptación de contraseñas con **Flask-Bcrypt**. Cuenta con un diseño responsivo enfocado en la experiencia de usuario (UX), diferenciación dinámica de roles (Cliente / Vendedor) y validaciones en formularios.

### **Stack Tecnológico**
* **Backend:** Python 3.10, Flask, Flask-SQLAlchemy (ORM), Flask-Bcrypt, PyJWT.
* **Base de Datos:** PostgreSQL (Contenedor Docker con persistencia mediante volumen de directorios locales).
* **Frontend móvil:** Kotlin, Jetpack Compose, Retrofit (Cliente HTTP), Coroutines.
* **Herramientas de entorno:** Docker y Docker Compose.

---

## **2. Desarrollo y Conceptos Clave**

### Conceptos Fundamentales
* **Docker:** Es una plataforma que permite guardar una aplicación junto con todo lo que necesita para funcionar dentro de un contenedor. Esto hace que la aplicación pueda ejecutarse de la misma manera en diferentes equipos.

* **Imagen y contenedor:** La imagen funciona como una plantilla que contiene todo lo necesario para crear la aplicación. El contenedor es la aplicación funcionando a partir de esa imagen. Los datos importantes, como los de la base de datos, se guardan mediante volúmenes para que no se pierdan cuando el contenedor se detiene o se elimina.

* **Docker Compose:** Es una herramienta que permite configurar y ejecutar varios contenedores al mismo tiempo. La configuración se guarda en un archivo llamado `docker-compose.yml`, por lo que toda la aplicación y sus servicios pueden iniciarse con un solo comando.

* **Servicio REST y ORM:** El backend permite comunicarse con la aplicación mediante diferentes rutas HTTP, como GET, POST, PUT y DELETE, y la información se envía normalmente en formato JSON. SQLAlchemy es un ORM que facilita trabajar con la base de datos PostgreSQL desde Python, ya que permite manejar las tablas como objetos sin tener que escribir directamente todas las consultas SQL.

---

## **3. Documentación de Endpoints del Backend**

Todos los endpoints protegidos requieren el encabezado HTTP: `Authorization: Bearer <TOKEN>`.

| Método | Ruta | Descripción | Restricción / Auth |
| :--- | :--- | :--- | :--- |
| **POST** | `/register` | Registra un nuevo usuario cliente con contraseña cifrada. | Público (Fuerza `is_admin = false`) |
| **POST** | `/login` | Inicia sesión, valida credenciales y retorna un token JWT con el rol correspondiente. | Público |
| **GET** | `/juegos` | Obtiene la lista completa de videojuegos del catálogo y su respectivo vendedor. | Requiere Token JWT |
| **POST** | `/juegos` | Agrega un nuevo videojuego al inventario. | Exclusivo para Administrador / Vendedor |
| **PUT** | `/juegos/{id}` | Actualiza la información de un videojuego existente. | Exclusivo para Administrador / Vendedor |
| **DELETE** | `/juegos/{id}` | Elimina un videojuego del catálogo. | Exclusivo para Administrador / Vendedor |

---

## **4. Instrucciones de Instalación y Ejecución**

Para levantar el servicio backend en un equipo limpio que únicamente cuente con Docker instalado:

1. Clonar el repositorio y ubicarse en la carpeta del backend (`Docker-Flask/ORM`).
2. Levantar el entorno utilizando Docker Compose:
   ```bash
   docker compose up --build -d
## **5. Notas**

1. Para crear una cuenta de usuarios con privelegios de administrador (vendedor) iniciales, ejecute el script de utilidad dentro del contenedor:
    ```bash
   docker-compose exec web python crear_admin.py
    El archivo actualmente contiene el admin REYI con contraseña 190905 para pruebas
2. Asegurarse de que el cliente de Retrofit apunta a la direcciónd de red correcta del emulador (**http://10.0.2.2:5000/**) y que el archivo AndroidManifest.xml tenga habilitado usesCleartextTraffic="true"

## **6. Evidencias**

1. **Pantalla de Inicio**

![alt text](image.png)

2. **Pantalla de Registro e Iniciar Sesión**

![alt text](image-1.png)
![alt text](image-2.png)

3. **Vista como Vendedor**
**Vista principal del vendedor**

![alt text](image-3.png)

**Agregar un nuevo registro de videojuego**

![alt text](image-4.png)

**Editar registro**

![alt text](image-5.png)

**Eliminar registro**

![alt text](image-6.png)

4. **Vista como Cliente**

![alt text](image-7.png)

## **7. Conclusión**

El desarrollo de esta práctica permitió abarcar los conocimientos sobre arquitecturas cliente-servidor nativas y desacopladas. Uno de los principales retos fue la correcta sincronización de volúmenes en Docker Compose con bases de datos relacionales (PostgreSQL) al modificar esquemas de tablas, lo cual se resolvió aplicando una correcta gestión de contenedores y volúmenes persistentes.

El uso de Jetpack Compose facilitó mucho la construcción de una interfaz fluida, reactiva y estética, cumpliendo con los estándares de seguridad, encriptación de credenciales y manejo de sesiones mediante tokens.
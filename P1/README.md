# Práctica 1: Instalación y Funcionamiento de los Entornos Móviles

Esta práctica tiene como propósito que el estudiante instale, configure y verifique el funcionamiento
del entorno de desarrollo necesario para la construcción de aplicaciones móviles nativas, y que, a partir de ese
entorno, desarrolle y publique en GitHub tres versiones de una aplicación básica empleando Views con XML,
Jetpack Compose y Flutter, comparando los tres enfoques de construcción de interfaces.

## 1. Descripción de Herramientas Instaladas
Para el desarrollo de esta práctica, se configuró el entorno de trabajo bajo Windows 11 utilizando las siguientes tecnologías:
* **Java (JDK - Amazon Corretto):** Entorno de ejecución y compilación indispensable para la plataforma Android y el funcionamiento de Gradle.
* **Apache Maven:** Herramienta de gestión de proyectos utilizada para automatizar la construcción de software.
* **Git:** Sistema de control de versiones distribuido para la gestión del código fuente.
* **Node.js:** Entorno de ejecución de JavaScript utilizado como dependencia de soporte para herramientas móviles.
* **Docker:** Plataforma de contenedores empleada para aislar y simular servicios auxiliares.
* **Flutter SDK:** Framework multiplataforma de Google basado en Dart para el desarrollo de aplicaciones nativas.

## 2. Descripción de los Proyectos Desarrollados
El repositorio contiene tres enfoques distintos para desarrollar una aplicación básica de "Hola Mundo", cumpliendo con mostrar el texto de bienvenida, nombre completo, número de boleta y grupo:
1. **`hola_mundo_xml` (Android Nativo con Views):** Enfoque tradicional basado en contenedores lineales y archivos de diseño XML separados de la lógica.
2. **`hola_mundo_compose` (Android Nativo con Jetpack Compose):** Enfoque moderno y declarativo utilizando componentes composables para construir la interfaz mediante código.
3. **`hola_mundo_flutter` (Flutter):** Enfoque multiplataforma estructurado mediante widgets de Material Design (`MaterialApp`, `Scaffold`, `Column` y `Text`).

## 3. Tabla de Versiones del Entorno

| Herramienta | Versión Instalada | Sistema Operativo |
| :--- | :--- | :--- |
| **Java (JDK)** | OpenJDK 25.0.4.1 (Corretto-25.0.4.8.1) | Windows 11 (build 10.0.26200) |
| **Maven** | Apache Maven 3.9.16 | Windows 11 (build 10.0.26200) |
| **Git** | git version 2.45.1.windows.1 | Windows 11 (build 10.0.26200) |
| **Node.js** | v24.20.0 | Windows 11 (build 10.0.26200) |
| **Docker** | Docker version 29.7.2, build a7dcaa6 | Windows 11 (build 10.0.26200) |
| **Flutter SDK** | Flutter 3.47.2 (Channel stable) | Windows 11 (build 10.0.26200) |

## 4. Instrucciones de Ejecución por Versión

### Versión 1: Android Views (XML)
1. Abrir Android Studio y seleccionar **Open an existing project**, ubicando la carpeta `hola_mundo_xml`.
2. Sincronizar las dependencias de Gradle.
3. Seleccionar un emulador virtual (AVD) o dispositivo físico e iniciar la ejecución haciendo clic en **Run**.

### Versión 2: Jetpack Compose
1. Abrir la carpeta `hola_mundo_compose` en Android Studio.
2. Verificar el archivo `MainActivity.kt` para comprobar la estructura de componentes.
3. Ejecutar la vista previa (*Preview*) o compilar directamente sobre el emulador.

### Versión 3: Flutter
1. Abrir la terminal dentro de la carpeta `hola_mundo_flutter`.
2. Ejecutar el comando para iniciar la compilación y despliegue:
   ```bash
   flutter run
   
## 5. Dificultades Encontradas y Soluciones
* **Advertencias y dependencias de Gradle:** Durante la inicialización y compilación de los proyectos nativos, se presentaron tiempos de espera prolongados debido a la descarga de dependencias del SDK de Android. Se resolvió permitiendo que el gestor concluyera la sincronización en segundo plano y limpiando la caché local del proyecto.
* **Configuración del entorno Flutter en la terminal:** Al intentar ejecutar y compilar por primera vez mediante la interfaz gráfica del IDE, surgieron conflictos de rutas en los archivos base como `main.dart`. Se solucionó utilizando el despliegue directo desde la consola mediante el comando `flutter run` vinculado al dispositivo virtual activo.

## 6. Conclusiones y Hallazgos
* **Sobre la instalación y el entorno:** Contar con un entorno unificado en Windows 11 agiliza el desarrollo móvil, aunque demanda una correcta sincronización y configuración previa de las variables de entorno para evitar fallos de compilación en herramientas críticas como Gradle y el SDK.
* **Comparativa de enfoques (XML vs. Jetpack Compose vs. Flutter):** 
  * El desarrollo tradicional en **Views (XML)** resulta más estructurado pero rígido, obligando a manipular múltiples archivos y referencias por ID para modificaciones sencillas.
  * **Jetpack Compose** moderniza el desarrollo nativo al emplear un diseño declarativo que reduce drásticamente las líneas de código y simplifica la creación de interfaces.
  * **Flutter** destaca fuertemente por su versatilidad multiplataforma y su sistema reactivo de widgets, ofreciendo un flujo sumamente eficiente para estructurar aplicaciones limpias bajo un mismo código base.

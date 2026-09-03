## Evidencias de Configuración y Herramientas

En esta sección se documenta la verificación de las herramientas instaladas en el sistema operativo para el desarrollo de la Práctica 1.

### 1. Tabla de Versiones

| Herramienta | Versión Instalada | Sistema Operativo |
| :--- | :--- | :--- |
| **Java (JDK)** | OpenJDK 25.0.4.1 (Corretto-25.0.4.8.1) | Windows 11 (build 10.0.26200) |
| **Maven** | Apache Maven 3.9.16 | Windows 11 (build 10.0.26200) |
| **Git** | git version 2.45.1.windows.1 | Windows 11 (build 10.0.26200) |
| **Node.js** | v24.20.0 | Windows 11 (build 10.0.26200) |
| **Docker** | Docker version 29.7.2, build a7dcaa6 | Windows 11 (build 10.0.26200) |
| **Flutter SDK** | Flutter 3.47.2 (Channel stable) | Windows 11 (build 10.0.26200) |

---

### 2. Captura de Pantalla de la Terminal

<img width="952" height="603" alt="Captura de pantalla 2026-09-02 221255" src="https://github.com/user-attachments/assets/6d3c1dda-aa2e-40c5-954b-be55643affb3" />

## Evidencias de los Proyectos "Hola Mundo"

Cada versión de la aplicación cumple con mostrar el texto "Hola Mundo"

### 1. Versión 1: Android Nativo con Views (XML)
<img width="1917" height="1020" alt="Captura de pantalla 2026-09-02 192731" src="https://github.com/user-attachments/assets/37033ceb-c802-4f26-87ed-317f2107fb7f" />

### 2. Versión 2: Android Nativo con Jetpack Compose
<img width="1917" height="1020" alt="Captura de pantalla 2026-09-02 194650" src="https://github.com/user-attachments/assets/248dcc1c-25a9-4c6d-9120-7d6a5e28d21b" />

### 3. Versión 3: Flutter
<img width="1917" height="1020" alt="image" src="https://github.com/user-attachments/assets/9a7479c6-0c6a-4e0e-b949-6fdff4dd23e1" />


## Comparativa de Enfoques

* **Facilidad de desarrollo:** Jetpack Compose y Flutter permiten iterar y visualizar cambios de forma mucho más ágil en comparación con el enfoque tradicional de XML, el cual requiere gestionar la separación de la lógica en archivos de diseño y código por separado.
* **Cantidad de código:** Las variantes de Jetpack Compose y Flutter requieren líneas de código considerablemente menores y más limpias gracias a sus paradigmas declarativos, a diferencia de los múltiples layouts y referencias por ID en Views.
* **Diseño de interfaz:** Views (XML) otorga un control estricto de posicionamiento absoluto mediante restricciones, mientras que Compose y Flutter facilitan la jerarquía de elementos mediante componentes anidados y reutilizables.

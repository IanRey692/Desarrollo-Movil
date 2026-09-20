# Casos de Uso

## Herramientas Actuales que Implementan MCP

1. **Google Antigravity:** Es un entorno de desarrollo agéntico que utiliza MCP para integrar modelos de lenguaje directamente con el flujo de trabajo del desarrollador. Utiliza servidores MCP para analizar el código fuente, ejecutar pruebas y navegar por el sistema de archivos del proyecto, permitiendo que el agente colabore activamente en la construcción de software complejo sin requerir intervenciones manuales constantes para la lectura del contexto.
2. **Claude Desktop:** La aplicación de escritorio de Anthropic actúa de forma nativa como un cliente (host) de MCP. Permite conectar el modelo de lenguaje de forma segura con recursos locales, como bases de datos o directorios específicos a través del servidor FS, y con herramientas empresariales externas. Su uso principal es permitir que el modelo analice datos locales y estructure respuestas o informes sin tener que sacar esa información del entorno del usuario.
3. **Cursor:** Es un editor de código (IDE) avanzado impulsado por IA que incorpora soporte para el estándar MCP. Utiliza el protocolo para permitir que los modelos de lenguaje integrados interactúen de forma segura con el sistema de archivos local y el árbol de dependencias del proyecto. Esto sirve para que el modelo pueda buscar referencias entre cientos de archivos, comprender la arquitectura general y proponer refactorizaciones precisas con pleno contexto.

## Edición de repositorios sin carga manual

Antes de la existencia de estas arquitecturas, para que un modelo de lenguaje pudiera analizar un proyecto, el desarrollador debía copiar el código manualmente, pegarlo en un navegador web, esperar la respuesta y luego copiar el resultado de vuelta a su editor.

Las herramientas modernas que implementan MCP eliminan por completo la necesidad de subir archivos manualmente. El proceso funciona de la siguiente manera:

1. El cliente (como Google Antigravity o Cursor) inicia un servidor MCP local (como el de Sistema de Archivos) y le otorga permisos estrictos sobre la carpeta del repositorio.
2. El usuario pide un cambio complejo que abarca varios archivos.
3. El LLM, al procesar la petición, descubre las herramientas del servidor MCP y decide invocar operaciones como `list_directory` y `read_file` para explorar la estructura del proyecto y comprender el código fuente existente de forma autónoma.
4. Tras analizar el contexto, el modelo formula la solución e invoca herramientas como `write_file` o `edit_file` para aplicar los cambios directamente en el disco duro local, editando el repositorio completo "in situ" bajo la supervisión del usuario.
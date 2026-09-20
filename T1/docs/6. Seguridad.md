# Seguridad

La integración de servidores MCP con modelos de lenguaje introduce vectores de ataque que requieren estrategias de mitigación rigurosas. Al otorgarle "manos" a un LLM, los fallos de seguridad tienen consecuencias directas en el sistema operativo local.

## Riesgos Concretos

* **Inyección de instrucciones a través del contenido de un archivo:** También conocido como inyección indirecta de *prompts*. Si un modelo tiene acceso para leer un archivo cuyo contenido fue manipulado por un tercero (por ejemplo, un script descargado de internet), ese archivo puede contener instrucciones ocultas. Al procesarlo, el modelo podría obedecer las instrucciones maliciosas en lugar de las del usuario, ejecutando código o filtrando datos.
* **Acceso a rutas fuera del directorio autorizado:** Si el servidor MCP no está correctamente configurado o sufre una vulnerabilidad de escalamiento de directorios (como el uso de `../`), el modelo podría leer información confidencial ajena al proyecto actual, como claves SSH, tokens de la nube o el historial del navegador.
* **Escritura o borrado no deseados:** Un agente autónomo que sufre de una "alucinación" o interpreta mal una solicitud podría sobrescribir código fuente importante, modificar archivos de configuración del sistema o borrar carpetas enteras de forma accidental o maliciosa.

## Mitigaciones

Para operar de forma segura con servidores MCP, se implementan las siguientes barreras defensivas:

* **Confirmación humana antes de ejecutar:** (Conocido como *Human-in-the-loop*). Los clientes MCP y los entornos agénticos deben exigir que el usuario revise y apruebe manualmente operaciones críticas, como modificaciones masivas o borrado de archivos, antes de que el servidor las ejecute.
* **Alcance limitado a un directorio:** Forzar a nivel de configuración (Roots) que el servidor MCP opere exclusivamente en una carpeta aislada o espacio de trabajo (*workspace*) específico. Cualquier solicitud fuera de esta ruta debe ser rechazada automáticamente por el servidor.
* **Permisos de solo lectura:** Si el caso de uso solo requiere que el modelo analice código o lea documentación, el servidor MCP debe instanciarse con banderas de solo lectura, deshabilitando por completo las herramientas de modificación o borrado.
* **Revisión de lo que el servidor expone:** Auditar de forma constante el catálogo de herramientas y recursos que publica el servidor MCP. Se debe aplicar el principio de privilegios mínimos, exponiendo únicamente las capacidades estrictamente necesarias para la tarea en curso.
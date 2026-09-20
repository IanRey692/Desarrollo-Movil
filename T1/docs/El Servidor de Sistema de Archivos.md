# El Servidor de Sistema de Archivos

El servidor de sistema de archivos (FS) no es una parte del protocolo MCP en sí mismo. MCP es únicamente el estándar de comunicación, mientras que "FS" es solo uno de los servidores MCP de referencia, entre muchos otros posibles que pueden implementarse.

## Herramientas que expone
Este servidor proporciona un catálogo de herramientas diseñadas para operar sobre el almacenamiento local de la máquina. Las operaciones que expone al modelo incluyen:
* Listar el contenido de un directorio.
* Leer el contenido de archivos existentes.
* Escribir o crear archivos nuevos.
* Mover archivos entre rutas.
* Buscar archivos específicos por nombre o contenido.

## Delimitación del alcance y su importancia
El alcance de todas estas herramientas se delimita estrictamente mediante la configuración de **directorios permitidos** al momento de iniciar el servidor. El servidor FS solo aceptará ejecutar operaciones de lectura o escritura si la ruta solicitada cae dentro de estas carpetas autorizadas.

Este límite existe como un mecanismo crítico de seguridad. Sin él, el modelo de lenguaje tendría acceso irrestricto a la raíz del disco duro del usuario y a toda su información. Si el límite no existiera, cualquier error del modelo, alucinación o ataque de inyección de instrucciones (*prompt injection*) podría resultar en la lectura de información sensible (como claves de bases de datos, tokens de sesión o documentos personales), o permitiría la modificación y borrado de archivos críticos del sistema operativo, comprometiendo por completo la integridad de la máquina.
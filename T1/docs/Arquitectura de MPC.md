# Arquitectura de MCP

## El modelo Host / Cliente / Servidor
La arquitectura del Model Context Protocol (MCP) se basa en una topología clara de tres componentes:

1. Host (Anfitrión): Es la aplicación final con la que interactúa el usuario y que integra el modelo de lenguaje (LLM). En el ejemplo de nuestra implementación, el host es Claude Desktop (o el entorno de desarrollo que hayas elegido, como VS Code o Cursor).
2. Cliente MCP: Es el componente (generalmente integrado dentro del Host) encargado de mantener la conexión con los servidores MCP, descubrir sus capacidades y enrutar las peticiones generadas por el modelo. En nuestro caso, el cliente es el motor interno de Claude Desktop.
3. Servidor MCP: Es el programa ligero que expone capacidades específicas (archivos, bases de datos, APIs). En nuestro ejemplo práctico, este rol lo cumple el Servidor de Sistema de Archivos (FS) de MCP, que se ejecuta localmente en nuestra máquina.

## Primitivas del Servidor
Un servidor MCP expone sus capacidades al cliente mediante tres primitivas principales:

* Herramientas (Tools): Son funciones ejecutables que el servidor expone (con nombre, descripción y esquema JSON). Permiten que el modelo realice acciones activas (ej. escribir un archivo, consultar una API externa o ejecutar un script).
* Recursos (Resources): Son fuentes de datos pasivas que el servidor pone a disposición del cliente para dar contexto al modelo (ej. leer el contenido de un archivo de registro local o una tabla de base de datos).
* Plantillas de prompt (Prompts): Son plantillas predefinidas que el servidor ofrece al cliente para ayudar a estructurar instrucciones comunes o estandarizadas, facilitando la interacción del usuario con el modelo.

## Primitivas del Cliente
El cliente MCP también expone primitivas hacia el servidor para controlar el flujo y los límites de la ejecución:

* Roots (Raíces): Definen los límites o el contexto de trabajo. Indican al servidor qué directorios locales o rutas específicas tiene permitidas acceder, acotando su alcance por razones de seguridad.
* Elicitation (Elicitación / Muestreo): Es la capacidad del cliente para interactuar con el modelo o solicitar entradas adicionales del usuario, permitiendo pausas en la ejecución o recopilación de datos antes de continuar con una acción del servidor.

## Transportes
MCP define dos mecanismos de transporte estandarizados para intercambiar mensajes JSON-RPC 2.0 entre el cliente y el servidor:

* `stdio` (Standard Input/Output): Utilizado para servidores locales. El cliente inicia el servidor MCP como un proceso hijo en la misma máquina y se comunican directamente a través de la entrada y salida estándar del sistema operativo.
* Streamable HTTP (Server-Sent Events / SSE): Utilizado para servidores remotos. Permite que el cliente y el servidor se comuniquen a través de la red, manteniendo una conexión HTTP abierta para la transmisión continua de eventos y respuestas.

## Versión de la Especificación
*Nota: La especificación de MCP es un estándar vivo y se actualiza con frecuencia.*
Versión consultada: Especificación oficial de MCP versión 1.0 (revisión consultada en septiembre de 2026).
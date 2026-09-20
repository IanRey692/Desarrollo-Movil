# API vs MCP

## ¿Qué es una API?

Una API (Interfaz de Programación de Aplicaciones) funciona como un contrato estricto y predefinido entre programas.

Su uso requiere un enfoque manual y determinista: la persona que desarrolla lee la documentación técnica, decide qué endpoint específico debe llamar, arma la estructura exacta de la petición —cabeceras, parámetros y cuerpo— y escribe el código que procesará e interpretará la respuesta.

En este paradigma, la lógica es estática; la decisión de qué recurso se llama, cómo se llama y en qué momento preciso se ejecuta queda escrita de antemano y de forma rígida en el código fuente de la aplicación.

---

## ¿Qué es MCP?

El Model Context Protocol (MCP) es un protocolo abierto y estandarizado, basado en JSON-RPC 2.0.

Funciona bajo un paradigma dinámico, donde un servidor publica un catálogo de herramientas, exponiendo:

* El nombre de cada herramienta.
* Su descripción en lenguaje natural.
* El esquema de los parámetros que requiere.

En lugar de tener integraciones codificadas rígidamente, el modelo de lenguaje descubre este catálogo en tiempo de ejecución.

Al procesar el contexto, el propio modelo evalúa las herramientas disponibles y decide de forma autónoma cuál debe invocar según lo que el usuario haya pedido.

---

## Comparación entre API tradicional y MCP

| Característica                               | API Tradicional                                                                                                            | MCP (Model Context Protocol)                                                                                                                            |
| -------------------------------------------- | -------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------------------------------------------------------------- |
| Quién decide qué se invoca               | La persona desarrolladora. La decisión está preprogramada en el código.                                                    | El modelo de lenguaje (LLM). Decide en tiempo de ejecución según la petición del usuario.                                                               |
| Cómo se descubren las capacidades        | Manualmente. El desarrollador debe leer la documentación técnica externa.                                                  | Automáticamente. El servidor expone un catálogo descriptivo que el modelo lee al conectarse.                                                            |
| Acoplamiento cliente-servicio            | Alto. El cliente debe conocer las rutas exactas, métodos HTTP y esquemas específicos del servicio.                     | Bajo. El cliente solo necesita entender el estándar MCP; el catálogo dicta cómo usar las herramientas.                                              |
| Formato de los mensajes                  | Variable (REST, GraphQL, gRPC), usando formatos como JSON, XML o texto plano según cada API.                               | Estrictamente estandarizado bajo el protocolo JSON-RPC 2.0.                                                                                             |
| Manejo de autenticación y consentimiento | Programado en el código (inyección de tokens/API keys). La ejecución suele ser directa y sin pausas.                       | Delega el control al cliente MCP, permitiendo integrar confirmación humana (human-in-the-loop) antes de ejecutar acciones críticas.                 |
| Reutilización entre aplicaciones         | Baja/Media. Integrar un nuevo servicio requiere escribir código específico (SDKs, clientes HTTP) para cada aplicación. | Alta (Universal). Cualquier cliente o entorno que soporte el estándar MCP puede conectarse y usar el servidor sin programar integraciones a medida. |

---

## Relación entre MCP y las APIs

Un servidor MCP casi siempre envuelve una API, una base de datos o un recurso informático ya existente.

MCP funciona estrictamente como una capa de abstracción por encima de la API subyacente.

Su propósito no es ejecutar la lógica de negocio profunda, sino estandarizar la interfaz para que las capacidades de esa API sean:

* Descubribles.
* Comprensibles semánticamente.
* Utilizables de forma segura por un modelo de lenguaje.

De esta manera, MCP permite que un modelo de lenguaje interactúe con capacidades existentes sin que cada aplicación tenga que implementar una integración específica y rígida para cada servicio.

### En términos simples

> Una API define cómo un programa puede comunicarse con otro programa, mientras que MCP estandariza cómo un modelo de lenguaje puede descubrir y utilizar herramientas y recursos externos.

En una integración tradicional, el desarrollador determina previamente qué API utilizar, qué endpoint llamar y cómo procesar la respuesta.

Con MCP, el servidor proporciona al modelo un catálogo descriptivo de las herramientas disponibles, permitiendo que el modelo determine en tiempo de ejecución cuál herramienta puede utilizar para atender la solicitud del usuario.

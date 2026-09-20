# 2. El problema del aislamiento

La transición de los Grandes Modelos de Lenguaje (LLM) independientes a los agentes basados en LLM marca un cambio fundamental en las capacidades de la IA. Si bien los primeros LLM eran principalmente sistemas de entrada de texto y salida de texto, los agentes modernos están diseñados como sistemas integrados con un "cerebro" central (el LLM) conectado a "manos" (herramientas, navegadores web y ejecutores de código). 

Sin embargo, esta expansión de la agencia introduce riesgos de seguridad que van mucho más allá de la moderación de contenido tradicional.

## Razones de arquitectura
Por sí mismo, un LLM es principalmente un sistema de entrada de texto y salida de texto. El modelo funciona como el "cerebro" central, pero para poder interactuar con archivos necesita estar conectado a "manos" (ejecutores de código o acceso al shell). Sin estos ejecutores, el LLM es un proceso aislado que no tiene la autoridad ni el mecanismo arquitectónico para realizar llamadas al sistema operativo de la máquina.

## Razones de seguridad
Incluso si el LLM cuenta con las "manos" para ejecutar código, el "aislamiento" se introduce como un principio de primera clase para la seguridad de los agentes. En este contexto, un fallo no es solo una frase ofensiva; es una secuencia de acciones que podría eliminar un archivo, filtrar datos sensibles o comprometer un dispositivo físico. Los riesgos de romper este aislamiento incluyen:

* **Inyección de solicitudes (Prompt Injection):** Táctica mediante la cual los atacantes manipulan los prompts. Ya sea sobreescribiendo la solicitud del sistema (inyección directa) o controlando archivos externos que el LLM lee (inyección indirecta), el atacante podría explotar los sistemas a los que accede el LLM.
* **Shell y ejecución de procesos:** Darle a un agente acceso al sistema de archivos le otorga la misma autoridad que a tu terminal. Puede instalar software, generar procesos en segundo plano o manipular sutilmente archivos que no están bajo control de versiones.
* **Credenciales y artefactos de identidad:** Si un agente no está aislado y puede leer claves SSH o tokens en la nube de la máquina del desarrollador, puede suplantar su identidad en distintos servicios, incluso mucho después de que finalice la sesión.
* **Exceso de autonomía:** Si un LLM produce resultados inesperados (debido a un ataque o una alucinación de IA), la aplicación podría tomar medidas potencialmente dañinas automáticamente.

Para evitar estos problemas, las organizaciones deben requerir que los humanos autoricen ciertas acciones antes de que se lleven a cabo (manteniendo al usuario en el control), limitar la funcionalidad a los niveles mínimos necesarios (modelo de seguridad Zero Trust) y tratar al LLM como un proceso autónomo con autoridad delegada que requiere aislamiento estricto.
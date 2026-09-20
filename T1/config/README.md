
# Implementación y Análisis de Model Context Protocol (MCP)

## FASE PRÁCTICA: IMPLEMENTACIÓN DEL SERVIDOR

### 1. Elección del Cliente
**Cliente elegido:** Claude Desktop.  
**Justificación:** Se seleccionó Claude Desktop debido a su integración nativa como *Host* de MCP. Su interfaz gráfica de usuario proporciona retroalimentación visual explícita cada vez que el modelo de lenguaje decide invocar una herramienta del catálogo. Esto facilita la trazabilidad de las acciones autónomas del agente y permite capturar evidencias fotográficas claras para cada operación requerida en esta práctica.

### 2. Instalación y Configuración del Servidor de Sistema de Archivos
Se instaló el servidor de referencia `@modelcontextprotocol/server-filesystem` mediante el entorno de ejecución de Node.js (`npx`). 

Para la configuración, se inyectó el siguiente bloque en el archivo `%APPDATA%\Claude\claude_desktop_config.json`

```json
{
  "mcpServers": {
    "filesystem": {
      "command": "npx",
      "args": [
        "-y",
        "@modelcontextprotocol/server-filesystem",
        "C:\\Users\\Ian\\Descargas\\mcp-workspace"
      ]
    }
  }
}

```

**Delimitación del directorio:** Como medida estricta de seguridad, no se utilizó la raíz del disco ni la carpeta de usuario. Se creó un directorio de trabajo aislado específicamente para esta tarea en `C:\Users\Ian\Descargas\mcp-workspace`. Al reiniciar Claude Desktop, el cliente reconoció exitosamente el servidor y habilitó el catálogo de herramientas (evidenciado por el ícono de herramientas en la interfaz).

### 3. Operaciones Demostradas

Se ejecutaron instrucciones en lenguaje natural para que el LLM utilizara las herramientas del servidor.

1. **Listar contenido:** El modelo invocó `list_directory` para explorar los archivos disponibles en el directorio base.

![Listar archivos](../Img/Listar_Archivos.png)

2. **Crear archivo:** El modelo utilizó `write_file` para generar un nuevo archivo llamado `practica.txt` e inyectarle texto inicial.

![Crear archivos](../Img/Crear_Archivos.png)

3. **Leer archivo:** Mediante read_file, el modelo extrajo el contenido exacto del archivo creado hacia su propio contexto. 

![Leer archivos](../Img/Leer_Archivo.png)


4. **Modificar archivo:** El modelo analizó el contenido actual y volvió a utilizar `write_file` (o herramientas de edición) para anexar nuevas líneas de texto al archivo existente.

![Editar archivos](../Img/Editar_Archivos.png)

5. **Buscar archivo:** El modelo invocó herramientas de búsqueda y lectura múltiple para encontrar coincidencias de una cadena de texto específica dentro de los archivos del directorio.

![Buscar archivos](../Img/Buscar_Archivos.png)

### 4. Prueba del Límite de Seguridad

**Acción solicitada:** Se le instruyó explícitamente al modelo leer el contenido de un archivo del sistema operativo que se encontraba fuera del directorio autorizado.

![Leer Fuera](../Img/Leer_Fuera.png)

**Resultado y justificación del mecanismo:** El modelo intentó invocar la herramienta, pero la operación falló y fue bloqueada. El mecanismo que impidió la acción fue el parámetro de directorio raíz (*Root*) establecido en la configuración del servidor local. El protocolo MCP delega la autoridad de acceso al servidor; al detectar que la ruta escapaba de los límites de `mcp-workspace`, rechazó la petición automáticamente. Esto demuestra cómo se garantiza el aislamiento y la seguridad frente a instrucciones externas o fallos del modelo.
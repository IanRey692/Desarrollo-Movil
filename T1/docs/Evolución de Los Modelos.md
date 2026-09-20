# Evolución de los modelos LM

## ¿Qué es un modelo de lenguaje?
Un modelo de lenguaje (LM, por sus siglas en inglés) es un modelo que estima la probabilidad de que un token o una secuencia de tokens aparezcan dentro de una secuencia más larga de tokens. Un token puede ser una palabra, una subpalabra (un subconjunto de una palabra) o incluso un solo carácter.

Por ejemplo:
> *"Cuando oigo llover en mi tejado, ______ en mi cocina."*

Un modelo de lenguaje determina las probabilidades de diferentes tokens o secuencias de tokens para completar ese espacio. Por ejemplo, podría asignar las siguientes probabilidades:

| Probabilidad | Posible continuación |
| :--- | :--- |
| **9.4%** | cocinar sopa |
| **5.2%** | calentar una tetera |
| **3.6%** | acobardarse |
| **2.5%** | siesta |
| **2.2%** | relajarse |

Una aplicación puede utilizar esta tabla de probabilidades para realizar una predicción. La predicción puede consistir en seleccionar el token con mayor probabilidad o elegir aleatoriamente entre aquellos que superen un determinado umbral.

Al modelar los patrones estadísticos de los tokens, los modelos de lenguaje modernos desarrollan representaciones internas del lenguaje que les permiten generar texto coherente y plausible.

### Modelos de lenguaje de n-gramas
Una forma tradicional de construir modelos de lenguaje es mediante los n-gramas. Un n-grama es una secuencia ordenada de palabras o tokens, donde N representa la cantidad de elementos que contiene la secuencia. Por ejemplo, un modelo de bigramas utiliza secuencias de dos palabras, mientras que uno de trigramas utiliza secuencias de tres.

Estos modelos permiten estimar la probabilidad de una palabra considerando las palabras que aparecen antes de ella. Sin embargo, tienen limitaciones para representar relaciones complejas y dependencias de largo alcance dentro del texto.

---

## ¿Qué es un LLM?
Los modelos de lenguaje de gran tamaño (LLM, *Large Language Models*) son un tipo de modelo de lenguaje que utiliza redes neuronales profundas de gran escala y que se preentrena con enormes cantidades de datos. Esto les permite comprender y generar lenguaje natural y realizar una amplia variedad de tareas.

Por lo tanto, un LLM es un tipo de LM, pero no todos los LM son LLM. La diferencia principal está en la escala, la arquitectura utilizada, la cantidad de parámetros, los datos de entrenamiento y los recursos computacionales empleados.

Los LLM modernos suelen utilizar una arquitectura de redes neuronales llamada **Transformer**, que se destaca en el procesamiento de secuencias y en la identificación de relaciones entre diferentes partes del texto. Durante su entrenamiento, los modelos aprenden patrones del lenguaje a partir de grandes cantidades de datos, lo que les permite desarrollar representaciones internas relacionadas con la gramática, diferentes idiomas y diversos tipos de conocimiento.

Los Transformers originales están compuestos por dos partes principales:
* **Codificador (encoder):** convierte el texto de entrada en una representación intermedia que contiene información relevante sobre la secuencia.
* **Decodificador (decoder):** utiliza esa representación para generar una secuencia de salida.

Sin embargo, los LLM actuales no necesariamente utilizan ambas partes. Existen modelos basados principalmente en codificadores, otros en decodificadores y arquitecturas que utilizan ambos componentes.

La arquitectura Transformer permite construir modelos con una gran cantidad de parámetros, llegando algunos a manejar cientos de miles de millones. Estos modelos pueden entrenarse con cantidades masivas de información, incluyendo grandes colecciones de textos disponibles en Internet y otros conjuntos de datos.

Los LLM son resultado de décadas de avances en procesamiento de lenguaje natural (PLN), aprendizaje automático y aprendizaje profundo. Su desarrollo ha permitido crear sistemas capaces de realizar tareas como generación de texto, traducción, resumen, respuesta a preguntas, programación y análisis de información. Actualmente, los LLM son accesibles mediante diferentes aplicaciones y servicios, como ChatGPT de OpenAI, Claude de Anthropic, Copilot de Microsoft, los modelos Llama de Meta y Gemini de Google.

---

## Modelos con Razonamiento Explícito
Un modelo de razonamiento, también llamado modelo de pensamiento o modelo de razonamiento grande (LRM), es un modelo de lenguaje grande (LLM) que ha sido afinado para realizar la resolución de problemas de múltiples pasos mediante la generación de pasos intermedios, a menudo llamados “rastreos de razonamiento”, a través del cual refina iterativamente su resultado antes de generar su respuesta final.

El concepto de "modelo de razonamiento" fue introducido por primera vez por OpenAI con o1-preview (y o1-mini) en septiembre de 2024, seguido por Alibaba con "Qwen with Questions" (QwQ-32B-preview) en noviembre y el experimento Gemini 2.0 Flash de Google en diciembre. 

Es muy importante destacar que **esta capacidad de razonamiento no aparece sola por el simple hecho de aumentar el tamaño del modelo**[cite: 9]. En su lugar, proviene estrictamente de dos factores:
1. **Técnicas de entrenamiento:** Los modelos se entrenan para dedicar más tiempo a "pensar" antes de responder, generando cadenas lógicas internas[cite: 9].
2. **Cómputo adicional en el momento de la inferencia:** Utilizan poder de procesamiento extra justo en el momento en que se les hace la pregunta para evaluar, corregir y planear su respuesta antes de mostrarla[cite: 9].

Se ha demostrado empíricamente que la incorporación de este "proceso de razonamiento" produce avances significativos en el rendimiento de los LLM en tareas complejas.
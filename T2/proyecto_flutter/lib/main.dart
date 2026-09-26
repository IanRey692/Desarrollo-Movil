import 'package:flutter/material.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'App Flutter',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MainScreen(),
    );
  }
}

class MainScreen extends StatefulWidget {
  const MainScreen({super.key});

  @override
  State<MainScreen> createState() => _MainScreenState();
}

class _MainScreenState extends State<MainScreen> {
  int _selectedIndex = 0;

  final ValueNotifier<String> sharedText = ValueNotifier<String>("");

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    final List<Widget> pages = [
      Section1Screen(sharedText: sharedText),
      const Section2Screen(),
      const Section3Screen(),
      const Section4Screen(),
      const Section5Screen(),
      Section6Screen(sharedText: sharedText),
    ];

    return Scaffold(
      appBar: AppBar(
        title: const Text('Proyecto Flutter'),
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
      ),
      body: pages[_selectedIndex],
      bottomNavigationBar: BottomNavigationBar(
        type: BottomNavigationBarType.fixed,
        items: const <BottomNavigationBarItem>[
          BottomNavigationBarItem(icon: Icon(Icons.edit), label: 'Texto'),
          BottomNavigationBarItem(icon: Icon(Icons.send), label: 'Botones'),
          BottomNavigationBarItem(icon: Icon(Icons.view_agenda), label: 'Selección'),
          BottomNavigationBarItem(icon: Icon(Icons.sort), label: 'Listas'),
          BottomNavigationBarItem(icon: Icon(Icons.info), label: 'Info'),
          BottomNavigationBarItem(icon: Icon(Icons.dashboard), label: 'Layouts'),
        ],
        currentIndex: _selectedIndex,
        onTap: _onItemTapped,
      ),
    );
  }
}

class Section1Screen extends StatefulWidget {
  final ValueNotifier<String> sharedText;
  const Section1Screen({super.key, required this.sharedText});

  @override
  State<Section1Screen> createState() => _Section1ScreenState();
}

class _Section1ScreenState extends State<Section1Screen> {
  bool _obscurePassword = true;
  String? _errorText;
  String _selectedColor = 'Rojo';
  final List<String> _colores = ['Rojo', 'Verde', 'Azul', 'Amarillo', 'Morado'];

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text("Entrada de Texto", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
          const SizedBox(height: 16),

          const Text("Dato Transversal (Para la Sección 6)", style: TextStyle(fontWeight: FontWeight.bold, color: Colors.blue)),
          const Text("Lo que escribas aquí se mostrará en los contenedores de la Sección 6.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          TextField(
            decoration: const InputDecoration(
              hintText: "Escribe tu mensaje secreto",
              border: OutlineInputBorder(),
            ),
            onChanged: (text) {
              widget.sharedText.value = text;
            },
          ),
          const SizedBox(height: 24),

          const Text("Campo simple", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          const TextField(
            decoration: InputDecoration(hintText: "Nombre completo", border: OutlineInputBorder()),
          ),
          const SizedBox(height: 16),

          const Text("Campo con validación", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          TextField(
            decoration: InputDecoration(
              hintText: "Usuario",
              border: const OutlineInputBorder(),
              errorText: _errorText,
            ),
            onChanged: (text) {
              setState(() {
                if (text.trim().length < 3 && text.isNotEmpty) {
                  _errorText = "Debe tener al menos 3 caracteres";
                } else {
                  _errorText = null;
                }
              });
            },
          ),
          const SizedBox(height: 16),

          const Text("Campo de contraseña", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          TextField(
            obscureText: _obscurePassword,
            decoration: InputDecoration(
              hintText: "Contraseña",
              border: const OutlineInputBorder(),
              suffixIcon: IconButton(
                icon: Icon(_obscurePassword ? Icons.visibility : Icons.visibility_off),
                onPressed: () {
                  setState(() {
                    _obscurePassword = !_obscurePassword;
                  });
                },
              ),
            ),
          ),
          const SizedBox(height: 16),

          const Text("Distintos teclados", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          const TextField(
            keyboardType: TextInputType.number,
            decoration: InputDecoration(hintText: "Edad (Numérico)", border: OutlineInputBorder()),
          ),
          const SizedBox(height: 8),
          const TextField(
            keyboardType: TextInputType.emailAddress,
            decoration: InputDecoration(hintText: "Correo electrónico", border: OutlineInputBorder()),
          ),
          const SizedBox(height: 8),
          const TextField(
            keyboardType: TextInputType.phone,
            decoration: InputDecoration(hintText: "Teléfono", border: OutlineInputBorder()),
          ),
          const SizedBox(height: 16),

          const Text("Campo multilínea", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          const TextField(
            maxLines: 3,
            keyboardType: TextInputType.multiline,
            decoration: InputDecoration(hintText: "Comentarios", border: OutlineInputBorder()),
          ),
          const SizedBox(height: 16),

          const Text("Desplegable / Sugerencias", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          DropdownButtonFormField<String>(
            value: _selectedColor,
            decoration: const InputDecoration(border: OutlineInputBorder()),
            items: _colores.map((String color) {
              return DropdownMenuItem<String>(value: color, child: Text(color));
            }).toList(),
            onChanged: (String? newValue) {
              setState(() {
                _selectedColor = newValue!;
              });
            },
          ),
          const SizedBox(height: 16),

          const Text("Barra de búsqueda", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          SearchBar(
            hintText: "Buscar...",
            leading: const Icon(Icons.search),
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }
}


// --- Lógica de la Sección 2 ---
class Section2Screen extends StatefulWidget {
  const Section2Screen({super.key});

  @override
  State createState() => _Section2ScreenState();
}

class _Section2ScreenState extends State {
  bool _isChecked = false;
  bool _isSwitched = false;
  int _radioValue = 1;

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text("Botones y Acciones", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
          const SizedBox(height: 16),

          // 1. Botón ElevatedButton (Relleno)
          const Text("Botón Relleno", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("El botón principal para la acción más importante.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("¡Acción confirmada!")),
                );
              },
              child: const Text("Confirmar Acción"),
            ),
          ),
          const SizedBox(height: 16),

          // 2. Botón OutlinedButton (Contorno)
          const Text("Botón de Contorno", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Acciones secundarias que no deben robar atención.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          SizedBox(
            width: double.infinity,
            child: OutlinedButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("Acción cancelada")),
                );
              },
              child: const Text("Cancelar"),
            ),
          ),
          const SizedBox(height: 16),

          // 3. Checkbox
          const Text("Casilla de Verificación", style: TextStyle(fontWeight: FontWeight.bold)),
          CheckboxListTile(
            title: const Text("Acepto los términos y condiciones"),
            value: _isChecked,
            onChanged: (bool? value) {
              setState(() {
                _isChecked = value ?? false;
              });
            },
          ),
          const SizedBox(height: 16),

          // 4. Switch
          const Text("Interruptor (Switch)", style: TextStyle(fontWeight: FontWeight.bold)),
          SwitchListTile(
            title: const Text("Recibir notificaciones"),
            value: _isSwitched,
            onChanged: (bool value) {
              setState(() {
                _isSwitched = value;
              });
            },
          ),
          const SizedBox(height: 16),

          // 5. Botones de Radio
          const Text("Botones de Radio", style: TextStyle(fontWeight: FontWeight.bold)),
          RadioListTile(
            title: const Text("Fácil"),
            value: 1,
            groupValue: _radioValue,
            onChanged: (int? value) {
              setState(() {
                _radioValue = value!;
              });
            },
          ),
          RadioListTile(
            title: const Text("Difícil"),
            value: 2,
            groupValue: _radioValue,
            onChanged: (int? value) {
              setState(() {
                _radioValue = value!;
              });
            },
          ),
          const SizedBox(height: 16),

          // 6. Floating Action Button (FAB)
          const Text("Botón Flotante (FAB)", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          Center(
            child: FloatingActionButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("¡Elemento agregado desde el FAB!")),
                );
              },
              child: const Icon(Icons.add),
            ),
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }
}


// --- Lógica de la Sección 3 ---
class Section3Screen extends StatefulWidget {
  const Section3Screen({super.key});

  @override
  State createState() => _Section3ScreenState();
}

class _Section3ScreenState extends State {
  double _currentSliderValue = 20.0;
  DateTime? _selectedDate;
  TimeOfDay? _selectedTime;

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text("Selección de Datos", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
          const SizedBox(height: 16),

          // 1. Slider (Deslizador)
          const Text("Deslizador (Slider)", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Permite seleccionar un valor numérico deslizándose.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          Slider(
            value: _currentSliderValue,
            min: 0,
            max: 100,
            divisions: 10,
            label: _currentSliderValue.round().toString(),
            onChanged: (double value) {
              setState(() {
                _currentSliderValue = value;
              });
            },
          ),
          Text("Valor actual: ${_currentSliderValue.round()}", style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500)),
          const SizedBox(height: 24),

          // 2. Selector de Fecha (DatePicker)
          const Text("Selector de Fecha", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Despliega el calendario nativo del sistema.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          ElevatedButton(
            onPressed: () async {
              final DateTime? picked = await showDatePicker(
                context: context,
                initialDate: DateTime.now(),
                firstDate: DateTime(2000),
                lastDate: DateTime(2100),
              );
              if (picked != null) {
                setState(() {
                  _selectedDate = picked;
                });
              }
            },
            child: const Text("Seleccionar Fecha"),
          ),
          const SizedBox(height: 4),
          const SizedBox(height: 24),

          // 3. Selector de Hora (TimePicker)
          const Text("Selector de Hora", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Despliega el reloj nativo del sistema.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          ElevatedButton(
            onPressed: () async {
              final TimeOfDay? picked = await showTimePicker(
                context: context,
                initialTime: TimeOfDay.now(),
              );
              if (picked != null) {
                setState(() {
                  _selectedTime = picked;
                });
              }
            },
            child: const Text("Seleccionar Hora"),
          ),
          const SizedBox(height: 4),
          Text(
            _selectedTime == null
                ? "Hora: No seleccionada"
                : "Hora: ${_selectedTime!.format(context)}",
            style: const TextStyle(fontSize: 16, fontWeight: FontWeight.w500),
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }
}

// --- Lógica de la Sección 4 ---
class Section4Screen extends StatelessWidget {
  const Section4Screen({super.key});

  @override
  Widget build(BuildContext context) {
    final List lenguajes = ["Java", "Kotlin", "Python", "C", "C++", "PHP", "VHDL", "Dart", "Flutter"];
    final List herramientas = ["Docker", "MySQL", "PostgreSQL", "Git", "Power BI", "Excel", "GNS3", "Flask", "Quartus"];

    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text("Listas y Colecciones", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
          const SizedBox(height: 16),

          // 1. ListView clásico para lenguajes
          const Text("Lista de Lenguajes (ListView)", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          ListView.builder(
            shrinkWrap: true, // Permite que el ListView funcione dentro de un ScrollView
            physics: const NeverScrollableScrollPhysics(), // Evita conflictos de scroll
            itemCount: lenguajes.length,
            itemBuilder: (context, index) {
              return Card(
                child: ListTile(
                  leading: const Icon(Icons.code, color: Colors.deepPurple),
                  title: Text(lenguajes[index]),
                  onTap: () {
                    ScaffoldMessenger.of(context).showSnackBar(
                      SnackBar(content: Text("Seleccionaste: ${lenguajes[index]}")),
                    );
                  },
                ),
              );
            },
          ),
          const SizedBox(height: 24),

          // 2. GridView o lista de herramientas
          const Text("Cuadrícula de Herramientas", style: TextStyle(fontWeight: FontWeight.bold)),
          const SizedBox(height: 8),
          GridView.builder(
            shrinkWrap: true,
            physics: const NeverScrollableScrollPhysics(),
            gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
              crossAxisCount: 2,
              crossAxisSpacing: 8.0,
              mainAxisSpacing: 8.0,
              childAspectRatio: 3.0,
            ),
            itemCount: herramientas.length,
            itemBuilder: (context, index) {
              return Card(
                color: Colors.deepPurple.shade50,
                child: Center(
                  child: Text(
                    herramientas[index],
                    style: const TextStyle(fontWeight: FontWeight.bold),
                  ),
                ),
              );
            },
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }
}

// --- Lógica de la Sección 5 ---
class Section5Screen extends StatefulWidget {
  const Section5Screen({super.key});

  @override
  State createState() => _Section5ScreenState();
}

class _Section5ScreenState extends State {
  double _progressValue = 0.0;

  void _aumentarProgreso() {
    setState(() {
      if (_progressValue >= 1.0) {
        _progressValue = 0.0;
      } else {
        _progressValue += 0.2;
      }
    });
  }

  void _mostrarDialogo(BuildContext context) {
    showDialog(
      context: context,
      builder: (BuildContext context) {
        return AlertDialog(
          title: const Text("Confirmar acción"),
          content: const Text("¿Estás seguro de que deseas guardar estos cambios en la base de datos?"),
          actions: [
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
                ScaffoldMessenger.of(context).showSnackBar(
                  const SnackBar(content: Text("Cambios guardados")),
                );
              },
              child: const Text("Sí"),
            ),
            TextButton(
              onPressed: () {
                Navigator.of(context).pop();
              },
              child: const Text("No"),
            ),
          ],
        );
      },
    );
  }

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text("Información y Diálogos", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
          const SizedBox(height: 16),

          // 1. ProgressBar Circular
          const Text("Carga Indeterminada", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Indica que un proceso (como red o base de datos) se está ejecutando.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 12),
          const Center(child: CircularProgressIndicator()),
          const SizedBox(height: 24),

          // 2. ProgressBar Horizontal
          const Text("Barra de Progreso", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Muestra el avance exacto de una tarea.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 12),
          LinearProgressIndicator(value: _progressValue),
          const SizedBox(height: 8),
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
              onPressed: _aumentarProgreso,
              child: const Text("Aumentar progreso (+20%)"),
            ),
          ),
          const SizedBox(height: 24),

          // 3. Snackbar
          const Text("Mensaje Snackbar", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Notificación en la parte inferior con opción de deshacer acción.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
              onPressed: () {
                ScaffoldMessenger.of(context).showSnackBar(
                  SnackBar(
                    content: const Text("Sincronización completada"),
                    action: SnackBarAction(
                      label: "Deshacer",
                      onPressed: () {
                        ScaffoldMessenger.of(context).showSnackBar(
                          const SnackBar(content: Text("Acción deshecha")),
                        );
                      },
                    ),
                  ),
                );
              },
              child: const Text("Mostrar Snackbar"),
            ),
          ),
          const SizedBox(height: 24),

          // 4. AlertDialog
          const Text("Cuadro de Diálogo", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Interrumpe al usuario para confirmar una decisión.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          SizedBox(
            width: double.infinity,
            child: ElevatedButton(
              onPressed: () => _mostrarDialogo(context),
              child: const Text("Mostrar Alerta"),
            ),
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }
}


// --- Lógica de la Sección 6 (Conexión de Datos en Tiempo Real) ---
class Section6Screen extends StatelessWidget {
  final ValueNotifier sharedText;
  const Section6Screen({super.key, required this.sharedText});

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      padding: const EdgeInsets.all(16.0),
      child: Column(
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          const Text("Layouts y Conexión", style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
          const SizedBox(height: 16),

          // --- DATO TRANSVERSAL RECIBIDO ---
          const Text("Dato Transversal (Desde Sección 1)", style: TextStyle(fontWeight: FontWeight.bold, color: Colors.green)),
          const Text("El texto que escribas en la primera pestaña aparece aquí en tiempo real:", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),

          // Escuchamos el ValueNotifier en tiempo real sin recargar toda la pantalla
          ValueListenableBuilder(
            valueListenable: sharedText,
            builder: (context, value, child) {
              return Container(
                width: double.infinity,
                padding: const EdgeInsets.all(12.0),
                decoration: BoxDecoration(
                  color: Colors.grey.shade800,
                  borderRadius: BorderRadius.circular(8.0),
                ),
                child: Text(
                  value.isEmpty ? "Aún no has escrito nada en la Sección 1." : value,
                  style: const TextStyle(
                    fontSize: 18,
                    fontStyle: FontStyle.italic,
                    color: Colors.white,
                  ),
                ),
              );
            },
          ),
          const SizedBox(height: 24),

          // 1. Contenedor Apilado (Equivalente a FrameLayout)
          const Text("Contenedor Apilado (Stack)", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Apila vistas una sobre otra como capas.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          Container(
            height: 100,
            width: double.infinity,
            color: Colors.red.shade200,
            child: Stack(
              children: const [
                Positioned(
                  top: 8,
                  left: 8,
                  child: Text("Capa del Fondo"),
                ),
                Center(
                  child: Text(
                    "Capa Frontal (Centro)",
                    style: TextStyle(fontWeight: FontWeight.bold, fontSize: 16),
                  ),
                ),
              ],
            ),
          ),
          const SizedBox(height: 24),

          // 2. Contenedor Posicionado (Equivalente a ConstraintLayout)
          const Text("Contenedor Relativo (Row espaciado)", style: TextStyle(fontWeight: FontWeight.bold)),
          const Text("Posicionamiento relativo anclando vistas a los extremos.", style: TextStyle(fontSize: 12)),
          const SizedBox(height: 8),
          Container(
            height: 100,
            width: double.infinity,
            padding: const EdgeInsets.symmetric(horizontal: 16.0),
            color: Colors.blue.shade200,
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceBetween,
              children: const [
                Text("Anclado Izquierda", style: TextStyle(fontWeight: FontWeight.bold)),
                Text("Anclado Derecha", style: TextStyle(fontWeight: FontWeight.bold)),
              ],
            ),
          ),
          const SizedBox(height: 32),
        ],
      ),
    );
  }
}
  @override
  Widget build(BuildContext context) {
    return const Center(child: Text("Aquí irá la Sección 6"));
  }



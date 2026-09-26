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
      const Center(child: Text("Sección 3: Selección")),
      const Center(child: Text("Sección 4: Listas")),
      const Center(child: Text("Sección 5: Info")),
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

class Section6Screen extends StatelessWidget {
  final ValueNotifier<String> sharedText;
  const Section6Screen({super.key, required this.sharedText});

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text("Aquí irá la Sección 6"));
  }
}


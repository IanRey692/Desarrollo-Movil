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
  State createState() => _MainScreenState();
}

class _MainScreenState extends State {
  int _selectedIndex = 0;

  // Nuestro "SharedViewModel" nativo en Flutter
  final ValueNotifier sharedText = ValueNotifier("");

  void _onItemTapped(int index) {
    setState(() {
      _selectedIndex = index;
    });
  }

  @override
  Widget build(BuildContext context) {
    // Lista de pantallas inyectando el ValueNotifier donde se necesita
    final List pages = [
      Section1Screen(sharedText: sharedText),
      const Center(child: Text("Sección 2: Botones y Acciones")),
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
        items: const [
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

// --- Cascarón de la Sección 1 ---
class Section1Screen extends StatelessWidget {
  final ValueNotifier sharedText;
  const Section1Screen({super.key, required this.sharedText});

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text("Aquí irá la Sección 1"));
  }
}

// --- Cascarón de la Sección 6 ---
class Section6Screen extends StatelessWidget {
  final ValueNotifier sharedText;
  const Section6Screen({super.key, required this.sharedText});

  @override
  Widget build(BuildContext context) {
    return const Center(child: Text("Aquí irá la Sección 6"));
  }
}
import 'package:flutter/material.dart';

void main() {
  runApp(const MiAppFlutter());
}

class MiAppFlutter extends StatelessWidget {
  const MiAppFlutter({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      debugShowCheckedModeBanner: false,
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Versión Flutter'),
        ),
        body: const Padding(
          padding: EdgeInsets.all(16.0),
          child: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: [
              Text('Hola Mundo', style: TextStyle(fontSize: 24, fontWeight: FontWeight.bold)),
              SizedBox(height: 8),
              Text('En Flutter', style: TextStyle(fontSize: 18)),
            ],
          ),
        ),
      ),
    );
  }
}
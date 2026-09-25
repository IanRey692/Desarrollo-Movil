package ianreyna272.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.ListView
import android.widget.Toast
import androidx.fragment.app.Fragment

class Section4Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seccion4, container, false)

        // --- 1. Lógica del ListView Clásico ---
        val listView: ListView = view.findViewById(R.id.list_view_simple)
        val lenguajes = arrayOf("Java", "Kotlin", "Python", "C", "C++", "PHP", "VHDL")

        val listAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, lenguajes)
        listView.adapter = listAdapter

        // Acción al tocar un elemento de la lista
        listView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(context, "Lenguaje: ${lenguajes[position]}", Toast.LENGTH_SHORT).show()
        }

        // --- 2. Lógica del GridView ---
        val gridView: GridView = view.findViewById(R.id.grid_view)
        val herramientas = arrayOf("Docker", "MySQL", "PostgreSQL", "Git", "Power BI", "Excel", "GNS3", "Flask", "Quartus")

        val gridAdapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1, herramientas)
        gridView.adapter = gridAdapter

        // Acción al tocar un elemento de la cuadrícula
        gridView.setOnItemClickListener { _, _, position, _ ->
            Toast.makeText(context, "Herramienta: ${herramientas[position]}", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
package ianreyna272.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.switchmaterial.SwitchMaterial

class Section2Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seccion2, container, false)

        // 1. Botón Simple
        val btnSimple: Button = view.findViewById(R.id.btn_simple)
        btnSimple.setOnClickListener {
            Toast.makeText(context, "¡Acción confirmada!", Toast.LENGTH_SHORT).show()
        }

        // 2. Botón de Contorno
        val btnContorno: Button = view.findViewById(R.id.btn_contorno)
        btnContorno.setOnClickListener {
            Toast.makeText(context, "Acción cancelada", Toast.LENGTH_SHORT).show()
        }

        // 3. CheckBox
        val cbTerminos: CheckBox = view.findViewById(R.id.cb_terminos)
        cbTerminos.setOnCheckedChangeListener { _, isChecked ->
            val mensaje = if (isChecked) "Términos aceptados" else "Términos rechazados"
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }

        // 4. Switch
        val swNotificaciones: SwitchMaterial = view.findViewById(R.id.sw_notificaciones)
        swNotificaciones.setOnCheckedChangeListener { _, isChecked ->
            val mensaje = if (isChecked) "Notificaciones activadas" else "Notificaciones silenciadas"
            Toast.makeText(context, mensaje, Toast.LENGTH_SHORT).show()
        }

        // 5. RadioGroup
        val rgDificultad: RadioGroup = view.findViewById(R.id.rg_dificultad)
        rgDificultad.setOnCheckedChangeListener { _, checkedId ->
            val seleccion = if (checkedId == R.id.rb_facil) "Fácil" else "Difícil"
            Toast.makeText(context, "Nivel cambiado a: $seleccion", Toast.LENGTH_SHORT).show()
        }

        // 6. Botón Flotante (FAB)
        val fabAgregar: FloatingActionButton = view.findViewById(R.id.fab_agregar)
        fabAgregar.setOnClickListener {
            Toast.makeText(context, "¡Elemento agregado desde el FAB!", Toast.LENGTH_SHORT).show()
        }

        return view
    }
}
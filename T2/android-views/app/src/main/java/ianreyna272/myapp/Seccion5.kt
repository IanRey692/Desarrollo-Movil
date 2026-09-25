package ianreyna272.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

class Section5Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seccion5, container, false)

        // 1. Barra de progreso horizontal
        val progressHorizontal: ProgressBar = view.findViewById(R.id.progress_horizontal)
        val btnAumentarProgreso: Button = view.findViewById(R.id.btn_aumentar_progreso)

        btnAumentarProgreso.setOnClickListener {
            // Si llega al 100%, la reiniciamos a 0
            if (progressHorizontal.progress >= 100) {
                progressHorizontal.progress = 0
            } else {
                progressHorizontal.progress += 10
            }
        }

        // 2. Snackbar (Mensaje inferior interactivo)
        val btnSnackbar: Button = view.findViewById(R.id.btn_snackbar)
        btnSnackbar.setOnClickListener {
            // Un Snackbar se amarra directamente a la vista raíz (view)
            Snackbar.make(view, "Sincronización completada", Snackbar.LENGTH_LONG)
                .setAction("Deshacer") {
                    Toast.makeText(context, "Acción deshecha", Toast.LENGTH_SHORT).show()
                }
                .show()
        }

        // 3. AlertDialog (Cuadro de confirmación)
        val btnDialogo: Button = view.findViewById(R.id.btn_dialogo)
        btnDialogo.setOnClickListener {
            val builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Confirmar acción")
            builder.setMessage("¿Estás seguro de que deseas guardar estos cambios en la base de datos?")

            builder.setPositiveButton("Sí") { dialog, _ ->
                Toast.makeText(context, "Cambios guardados", Toast.LENGTH_SHORT).show()
                dialog.dismiss()
            }

            builder.setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }

            val dialog: AlertDialog = builder.create()
            dialog.show()
        }

        return view
    }
}
package ianreyna272.myapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

class Section6Fragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seccion6, container, false)

        val tvDatoRecibido: TextView = view.findViewById(R.id.tv_dato_recibido)

        // Instanciamos el mismo ViewModel de la actividad principal
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Usamos una corrutina para recolectar el texto transversal en tiempo real
        viewLifecycleOwner.lifecycleScope.launch {
            sharedViewModel.sharedText.collect { textoSecreto ->
                if (textoSecreto.isBlank()) {
                    tvDatoRecibido.text = "Aún no has escrito nada en la Sección 1."
                } else {
                    tvDatoRecibido.text = textoSecreto
                }
            }
        }

        return view
    }
}
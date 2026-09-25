package ianreyna272.myapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class Section1Fragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seccion1, container, false)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // --- 1. Lógica para el Dato Transversal ---
        // Declaramos el tipo aquí para no usar los símbolos que oculta el navegador
        val etTransversal: TextInputEditText = view.findViewById(R.id.et_transversal)

        etTransversal.setText(sharedViewModel.sharedText.value)

        etTransversal.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                sharedViewModel.updateSharedText(s.toString())
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // --- 2. Lógica para la validación de texto ---
        val layoutValidacion: TextInputLayout = view.findViewById(R.id.layout_validacion)
        val etValidacion: TextInputEditText = view.findViewById(R.id.et_validacion)

        etValidacion.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.toString().trim().length < 3) {
                    layoutValidacion.error = "Debe tener al menos 3 caracteres"
                } else {
                    layoutValidacion.error = null
                }
            }
            override fun afterTextChanged(s: Editable?) {}
        })

        // --- 3. Lógica para el menú de sugerencias (Dropdown) ---
        val autoComplete: AutoCompleteTextView = view.findViewById(R.id.autoCompleteOpciones)
        val opciones = arrayOf("Rojo", "Verde", "Azul", "Amarillo", "Morado")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, opciones)
        autoComplete.setAdapter(adapter)

        return view
    }
}
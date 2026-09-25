package ianreyna272.myapp

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import java.util.*

class Section3Fragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_seccion3, container, false)

        // 1. Spinner (Menú clásico)
        val spinner: Spinner = view.findViewById(R.id.spinner_opciones)
        val opciones = arrayOf("Licenciatura", "Maestría", "Doctorado")
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_dropdown_item, opciones)
        spinner.adapter = adapter

        // 2. RatingBar
        val ratingBar: RatingBar = view.findViewById(R.id.rating_bar)
        ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            Toast.makeText(context, "Calificación: $rating", Toast.LENGTH_SHORT).show()
        }

        // 3. SeekBar
        val seekBar: SeekBar = view.findViewById(R.id.seek_bar)
        val tvSeekValor: TextView = view.findViewById(R.id.tv_seek_valor)
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                tvSeekValor.text = "Valor: $progress"
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // 4. DatePicker (Selector de Fecha)
        val btnFecha: Button = view.findViewById(R.id.btn_fecha)
        val tvFecha: TextView = view.findViewById(R.id.tv_fecha)
        btnFecha.setOnClickListener {
            val calendario = Calendar.getInstance()
            val year = calendario.get(Calendar.YEAR)
            val month = calendario.get(Calendar.MONTH)
            val day = calendario.get(Calendar.DAY_OF_MONTH)

            DatePickerDialog(requireContext(), { _, y, m, d ->

            }, year, month, day).show()
        }

        // 5. TimePicker (Selector de Hora)
        val btnHora: Button = view.findViewById(R.id.btn_hora)
        val tvHora: TextView = view.findViewById(R.id.tv_hora)
        btnHora.setOnClickListener {
            val calendario = Calendar.getInstance()
            val hour = calendario.get(Calendar.HOUR_OF_DAY)
            val minute = calendario.get(Calendar.MINUTE)

            TimePickerDialog(requireContext(), { _, h, m ->
            }, hour, minute, true).show()
        }

        return view
    }
}
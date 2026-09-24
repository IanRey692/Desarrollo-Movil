package ianreyna272.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Section3Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Elementos de Selección", style = MaterialTheme.typography.headlineMedium)

        // 1. Casillas de verificación (Checkbox y TriState)
        Text("Casillas de Verificación", style = MaterialTheme.typography.titleMedium)
        var checkedState by remember { mutableStateOf(false) }
        var triState by remember { mutableStateOf(ToggleableState.Indeterminate) }

        Row(verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = checkedState, onCheckedChange = { checkedState = it })
            Text("Opción simple")
            Spacer(modifier = Modifier.width(16.dp))
            TriStateCheckbox(
                state = triState,
                onClick = {
                    triState = when (triState) {
                        ToggleableState.On -> ToggleableState.Off
                        ToggleableState.Off -> ToggleableState.Indeterminate
                        ToggleableState.Indeterminate -> ToggleableState.On
                    }
                }
            )
            Text("Indeterminado")
        }
        Text("Permiten selecciones múltiples. El estado indeterminado es útil para representar un grupo donde solo algunos sub-elementos están seleccionados.", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 2. Botones de opción (Radio Buttons)
        Text("Grupo de Opciones Excluyentes", style = MaterialTheme.typography.titleMedium)
        val radioOptions = listOf("Opción A", "Opción B", "Opción C")
        var selectedOption by remember { mutableStateOf(radioOptions[0]) }

        Column(Modifier.selectableGroup()) {
            radioOptions.forEach { text ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(40.dp)
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = { selectedOption = text },
                            role = Role.RadioButton
                        ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(selected = (text == selectedOption), onClick = null)
                    Text(text = text, modifier = Modifier.padding(start = 8.dp))
                }
            }
        }
        Text("Los RadioButtons obligan al usuario a elegir una y solo una opción de un conjunto mutuamente excluyente.", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 3. Interruptor (Switch)
        Text("Interruptor (Switch)", style = MaterialTheme.typography.titleMedium)
        var switchState by remember { mutableStateOf(true) }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Switch(
                checked = switchState,
                onCheckedChange = { switchState = it },
                thumbContent = if (switchState) {
                    { Icon(Icons.Filled.Check, contentDescription = null, Modifier.size(SwitchDefaults.IconSize)) }
                } else null
            )
            Text(" Modo ${if (switchState) "Activado" else "Desactivado"}", modifier = Modifier.padding(start = 8.dp))
        }
        Text("Cambia el estado de un ajuste único, aplicando el efecto casi de manera instantánea (ej. Wi-Fi).", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 4. Deslizadores (Slider y RangeSlider)
        Text("Deslizadores", style = MaterialTheme.typography.titleMedium)
        var sliderValue by remember { mutableFloatStateOf(50f) }
        var rangeSliderValue by remember { mutableStateOf(20f..80f) }

        Text("Valor único: ${sliderValue.toInt()}")
        Slider(value = sliderValue, onValueChange = { sliderValue = it }, valueRange = 0f..100f)

        Text("Rango: ${rangeSliderValue.start.toInt()} - ${rangeSliderValue.endInclusive.toInt()}")
        RangeSlider(value = rangeSliderValue, onValueChange = { rangeSliderValue = it }, valueRange = 0f..100f)

        Text("Permiten al usuario seleccionar un valor específico o un rango de valores deslizando sobre una barra continua.", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 5. Chips de filtro
        Text("Chips de Filtro", style = MaterialTheme.typography.titleMedium)
        var chip1Selected by remember { mutableStateOf(false) }
        var chip2Selected by remember { mutableStateOf(true) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            FilterChip(
                selected = chip1Selected,
                onClick = { chip1Selected = !chip1Selected },
                label = { Text("Vegetariano") },
                leadingIcon = if (chip1Selected) { { Icon(Icons.Filled.Check, contentDescription = null) } } else null
            )
            FilterChip(
                selected = chip2Selected,
                onClick = { chip2Selected = !chip2Selected },
                label = { Text("Sin Gluten") },
                leadingIcon = if (chip2Selected) { { Icon(Icons.Filled.Check, contentDescription = null) } } else null
            )
        }
        Text("Los FilterChips son componentes compactos que permiten activar o desactivar filtros de búsqueda de forma independiente.", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 6. Selectores de Fecha y Hora (Dialogs básicos para la demostración)
        Text("Selectores de Fecha y Hora", style = MaterialTheme.typography.titleMedium)
        var showDatePicker by remember { mutableStateOf(false) }
        var showTimePicker by remember { mutableStateOf(false) }
        var selectedDate by remember { mutableStateOf("No seleccionada") }
        var selectedTime by remember { mutableStateOf("No seleccionada") }

        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { showDatePicker = true }) { Text("Elegir Fecha") }
            Button(onClick = { showTimePicker = true }) { Text("Elegir Hora") }
        }
        Text("Fecha: $selectedDate | Hora: $selectedTime", color = MaterialTheme.colorScheme.primary)
        Text("Despliegan interfaces nativas complejas para garantizar que el usuario ingrese tiempos y fechas en formatos válidos.", style = MaterialTheme.typography.bodySmall)

        if (showDatePicker) {
            val datePickerState = rememberDatePickerState()
            DatePickerDialog(
                onDismissRequest = { showDatePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        datePickerState.selectedDateMillis?.let { millis ->
                            selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(Date(millis))
                        }
                        showDatePicker = false
                    }) { Text("Aceptar") }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }

        if (showTimePicker) {
            val timePickerState = rememberTimePickerState()
            AlertDialog(
                onDismissRequest = { showTimePicker = false },
                confirmButton = {
                    TextButton(onClick = {
                        selectedTime = "${timePickerState.hour}:${timePickerState.minute.toString().padStart(2, '0')}"
                        showTimePicker = false
                    }) { Text("Aceptar") }
                },
                text = { TimePicker(state = timePickerState) }
            )
        }
    }
}
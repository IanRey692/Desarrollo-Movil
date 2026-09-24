package ianreyna272.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Section1Screen(sharedViewModel: SharedViewModel) {
    // Escuchamos el estado del texto compartido
    val sharedText by sharedViewModel.sharedText.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()), // Permite hacer scroll en la pantalla
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Entrada de Texto", style = MaterialTheme.typography.headlineMedium)

        // 1. Campo de texto simple (Vinculado a la Sección 4)
        OutlinedTextField(
            value = sharedText,
            onValueChange = { sharedViewModel.updateSharedText(it) },
            label = { Text("Campo de texto simple") },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Permite ingresar texto libre. Lo que escribas aquí se guardará en el SharedViewModel y aparecerá en la lista de la Sección 4.",
            style = MaterialTheme.typography.bodySmall
        )
        Divider()

        // 2. Campo con validación y error
        var email by remember { mutableStateOf("") }
        var isError by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                isError = !it.contains("@") && it.isNotEmpty()
            },
            label = { Text("Correo electrónico") },
            isError = isError,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            supportingText = {
                if (isError) {
                    Text("Error: Formato de correo inválido (falta el @)")
                }
            }
        )
        Text(
            "Valida en tiempo real que el texto contenga un '@'. Activa el estado de error y cambia el color del componente si la validación falla.",
            style = MaterialTheme.typography.bodySmall
        )
        Divider()

        // 3. Campo de contraseña
        var password by remember { mutableStateOf("") }
        var passwordVisible by remember { mutableStateOf(false) }

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                val image = if (passwordVisible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
                IconButton(onClick = { passwordVisible = !passwordVisible }) {
                    Icon(imageVector = image, contentDescription = "Alternar visibilidad")
                }
            },
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Oculta los caracteres por seguridad. Incluye un ícono interactivo al final para alternar entre mostrar u ocultar el texto.",
            style = MaterialTheme.typography.bodySmall
        )

        Divider()

        // 4. Campo con teclado específico (Teléfono)
        var phone by remember { mutableStateOf("") }
        OutlinedTextField(
            value = phone,
            onValueChange = { phone = it },
            label = { Text("Teléfono") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Despliega un teclado numérico optimizado para introducir números de teléfono.",
            style = MaterialTheme.typography.bodySmall
        )
        Divider()

        // 5. Campo multilínea
        var multiline by remember { mutableStateOf("") }
        OutlinedTextField(
            value = multiline,
            onValueChange = { multiline = it },
            label = { Text("Comentarios (Multilínea)") },
            singleLine = false,
            minLines = 3,
            maxLines = 5,
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Permite ingresar varias líneas de texto, expandiéndose verticalmente hasta un límite de 5 líneas.",
            style = MaterialTheme.typography.bodySmall
        )
        Divider()

        // 6. Desplegable de opciones (Sugerencias)
        val options = listOf("Kotlin", "Java", "Flutter", "Swift")
        var expanded by remember { mutableStateOf(false) }
        var selectedOptionText by remember { mutableStateOf(options[0]) }

        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
        ) {
            OutlinedTextField(
                readOnly = true,
                value = selectedOptionText,
                onValueChange = { },
                label = { Text("Tecnología favorita") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                modifier = Modifier.menuAnchor().fillMaxWidth()
            )
            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                options.forEach { selectionOption ->
                    DropdownMenuItem(
                        text = { Text(selectionOption) },
                        onClick = {
                            selectedOptionText = selectionOption
                            expanded = false
                        }
                    )
                }
            }
        }
        Text(
            "Muestra una lista de opciones al pulsarse. Al elegir una, el menú se cierra y el valor se actualiza.",
            style = MaterialTheme.typography.bodySmall
        )
        Divider()

        // 7. Barra de búsqueda
        var searchQuery by remember { mutableStateOf("") }
        OutlinedTextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            placeholder = { Text("Buscar en el catálogo...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Buscar") },
            trailingIcon = {
                if (searchQuery.isNotEmpty()) {
                    IconButton(onClick = { searchQuery = "" }) {
                        Icon(Icons.Default.Clear, contentDescription = "Limpiar búsqueda")
                    }
                }
            },
            singleLine = true,
            shape = MaterialTheme.shapes.extraLarge, // Le da forma redondeada de barra de búsqueda
            modifier = Modifier.fillMaxWidth()
        )
        Text(
            "Campo optimizado visualmente para buscar. Incluye un ícono inicial y un botón final que aparece para limpiar el texto rápidamente.",
            style = MaterialTheme.typography.bodySmall
        )
    }
}
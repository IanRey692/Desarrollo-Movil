package ianreyna272.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Section2Screen() {
    // Estado para mostrar la "respuesta visible al pulsarse"
    var lastAction by remember { mutableStateOf("Toca cualquier botón...") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Botones y Acciones", style = MaterialTheme.typography.headlineMedium)

        // Banner de respuesta visible
        Surface(
            color = MaterialTheme.colorScheme.secondaryContainer,
            shape = MaterialTheme.shapes.medium,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Última acción: $lastAction",
                modifier = Modifier.padding(16.dp),
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        }
        Divider()

        // 1. Relleno, contorno y texto
        Text("Tipos Básicos", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(onClick = { lastAction = "Botón relleno pulsado" }) { Text("Relleno") }
            OutlinedButton(onClick = { lastAction = "Botón contorno pulsado" }) { Text("Contorno") }
            TextButton(onClick = { lastAction = "Botón texto pulsado" }) { Text("Texto") }
        }
        Text("Botón principal de alto énfasis (relleno), énfasis medio (contorno) y bajo énfasis (solo texto).", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 2. Botones con Ícono
        Text("Con Íconos", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { lastAction = "Solo ícono pulsado" }) {
                Icon(Icons.Default.Favorite, contentDescription = "Favorito")
            }
            Button(onClick = { lastAction = "Ícono + Texto pulsado" }) {
                Icon(Icons.Default.AddShoppingCart, contentDescription = "Comprar", modifier = Modifier.padding(end = 8.dp))
                Text("Comprar")
            }
        }
        Text("IconButton para acciones compactas y Button combinado con Icon para mayor claridad.", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 3. Acción Flotante (FAB)
        Text("Acción Flotante (FAB)", style = MaterialTheme.typography.titleMedium)
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp), verticalAlignment = Alignment.CenterVertically) {
            FloatingActionButton(onClick = { lastAction = "FAB Normal pulsado" }) {
                Icon(Icons.Default.Add, contentDescription = "Agregar")
            }
            ExtendedFloatingActionButton(
                onClick = { lastAction = "FAB Extendido pulsado" },
                icon = { Icon(Icons.Default.Edit, contentDescription = "Editar") },
                text = { Text("Redactar") }
            )
        }
        Text("Elementos prominentes para la acción principal de la pantalla. El extendido incluye texto.", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 4. Toggle / Alternancia
        Text("Alternancia (Toggle)", style = MaterialTheme.typography.titleMedium)
        var isToggled by remember { mutableStateOf(false) }
        IconToggleButton(
            checked = isToggled,
            onCheckedChange = {
                isToggled = it
                lastAction = if (it) "Toggle activado" else "Toggle desactivado"
            }
        ) {
            Icon(
                imageVector = if (isToggled) Icons.Default.Star else Icons.Default.StarBorder,
                contentDescription = "Alternar favorito",
                tint = if (isToggled) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Text("Guarda un estado de encendido/apagado, ideal para marcar favoritos o cambiar modos.", style = MaterialTheme.typography.bodySmall)
        Divider()

        // 5. Deshabilitado y Carga
        Text("Estados Especiales", style = MaterialTheme.typography.titleMedium)
        var isLoading by remember { mutableStateOf(false) }
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp), verticalAlignment = Alignment.CenterVertically) {
            Button(onClick = { }, enabled = false) {
                Text("Deshabilitado")
            }
            Button(
                onClick = {
                    isLoading = !isLoading
                    lastAction = if (isLoading) "Cargando..." else "Carga detenida"
                }
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(20.dp),
                        color = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Cargando...")
                } else {
                    Text("Iniciar Carga")
                }
            }
        }
        Text("Muestra un botón inactivo que no responde a clics y un botón que integra un indicador de progreso circular.", style = MaterialTheme.typography.bodySmall)
    }
}
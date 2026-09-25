package ianreyna272.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun Section4Screen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Listas y Colecciones", style = MaterialTheme.typography.headlineMedium)

        // 1. Representación de Columna Simple
        Text("Lista Vertical Simple", style = MaterialTheme.typography.titleMedium)
        Column(modifier = Modifier.fillMaxWidth()) {
            (1..3).forEach { item ->
                ListItem(
                    headlineContent = { Text("Elemento $item") },
                    supportingContent = { Text("Descripción del elemento de lista") },
                    leadingContent = { Icon(Icons.Default.Star, contentDescription = null) }
                )
            }
        }
        Text("Representa una 'LazyColumn'. Despliega elementos verticalmente, cargando solo los visibles en pantalla. Ideal para menús largos.", style = MaterialTheme.typography.bodySmall)
        HorizontalDivider()

        // 2. Representación de Fila Simple
        Text("Lista Horizontal", style = MaterialTheme.typography.titleMedium)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            (1..3).forEach { item ->
                Card(modifier = Modifier.size(100.dp)) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text("Item $item")
                    }
                }
            }
        }
        Text("Representa una 'LazyRow'. Muestra elementos en un carrusel de lado a lado. Se usa mucho para categorías o historias.", style = MaterialTheme.typography.bodySmall)
        HorizontalDivider()

        // 3. Representación de Cuadrícula
        Text("Cuadrícula (Grid)", style = MaterialTheme.typography.titleMedium)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(modifier = Modifier.height(80.dp).fillMaxWidth()) { Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Grid 1") } }
                Card(modifier = Modifier.height(80.dp).fillMaxWidth()) { Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Grid 3") } }
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Card(modifier = Modifier.height(80.dp).fillMaxWidth()) { Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Grid 2") } }
                Card(modifier = Modifier.height(80.dp).fillMaxWidth()) { Box(Modifier.fillMaxSize(), Alignment.Center) { Text("Grid 4") } }
            }
        }
        Text("Representa un 'LazyVerticalGrid'. Organiza los datos en filas y columnas responsivas. Perfecto para galerías de productos.", style = MaterialTheme.typography.bodySmall)
    }
}
package ianreyna272.myapp

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Section5Screen() {
    var progress by remember { mutableFloatStateOf(0.1f) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            progress = if (progress < 1f) progress + 0.1f else 0f
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Elementos de Información",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Indicadores de Progreso",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CircularProgressIndicator(progress = { progress })
            LinearProgressIndicator(progress = { progress })
        }
        Text(
            text = "Informan al usuario sobre el estado de una operación en curso.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )

        HorizontalDivider()

        Text(
            text = "Insignias (Badges)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            BadgedBox(badge = { Badge { Text("3") } }) {
                Icon(Icons.Default.Email, contentDescription = "Correos", modifier = Modifier.size(40.dp))
            }
            BadgedBox(badge = { Badge() }) {
                Icon(Icons.Default.Info, contentDescription = "Notificación", modifier = Modifier.size(40.dp))
            }
        }
        Text(
            text = "Muestran notificaciones o conteos sobre otros elementos.",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
    }
}
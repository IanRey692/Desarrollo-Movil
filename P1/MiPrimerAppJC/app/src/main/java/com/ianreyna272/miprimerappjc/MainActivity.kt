package com.ianreyna272.miprimerappjc

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ianreyna272.miprimerappjc.ui.theme.MiPrimerAppJCTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MiPrimerAppJCTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Info(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}
@Preview(showBackground = true)
@Composable
fun Info(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.padding(16.dp)
    ){
        Text(text = "Hola Mundo")
        Text(text = "En Jetpack Compose")
    }
}
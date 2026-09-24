package ianreyna272.myapp

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun MainScreen(sharedViewModel: SharedViewModel = viewModel()) {
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "section1",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("section1") { Section1Screen(sharedViewModel) }
            composable("section2") { Section2Screen() }
            composable("section3") { Section3Screen() }
            composable("section4") { Text("Sección 4: Listas") }
            composable("section5") { Text("Sección 5: Info") }
            composable("section6") { Text("Sección 6: Contenedores") }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        Pair("section1", Icons.Default.Edit),
        Pair("section2", Icons.Default.TouchApp),
        Pair("section3", Icons.Default.Checklist),
        Pair("section4", Icons.Default.List),
        Pair("section5", Icons.Default.Info),
        Pair("section6", Icons.Default.ViewQuilt)
    )

    NavigationBar {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.second, contentDescription = "Sección ${index + 1}") },
                label = { Text("S${index + 1}") },
                selected = currentRoute == item.first,
                onClick = {
                    navController.navigate(item.first) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
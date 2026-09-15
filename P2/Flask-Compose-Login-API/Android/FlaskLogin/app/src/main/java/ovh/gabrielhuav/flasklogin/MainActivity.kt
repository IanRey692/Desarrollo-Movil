package ovh.gabrielhuav.flasklogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch

val GuindaFuerte = Color(0xFF6B1028)
val GuindaOscuro = Color(0xFF420615)
val GuindaClaro = Color(0xFFA6183D)

val CustomColorScheme = lightColorScheme(
    primary = GuindaFuerte,
    onPrimary = Color.White,
    secondary = GuindaOscuro,
    tertiary = GuindaClaro,
    onTertiary = Color.White
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(colorScheme = CustomColorScheme) {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    val snackbarHostState = remember { SnackbarHostState() }
    var sessionToken by remember { mutableStateOf("") }
    var sessionUsername by remember { mutableStateOf("") }
    var sessionIsAdmin by remember { mutableStateOf(false) }

    Scaffold(snackbarHost = { SnackbarHost(snackbarHostState) }, modifier = Modifier.fillMaxSize()) { innerPadding ->
        NavHost(navController = navController, startDestination = "welcome", modifier = Modifier.padding(innerPadding)) {
            composable("welcome") { WelcomeScreen(navController) }

            composable("auth/{mode}") { backStackEntry ->
                val isLoginMode = backStackEntry.arguments?.getString("mode") == "login"
                AuthScreen(
                    isLoginMode = isLoginMode,
                    snackbarHostState = snackbarHostState,
                    onBack = { navController.popBackStack() },
                    onLoginSuccess = { token, username, isAdmin ->
                        sessionToken = "Bearer $token"
                        sessionUsername = username
                        sessionIsAdmin = isAdmin
                        navController.navigate("store") { popUpTo("welcome") { inclusive = true } }
                    }
                )
            }

            composable("store") {
                StoreScreen(token = sessionToken, username = sessionUsername, isAdmin = sessionIsAdmin, navController = navController, snackbarHostState = snackbarHostState)
            }
        }
    }
}

// ================= PANTALLA 1: BIENVENIDA =================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomeScreen(navController: NavController) {
    var menuExpanded by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("GameStore") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, actionIconContentColor = MaterialTheme.colorScheme.onPrimary),
                actions = {
                    IconButton(onClick = { menuExpanded = true }) { Icon(Icons.Default.MoreVert, contentDescription = "Menú") }
                    DropdownMenu(expanded = menuExpanded, onDismissRequest = { menuExpanded = false }) {
                        DropdownMenuItem(text = { Text("Iniciar Sesión") }, onClick = { menuExpanded = false; navController.navigate("auth/login") }, leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) })
                        DropdownMenuItem(text = { Text("Registrarse") }, onClick = { menuExpanded = false; navController.navigate("auth/register") }, leadingIcon = { Icon(Icons.Default.Add, contentDescription = null) })
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier.fillMaxSize().padding(padding).padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // 1. Título principal
                Text(
                    text = "Catálogo Digital",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onBackground
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 2. Imagen en círculo (AQUÍ ESTÁ LA MAGIA)
                androidx.compose.foundation.Image(
                    painter = androidx.compose.ui.res.painterResource(id = R.drawable.img),
                    contentDescription = "Imagen de bienvenida",
                    modifier = Modifier
                        .size(140.dp) // Tamaño del círculo
                        .clip(androidx.compose.foundation.shape.CircleShape), // Recorta la imagen
                    contentScale = androidx.compose.ui.layout.ContentScale.Crop // Ajusta la foto
                )

                Spacer(modifier = Modifier.height(20.dp))

                // 3. Descripción abajo de la imagen
                Text(
                    text = "Bienvenido a la tienda. \nInicia sesión para ver los mejores títulos.",
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Botones inferiores que ya tenías
            Column(modifier = Modifier.fillMaxWidth()) {
                Button(
                    onClick = { navController.navigate("auth/login") },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Iniciar Sesión", fontSize = 16.sp) }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedButton(
                    onClick = { navController.navigate("auth/register") },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
                    shape = RoundedCornerShape(12.dp)
                ) { Text("Crear Cuenta Nueva", fontSize = 16.sp) }
            }
        }
    }
}

// ================= PANTALLA 2: AUTENTICACIÓN =================
@Composable
fun AuthScreen(isLoginMode: Boolean, snackbarHostState: SnackbarHostState, onBack: () -> Unit, onLoginSuccess: (String, String, Boolean) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = if (isLoginMode) "Iniciar Sesión" else "Nueva Cuenta", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario ") },
            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            visualTransformation = PasswordVisualTransformation(),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        if (!isLoginMode) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "💡 Usa al menos 6 caracteres, combinando letras y números.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (username.isBlank() || password.isBlank()) {
                    coroutineScope.launch { snackbarHostState.showSnackbar("Llena todos los campos") }
                    return@Button
                }
                if (!isLoginMode && (username.length < 3 || password.length < 6)) {
                    coroutineScope.launch { snackbarHostState.showSnackbar("Usuario o contraseña no válidos") }
                    return@Button
                }

                val user = User(username = username, password = password, is_admin = false)

                if (isLoginMode) {
                    RetrofitClient.apiService.loginUser(user).enqueue(object : retrofit2.Callback<LoginResponse> {
                        override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                            if (response.isSuccessful && response.body()?.token != null) {
                                val body = response.body()!!
                                onLoginSuccess(body.token!!, body.username ?: "Usuario", body.is_admin ?: false)
                            } else {
                                coroutineScope.launch { snackbarHostState.showSnackbar("No ha sido posible iniciar sesion, intenta de nuevo") }
                            }
                        }
                        override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {}
                    })
                } else {
                    // Registro
                    RetrofitClient.apiService.registerUser(user).enqueue(object : retrofit2.Callback<ApiResponse> {
                        override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                            if (response.isSuccessful) {
                                coroutineScope.launch { snackbarHostState.showSnackbar("Cuenta creada con éxito") }

                                // Auto-login inmediato para entrar directo al portal
                                RetrofitClient.apiService.loginUser(user).enqueue(object : retrofit2.Callback<LoginResponse> {
                                    override fun onResponse(call: retrofit2.Call<LoginResponse>, loginResponse: retrofit2.Response<LoginResponse>) {
                                        if (loginResponse.isSuccessful && loginResponse.body()?.token != null) {
                                            val body = loginResponse.body()!!
                                            onLoginSuccess(body.token!!, body.username ?: username, body.is_admin ?: false)
                                        }
                                    }
                                    override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {}
                                })

                            } else {
                                coroutineScope.launch { snackbarHostState.showSnackbar("El nombre de usuario no está disponible o no es válido") }
                            }
                        }
                        override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {}
                    })
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp), shape = RoundedCornerShape(12.dp)
        ) { Text(if (isLoginMode) "Entrar" else "Crear Cuenta", fontSize = 16.sp) }

        Spacer(modifier = Modifier.height(16.dp))
        TextButton(onClick = { onBack() }) { Text("← Volver", color = MaterialTheme.colorScheme.secondary) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StoreScreen(token: String, username: String, isAdmin: Boolean, navController: NavController, snackbarHostState: SnackbarHostState) {
    var juegos by remember { mutableStateOf(listOf<Videojuego>()) }
    var showDialog by remember { mutableStateOf(false) }
    var showExitDialog by remember { mutableStateOf(false) } // Controla la alerta de salida
    var juegoEnEdicion by remember { mutableStateOf<Videojuego?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // 1. Interceptar el botón de "Atrás" físico del celular para confirmar salida
    BackHandler {
        showExitDialog = true
    }

    fun cargarJuegos() {
        RetrofitClient.apiService.obtenerJuegos(token).enqueue(object : retrofit2.Callback<VideojuegosResponse> {
            override fun onResponse(call: retrofit2.Call<VideojuegosResponse>, response: retrofit2.Response<VideojuegosResponse>) {
                if (response.isSuccessful) { juegos = response.body()?.juegos ?: emptyList() }
            }
            override fun onFailure(call: retrofit2.Call<VideojuegosResponse>, t: Throwable) {}
        })
    }

    LaunchedEffect(Unit) { cargarJuegos() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isAdmin) "Inventario" else "Catálogo de Juegos") },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = MaterialTheme.colorScheme.onPrimary, actionIconContentColor = MaterialTheme.colorScheme.onPrimary),
                actions = {
                    // Botón superior de salida con confirmación
                    IconButton(onClick = { showExitDialog = true }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            if (isAdmin) {
                FloatingActionButton(onClick = { juegoEnEdicion = null; showDialog = true }, containerColor = MaterialTheme.colorScheme.tertiary, contentColor = MaterialTheme.colorScheme.onTertiary) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {
            Text(if (isAdmin) "Bienvenido $username" else "Bienvenido $username", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, modifier = Modifier.padding(bottom = 16.dp))

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(juegos) { juego ->
                    ElevatedCard(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp), shape = RoundedCornerShape(12.dp)) {
                        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(juego.titulo, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                Text(juego.plataforma, style = MaterialTheme.typography.bodyMedium, fontWeight = FontWeight.SemiBold)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text(juego.descripcion, style = MaterialTheme.typography.bodySmall, maxLines = 2, overflow = TextOverflow.Ellipsis)
                                Text("Publicado por: ${juego.vendedor_nombre}", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("$${juego.precio} MXN", color = Color(0xFF2E7D32), fontWeight = FontWeight.ExtraBold)
                            }

                            if (isAdmin) {
                                IconButton(onClick = { juegoEnEdicion = juego; showDialog = true }) { Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.secondary) }
                                IconButton(onClick = {
                                    RetrofitClient.apiService.borrarJuego(token, juego.id!!).enqueue(object : retrofit2.Callback<ApiResponse> {
                                        override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                                            if (response.isSuccessful) { cargarJuegos(); coroutineScope.launch { snackbarHostState.showSnackbar("Juego eliminado") } }
                                        }
                                        override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {}
                                    })
                                }) { Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error) }
                            }
                        }
                    }
                }
            }
        }
    }

    // Modal de confirmación para cerrar sesión / ir atrás
    if (showExitDialog) {
        AlertDialog(
            onDismissRequest = { showExitDialog = false },
            title = { Text("Cerrar Sesión") },
            text = { Text("¿Estás seguro de que deseas cerrar tu sesión?") },
            confirmButton = {
                Button(onClick = {
                    showExitDialog = false
                    navController.navigate("welcome") { popUpTo(0) }
                }) { Text("Sí, salir") }
            },
            dismissButton = {
                TextButton(onClick = { showExitDialog = false }) { Text("Cancelar") }
            }
        )
    }

    // Modal de edición con restricción numérica estricta para el precio
    if (showDialog && isAdmin) {
        var titulo by remember { mutableStateOf(juegoEnEdicion?.titulo ?: "") }
        var plataforma by remember { mutableStateOf(juegoEnEdicion?.plataforma ?: "") }
        var descripcion by remember { mutableStateOf(juegoEnEdicion?.descripcion ?: "") }
        var precio by remember { mutableStateOf(juegoEnEdicion?.precio?.toString() ?: "") }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (juegoEnEdicion == null) "Agregar Videojuego" else "Actualizar") },
            text = {
                Column {
                    OutlinedTextField(value = titulo, onValueChange = { titulo = it }, label = { Text("Título") }, singleLine = true)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = plataforma, onValueChange = { plataforma = it }, label = { Text("Plataforma") }, singleLine = true)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = descripcion, onValueChange = { descripcion = it }, label = { Text("Descripción") }, maxLines = 3)
                    Spacer(modifier = Modifier.height(8.dp))

                    // 2. Campo de precio protegido: solo acepta números y punto decimal
                    OutlinedTextField(
                        value = precio,
                        onValueChange = { input ->
                            if (input.all { it.isDigit() || it == '.' }) {
                                precio = input
                            }
                        },
                        label = { Text("Precio MXN") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        singleLine = true
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (titulo.isBlank() || plataforma.isBlank() || precio.isBlank() || descripcion.isBlank()) {
                        coroutineScope.launch { snackbarHostState.showSnackbar("Llena todos los campos") }
                        return@Button
                    }
                    val numPrecio = precio.toDoubleOrNull() ?: 0.0
                    val nuevo = Videojuego(titulo = titulo, plataforma = plataforma, precio = numPrecio, descripcion = descripcion)
                    val call = if (juegoEnEdicion == null) RetrofitClient.apiService.crearJuego(token, nuevo) else RetrofitClient.apiService.actualizarJuego(token, juegoEnEdicion!!.id!!, nuevo)
                    call.enqueue(object : retrofit2.Callback<ApiResponse> {
                        override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                            if (response.isSuccessful) { cargarJuegos(); showDialog = false }
                        }
                        override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {}
                    })
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
        )
    }
}
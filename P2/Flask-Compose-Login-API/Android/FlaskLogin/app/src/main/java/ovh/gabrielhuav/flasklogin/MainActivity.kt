package ovh.gabrielhuav.flasklogin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.*
import kotlinx.coroutines.launch
import ovh.gabrielhuav.flasklogin.ui.theme.FlaskLoginTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FlaskLoginTheme {
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

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "role_selection",
            modifier = Modifier.padding(innerPadding)
        ) {
            // PANTALLA 1: ELEGIR ROL
            composable("role_selection") {
                RoleSelectionScreen(navController)
            }

            // PANTALLA 2: LOGIN / REGISTRO (Dinámico según el rol elegido)
            composable("auth/{role}") { backStackEntry ->
                val role = backStackEntry.arguments?.getString("role") ?: "alumno"
                val isTeacherMode = role == "profesor"

                AuthScreen(
                    isTeacherMode = isTeacherMode,
                    snackbarHostState = snackbarHostState,
                    onBack = { navController.popBackStack() },
                    onLoginSuccess = { token, username, isAdmin ->
                        sessionToken = "Bearer $token"
                        sessionUsername = username
                        sessionIsAdmin = isAdmin
                        navController.navigate("home") {
                            popUpTo("role_selection") { inclusive = true }
                        }
                    }
                )
            }

            // PANTALLA 3: EL CRUD
            composable("home") {
                HomeScreen(
                    token = sessionToken,
                    username = sessionUsername,
                    isAdmin = sessionIsAdmin,
                    navController = navController,
                    snackbarHostState = snackbarHostState
                )
            }
        }
    }
}

// ================== PANTALLA 1: SELECCIÓN DE ROL ==================
@Composable
fun RoleSelectionScreen(navController: NavController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Default.AccountCircle,
            contentDescription = "Logo",
            modifier = Modifier.size(100.dp),
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Bienvenido al Sistema",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )
        Text(
            text = "Selecciona tu perfil para continuar",
            fontSize = 16.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(bottom = 32.dp)
        )

        RoleCard(
            title = "Profesor",
            description = "Acceso total para gestionar a los alumnos",
            icon = Icons.Default.Star,
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            onClick = { navController.navigate("auth/profesor") }
        )

        Spacer(modifier = Modifier.height(16.dp))

        RoleCard(
            title = "Alumno",
            description = "Acceso para completar tu perfil estudiantil",
            icon = Icons.Default.Person,
            containerColor = MaterialTheme.colorScheme.secondaryContainer,
            contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
            onClick = { navController.navigate("auth/alumno") }
        )
    }
}

@Composable
fun RoleCard(title: String, description: String, icon: androidx.compose.ui.graphics.vector.ImageVector, containerColor: Color, contentColor: Color, onClick: () -> Unit) {
    ElevatedCard(
        modifier = Modifier.fillMaxWidth().height(120.dp).clickable { onClick() },
        colors = CardDefaults.elevatedCardColors(containerColor = containerColor),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(48.dp), tint = contentColor)
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(text = title, fontSize = 20.sp, fontWeight = FontWeight.Bold, color = contentColor)
                Text(text = description, fontSize = 14.sp, color = contentColor.copy(alpha = 0.8f))
            }
        }
    }
}

// ================== PANTALLA 2: AUTENTICACIÓN ==================
@Composable
fun AuthScreen(
    isTeacherMode: Boolean,
    snackbarHostState: SnackbarHostState,
    onBack: () -> Unit,
    onLoginSuccess: (String, String, Boolean) -> Unit
) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoginMode by remember { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = if (isTeacherMode) "Portal de Profesores" else "Portal de Alumnos",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.primary
        )
        Text(
            text = if (isLoginMode) "Inicia sesión en tu cuenta" else "Crea una cuenta nueva",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Usuario") },
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

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = {
                if (username.isBlank() || password.isBlank()) {
                    coroutineScope.launch { snackbarHostState.showSnackbar("Llena todos los campos") }
                    return@Button
                }

                // Aquí enviamos el isTeacherMode para que Flask sepa si darle poder de Admin o no
                val user = User(username = username, password = password, is_admin = isTeacherMode)

                if (isLoginMode) {
                    RetrofitClient.apiService.loginUser(user).enqueue(object : retrofit2.Callback<LoginResponse> {
                        override fun onResponse(call: retrofit2.Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                            if (response.isSuccessful && response.body()?.token != null) {
                                val body = response.body()!!
                                onLoginSuccess(body.token!!, body.username ?: "Usuario", body.is_admin ?: false)
                            } else {
                                coroutineScope.launch { snackbarHostState.showSnackbar("Error de acceso: Verifica tu rol o contraseña") }
                            }
                        }
                        override fun onFailure(call: retrofit2.Call<LoginResponse>, t: Throwable) {
                            coroutineScope.launch { snackbarHostState.showSnackbar("Fallo de conexión al servidor") }
                        }
                    })
                } else {
                    RetrofitClient.apiService.registerUser(user).enqueue(object : retrofit2.Callback<ApiResponse> {
                        override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                            if (response.isSuccessful) {
                                coroutineScope.launch { snackbarHostState.showSnackbar("¡Registrado! Ahora inicia sesión.") }
                                isLoginMode = true
                            } else {
                                coroutineScope.launch { snackbarHostState.showSnackbar("El usuario ya existe") }
                            }
                        }
                        override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {}
                    })
                }
            },
            modifier = Modifier.fillMaxWidth().height(50.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(if (isLoginMode) "Entrar" else "Registrarme", fontSize = 16.sp)
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = { isLoginMode = !isLoginMode }) {
            Text(if (isLoginMode) "¿No tienes cuenta? Regístrate" else "¿Ya tienes cuenta? Inicia sesión")
        }

        TextButton(onClick = { onBack() }) {
            Text("← Cambiar de Rol", color = MaterialTheme.colorScheme.secondary)
        }
    }
}

// ================== PANTALLA 3: PANEL PRINCIPAL (CRUD) ==================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    token: String,
    username: String,
    isAdmin: Boolean,
    navController: NavController,
    snackbarHostState: SnackbarHostState
) {
    var alumnos by remember { mutableStateOf(listOf<Alumno>()) }
    var showDialog by remember { mutableStateOf(false) }
    var alumnoEnEdicion by remember { mutableStateOf<Alumno?>(null) }
    val coroutineScope = rememberCoroutineScope()

    fun cargarAlumnos() {
        RetrofitClient.apiService.obtenerAlumnos(token).enqueue(object : retrofit2.Callback<AlumnosResponse> {
            override fun onResponse(call: retrofit2.Call<AlumnosResponse>, response: retrofit2.Response<AlumnosResponse>) {
                if (response.isSuccessful) { alumnos = response.body()?.alumnos ?: emptyList() }
            }
            override fun onFailure(call: retrofit2.Call<AlumnosResponse>, t: Throwable) {}
        })
    }

    LaunchedEffect(Unit) { cargarAlumnos() }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (isAdmin) "Panel de Control" else "Mi Perfil") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                actions = {
                    IconButton(onClick = { navController.navigate("role_selection") { popUpTo(0) } }) {
                        Icon(Icons.Default.ExitToApp, contentDescription = "Cerrar Sesión")
                    }
                }
            )
        },
        floatingActionButton = {
            // Solo mostramos el botón flotante si es admin o si el alumno aún no tiene registro
            if (isAdmin || alumnos.isEmpty()) {
                FloatingActionButton(
                    onClick = { alumnoEnEdicion = null; showDialog = true },
                    containerColor = MaterialTheme.colorScheme.tertiary,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Agregar")
                }
            }
        }
    ) { innerPadding ->
        Column(modifier = Modifier.fillMaxSize().padding(innerPadding).padding(16.dp)) {

            Text(
                text = "Hola, $username",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            LazyColumn(modifier = Modifier.weight(1f)) {
                items(alumnos) { alumno ->
                    ElevatedCard(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
                        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 4.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(16.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column(modifier = Modifier.weight(1f)) {
                                Text(alumno.nombre, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                                Text("Matrícula: ${alumno.matricula}", style = MaterialTheme.typography.bodyMedium)
                                Spacer(modifier = Modifier.height(4.dp))
                                Badge(containerColor = if (alumno.inscrito) Color(0xFF4CAF50) else Color(0xFFF44336)) {
                                    Text(
                                        text = if (alumno.inscrito) "Activo" else "Baja",
                                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                                        color = Color.White
                                    )
                                }
                            }

                            IconButton(onClick = { alumnoEnEdicion = alumno; showDialog = true }) {
                                Icon(Icons.Default.Edit, contentDescription = "Editar", tint = MaterialTheme.colorScheme.primary)
                            }

                            if (isAdmin) {
                                IconButton(onClick = {
                                    RetrofitClient.apiService.borrarAlumno(token, alumno.id!!).enqueue(object : retrofit2.Callback<ApiResponse> {
                                        override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                                            if (response.isSuccessful) {
                                                cargarAlumnos()
                                                coroutineScope.launch { snackbarHostState.showSnackbar("Registro eliminado") }
                                            }
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

    // --- FORMULARIO DIALOG ---
    if (showDialog) {
        var nombre by remember { mutableStateOf(alumnoEnEdicion?.nombre ?: "") }
        var matricula by remember { mutableStateOf(alumnoEnEdicion?.matricula ?: "") }
        var inscrito by remember { mutableStateOf(alumnoEnEdicion?.inscrito ?: true) }

        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(if (alumnoEnEdicion == null) "Datos del Alumno" else "Editar Datos") },
            text = {
                Column {
                    OutlinedTextField(value = nombre, onValueChange = { nombre = it }, label = { Text("Nombre completo") }, singleLine = true)
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(value = matricula, onValueChange = { matricula = it }, label = { Text("Matrícula") }, singleLine = true)
                    Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(top = 8.dp)) {
                        Checkbox(checked = inscrito, onCheckedChange = { inscrito = it })
                        Text("¿Está inscrito activamente?")
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (nombre.isBlank() || matricula.isBlank()) {
                        coroutineScope.launch { snackbarHostState.showSnackbar("Llena los campos requeridos") }
                        return@Button
                    }
                    val nuevo = Alumno(nombre = nombre, matricula = matricula, inscrito = inscrito)
                    val call = if (alumnoEnEdicion == null) RetrofitClient.apiService.crearAlumno(token, nuevo)
                    else RetrofitClient.apiService.actualizarAlumno(token, alumnoEnEdicion!!.id!!, nuevo)

                    call.enqueue(object : retrofit2.Callback<ApiResponse> {
                        override fun onResponse(call: retrofit2.Call<ApiResponse>, response: retrofit2.Response<ApiResponse>) {
                            if (response.isSuccessful) {
                                cargarAlumnos()
                                showDialog = false
                            } else {
                                coroutineScope.launch { snackbarHostState.showSnackbar("La matrícula ya está registrada o datos inválidos") }
                            }
                        }
                        override fun onFailure(call: retrofit2.Call<ApiResponse>, t: Throwable) {
                            coroutineScope.launch { snackbarHostState.showSnackbar("Error de conexión") }
                        }
                    })
                }) { Text("Guardar") }
            },
            dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
        )
    }
}
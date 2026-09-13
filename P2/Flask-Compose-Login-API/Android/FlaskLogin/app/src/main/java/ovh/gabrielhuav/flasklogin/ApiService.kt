package ovh.gabrielhuav.flasklogin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.*

data class User(
    val username: String,
    val password: String,
    val is_admin: Boolean = false
)
data class ApiResponse(val message: String)

interface ApiService {
    @POST("register")
    fun registerUser(@Body user: User): Call<ApiResponse>

    @POST("login")
    fun loginUser(@Body user: User): Call<LoginResponse>

    // --- RUTAS CRUD (PROTEGIDAS CON TOKEN) ---

    @GET("alumnos")
    fun obtenerAlumnos(@Header("Authorization") token: String): Call<AlumnosResponse>

    @POST("alumnos")
    fun crearAlumno(@Header("Authorization") token: String, @Body alumno: Alumno): Call<ApiResponse>

    @PUT("alumnos/{id}")
    fun actualizarAlumno(
        @Header("Authorization") token: String,
        @Path("id") id: Int,
        @Body alumno: Alumno
    ): Call<ApiResponse>

    @DELETE("alumnos/{id}")
    fun borrarAlumno(@Header("Authorization") token: String, @Path("id") id: Int): Call<ApiResponse>
}
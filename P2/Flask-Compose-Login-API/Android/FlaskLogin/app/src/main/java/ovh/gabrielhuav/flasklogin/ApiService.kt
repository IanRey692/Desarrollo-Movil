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
    @GET("juegos")
    fun obtenerJuegos(@Header("Authorization") token: String): Call<VideojuegosResponse>
    @POST("juegos")
    fun crearJuego(@Header("Authorization") token: String, @Body juego: Videojuego): Call<ApiResponse>
    @PUT("juegos/{id}")
    fun actualizarJuego(@Header("Authorization") token: String, @Path("id") id: Int, @Body juego: Videojuego): Call<ApiResponse>
    @DELETE("juegos/{id}")
    fun borrarJuego(@Header("Authorization") token: String, @Path("id") id: Int): Call<ApiResponse>
}
package ovh.gabrielhuav.flasklogin

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class User(val username: String, val password: String)
data class ApiResponse(val message: String)

interface ApiService {
    @POST("register")
    fun registerUser(@Body user: User): Call<ApiResponse>

    @POST("login")
    fun loginUser(@Body user: User): Call<ApiResponse>
}
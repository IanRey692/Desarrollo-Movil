package ovh.gabrielhuav.flasklogin

data class LoginResponse(
    val status: String?,
    val message: String,
    val token: String?,
    val username: String?,
    val is_admin: Boolean?
)
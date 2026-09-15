package ovh.gabrielhuav.flasklogin

data class Videojuego(
    val id: Int? = null,
    val titulo: String,
    val plataforma: String,
    val precio: Double,
    val descripcion: String = "",
    val vendedor_nombre: String = "Desconocido"
)

data class VideojuegosResponse(
    val juegos: List<Videojuego>
)
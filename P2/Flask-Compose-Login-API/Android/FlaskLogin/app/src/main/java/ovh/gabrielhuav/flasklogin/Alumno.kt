package ovh.gabrielhuav.flasklogin

data class Alumno(
    val id: Int? = null,
    val nombre: String,
    val matricula: String,
    val inscrito: Boolean = true,
    val owner_id: Int? = null
)

data class AlumnosResponse(
    val alumnos: List<Alumno>
)
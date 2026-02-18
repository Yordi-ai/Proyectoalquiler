package com.example.proyectomaquinaria.data.model

data class User(
    val uid: String = "",
    val email: String = "",
    val nombre: String = "",
    val telefono: String = "",
    val tipoUsuario: TipoUsuario = TipoUsuario.CLIENTE,
    val foto: String = "",
    val calificacion: Float = 0f,
    val ubicacion: Ubicacion? = null,
    val createdAt: Long = System.currentTimeMillis()
)

enum class TipoUsuario {
    CLIENTE,
    PROVEEDOR
}

data class Ubicacion(
    val latitud: Double = 0.0,
    val longitud: Double = 0.0,
    val direccion: String = ""
)
package com.example.taller3.clases

data class Usuario(
    var email: String = "",
    var nombre: String = "",
    var apellido: String = "",
    var id: Int = 0,
    var latitud: Double = 0.0,
    var longitud: Double = 0.0,
    var uid: String? = null,
    var imagenDir: String = "",
    var disponible: Boolean = false
)
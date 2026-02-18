package com.example.proyectomaquinaria.core.utils

object Constants {

    // Supabase Configuration
    const val SUPABASE_URL = "https://clfxnhbelpomkxzexmfp.supabase.co"
    const val SUPABASE_ANON_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNsZnhuaGJlbHBvbWt4emV4bWZwIiwicm9sZSI6ImFub24iLCJpYXQiOjE3NzEwMTMzNjEsImV4cCI6MjA4NjU4OTM2MX0.BP9vKCJbqjJhpPF7Jl6myTWnRCZgb-OsaW0FTi__xlU"

    // API Configuration
    const val BASE_URL = "http://tu-servidor.com/api/"
    const val TIMEOUT_SECONDS = 30L

    // SharedPreferences Keys
    const val PREFS_NAME = "MaquiRentPrefs"
    const val KEY_USER_TOKEN = "user_token"
    const val KEY_USER_TYPE = "user_type"
    const val KEY_IS_LOGGED_IN = "is_logged_in"
    const val KEY_USER_ID = "user_id"
    const val KEY_USER_EMAIL = "user_email"

    // User Types
    const val USER_TYPE_CLIENT = "cliente"
    const val USER_TYPE_PROVIDER = "proveedor"

    // Request Codes
    const val REQUEST_LOCATION_PERMISSION = 1001
    const val REQUEST_CAMERA_PERMISSION = 1002

    // Google Maps
    const val DEFAULT_ZOOM = 15f
    const val DEFAULT_LAT = -7.1638  // Cajamarca, Perú
    const val DEFAULT_LNG = -78.5126

    // Tipos de Maquinaria
    val TIPOS_MAQUINARIA = listOf(
        "Excavadora",
        "Retroexcavadora",
        "Cargador Frontal",
        "Motoniveladora",
        "Rodillo Compactador",
        "Volquete",
        "Grúa",
        "Minicargador"
    )

    // Tipos de Agregados
    val TIPOS_AGREGADOS = listOf(
        "Arena Gruesa",
        "Arena Fina",
        "Piedra Chancada 1/2\"",
        "Piedra Chancada 3/4\"",
        "Hormigón",
        "Confitillo",
        "Afirmado"
    )
}
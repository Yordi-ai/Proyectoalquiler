package com.example.proyectomaquinaria.core.utils

import android.content.Context
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * EXTENSIONES PARA CONTEXT
 */

// Mostrar Toast corto
fun Context.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

// Mostrar Toast largo
fun Context.showLongToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

/**
 * EXTENSIONES PARA VIEW
 */

// Mostrar vista
fun View.visible() {
    this.visibility = View.VISIBLE
}

// Ocultar vista
fun View.gone() {
    this.visibility = View.GONE
}

// Invisible (ocupa espacio pero no se ve)
fun View.invisible() {
    this.visibility = View.INVISIBLE
}

// Mostrar Snackbar
fun View.showSnackbar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

// Snackbar con acción
fun View.showSnackbarWithAction(
    message: String,
    actionText: String,
    action: () -> Unit
) {
    Snackbar.make(this, message, Snackbar.LENGTH_LONG)
        .setAction(actionText) { action() }
        .show()
}

/**
 * EXTENSIONES PARA STRING
 */

// Validar email
fun String.isValidEmail(): Boolean {
    val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    return this.matches(emailRegex.toRegex())
}

// Validar teléfono peruano (9 dígitos)
fun String.isValidPhone(): Boolean {
    val phoneRegex = "^9\\d{8}$"
    return this.matches(phoneRegex.toRegex())
}

// Validar RUC peruano (11 dígitos)
fun String.isValidRuc(): Boolean {
    val rucRegex = "^\\d{11}$"
    return this.matches(rucRegex.toRegex())
}

/**
 * EXTENSIONES PARA DOUBLE
 */

// Formatear a moneda peruana (S/)
fun Double.toSolesFormat(): String {
    val format = NumberFormat.getCurrencyInstance(Locale("es", "PE"))
    return format.format(this).replace("PEN", "S/")
}

// Ejemplo: 450.50 → "S/ 450.50"

/**
 * EXTENSIONES PARA DATE
 */

// Formatear fecha a texto legible
fun Date.toFormattedString(pattern: String = "dd/MM/yyyy"): String {
    val formatter = SimpleDateFormat(pattern, Locale("es", "PE"))
    return formatter.format(this)
}

// Ejemplo: Date() → "12/02/2026"

/**
 * EXTENSIONES PARA LONG (timestamp)
 */

// Convertir timestamp a fecha legible
fun Long.toDateString(pattern: String = "dd/MM/yyyy HH:mm"): String {
    val date = Date(this)
    val formatter = SimpleDateFormat(pattern, Locale("es", "PE"))
    return formatter.format(date)
}
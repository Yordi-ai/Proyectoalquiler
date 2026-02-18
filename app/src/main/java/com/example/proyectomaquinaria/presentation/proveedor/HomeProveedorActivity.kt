package com.example.proyectomaquinaria.presentation.proveedor

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectomaquinaria.R
import com.example.proyectomaquinaria.databinding.ActivityHomeProveedorBinding
import com.example.proyectomaquinaria.presentation.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth

class HomeProveedorActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeProveedorBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeProveedorBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()

        setupToolbar()
        setupBottomNavigation()

        Toast.makeText(this, "¡Bienvenido Proveedor!", Toast.LENGTH_SHORT).show()
    }

    private fun setupToolbar() {
        setSupportActionBar(binding.toolbar)
        binding.toolbar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.action_notifications -> {
                    Toast.makeText(this, "Notificaciones", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.action_logout -> {
                    showLogoutDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    Toast.makeText(this, "Inicio", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_services -> {
                    Toast.makeText(this, "Mis Servicios", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_requests -> {
                    Toast.makeText(this, "Solicitudes Recibidas", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.nav_profile -> {
                    // Mostrar diálogo de cerrar sesión cuando toca Perfil
                    showLogoutDialog()
                    true
                }
                else -> false
            }
        }
    }

    private fun showLogoutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar Sesión")
            .setMessage("¿Estás seguro que deseas cerrar sesión?")
            .setPositiveButton("Sí") { _, _ ->
                logout()
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun logout() {
        auth.signOut()
        val intent = Intent(this, LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }

    override fun onBackPressed() {
        showLogoutDialog()
    }
}
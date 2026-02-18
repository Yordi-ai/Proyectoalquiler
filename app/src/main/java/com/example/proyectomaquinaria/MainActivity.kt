package com.example.proyectomaquinaria.presentation.main


import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectomaquinaria.databinding.ActivityMainBinding
import com.example.proyectomaquinaria.data.model.TipoUsuario
import com.example.proyectomaquinaria.presentation.auth.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        loadUserData()
        setupListeners()
    }

    private fun loadUserData() {
        val currentUser = auth.currentUser ?: return

        firestore.collection("users")
            .document(currentUser.uid)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val tipoUsuario = document.getString("tipoUsuario")
                    val email = document.getString("email")

                    val tipo = when (tipoUsuario) {
                        "PROVEEDOR" -> "PROVEEDOR 🔧"
                        else -> "CLIENTE 🔍"
                    }

                    binding.tvWelcome.text = "¡Bienvenido!\n$tipo\n$email"
                }
            }
    }

    private fun setupListeners() {
        binding.btnLogout.setOnClickListener {
            auth.signOut()
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}
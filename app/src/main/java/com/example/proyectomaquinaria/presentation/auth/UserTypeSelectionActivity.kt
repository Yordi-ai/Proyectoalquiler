package com.example.proyectomaquinaria.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectomaquinaria.databinding.ActivityUserTypeSelectionBinding
import com.example.proyectomaquinaria.data.model.TipoUsuario
import com.example.proyectomaquinaria.data.model.User
import com.example.proyectomaquinaria.presentation.cliente.HomeClienteActivity
import com.example.proyectomaquinaria.presentation.proveedor.HomeProveedorActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class UserTypeSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserTypeSelectionBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserTypeSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        setupListeners()
    }

    private fun setupListeners() {
        binding.cardCliente.setOnClickListener {
            Log.d("UserTypeSelection", "Cliente card clicked")
            crearPerfil(TipoUsuario.CLIENTE)
        }

        binding.cardProveedor.setOnClickListener {
            Log.d("UserTypeSelection", "Proveedor card clicked")
            crearPerfil(TipoUsuario.PROVEEDOR)
        }
    }

    private fun crearPerfil(tipoUsuario: TipoUsuario) {
        val currentUser = auth.currentUser

        if (currentUser == null) {
            Toast.makeText(this, "Error: No hay usuario autenticado", Toast.LENGTH_SHORT).show()
            return
        }

        Log.d("UserTypeSelection", "Creating profile for: ${currentUser.email} as $tipoUsuario")

        val user = User(
            uid = currentUser.uid,
            email = currentUser.email ?: "",
            tipoUsuario = tipoUsuario
        )

        // Mostrar loading
        Toast.makeText(this, "Guardando perfil...", Toast.LENGTH_SHORT).show()

        // Guardar en Firestore
        firestore.collection("users")
            .document(currentUser.uid)
            .set(user)
            .addOnSuccessListener {
                Log.d("UserTypeSelection", "Profile saved successfully")
                Toast.makeText(this, "¡Perfil creado!", Toast.LENGTH_SHORT).show()
                navigateToHome(tipoUsuario)
            }
            .addOnFailureListener { e ->
                Log.e("UserTypeSelection", "Error saving profile", e)
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }

    private fun navigateToHome(tipoUsuario: TipoUsuario) {
        Log.d("UserTypeSelection", "Navigating to home for: $tipoUsuario")

        val intent = when (tipoUsuario) {
            TipoUsuario.CLIENTE -> {
                Log.d("UserTypeSelection", "Starting HomeClienteActivity")
                Intent(this, HomeClienteActivity::class.java)
            }
            TipoUsuario.PROVEEDOR -> {
                Log.d("UserTypeSelection", "Starting HomeProveedorActivity")
                Intent(this, HomeProveedorActivity::class.java)
            }
        }

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
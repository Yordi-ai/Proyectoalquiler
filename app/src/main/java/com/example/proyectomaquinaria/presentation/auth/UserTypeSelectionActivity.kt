package com.example.proyectomaquinaria.presentation.auth

import android.content.Intent
import android.os.Bundle
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
            crearPerfil(TipoUsuario.CLIENTE)
        }

        binding.cardProveedor.setOnClickListener {
            crearPerfil(TipoUsuario.PROVEEDOR)
        }
    }

    private fun crearPerfil(tipoUsuario: TipoUsuario) {
        val currentUser = auth.currentUser ?: return

        val user = User(
            uid = currentUser.uid,
            email = currentUser.email ?: "",
            tipoUsuario = tipoUsuario
        )

        firestore.collection("users")
            .document(currentUser.uid)
            .set(user)
            .addOnSuccessListener {
                Toast.makeText(
                    this,
                    "¡Perfil creado exitosamente!",
                    Toast.LENGTH_SHORT
                ).show()
                navigateToHome(tipoUsuario)
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun navigateToHome(tipoUsuario: TipoUsuario) {
        val intent = when (tipoUsuario) {
            TipoUsuario.CLIENTE -> Intent(this, HomeClienteActivity::class.java)
            TipoUsuario.PROVEEDOR -> Intent(this, HomeProveedorActivity::class.java)
        }
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
package com.example.proyectomaquinaria.presentation.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.proyectomaquinaria.R
import com.example.proyectomaquinaria.presentation.auth.LoginActivity
import com.example.proyectomaquinaria.presentation.auth.UserTypeSelectionActivity
import com.example.proyectomaquinaria.presentation.cliente.HomeClienteActivity
import com.example.proyectomaquinaria.presentation.proveedor.HomeProveedorActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            checkUserStatus()
        }, 2000)
    }

    private fun checkUserStatus() {
        val currentUser = auth.currentUser

        if (currentUser != null) {
            firestore.collection("users")
                .document(currentUser.uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document.exists()) {
                        val tipoUsuario = document.getString("tipoUsuario")
                        val intent = when (tipoUsuario) {
                            "PROVEEDOR" -> Intent(this, HomeProveedorActivity::class.java)
                            else -> Intent(this, HomeClienteActivity::class.java)
                        }
                        startActivity(intent)
                    } else {
                        startActivity(Intent(this, UserTypeSelectionActivity::class.java))
                    }
                    finish()
                }
                .addOnFailureListener {
                    startActivity(Intent(this, UserTypeSelectionActivity::class.java))
                    finish()
                }
        } else {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}
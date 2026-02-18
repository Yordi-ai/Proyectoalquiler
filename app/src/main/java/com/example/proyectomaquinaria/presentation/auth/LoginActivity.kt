package com.example.proyectomaquinaria.presentation.auth

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.proyectomaquinaria.databinding.ActivityLoginBinding
import com.example.proyectomaquinaria.presentation.main.MainActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private var isLoginMode = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializar Firebase Auth
        auth = FirebaseAuth.getInstance()

        setupListeners()
    }

    private fun setupListeners() {
        binding.btnLogin.setOnClickListener {
            val email = binding.etEmail.text.toString().trim()
            val password = binding.etPassword.text.toString()

            if (isLoginMode) {
                performLogin(email, password)
            } else {
                val confirmPassword = binding.etConfirmPassword.text.toString()
                performRegister(email, password, confirmPassword)
            }
        }

        binding.tvToggleMode.setOnClickListener {
            if (isLoginMode) {
                switchToRegisterMode()
            } else {
                switchToLoginMode()
            }
        }
    }

    private fun performLogin(email: String, password: String) {
        // Validaciones
        if (email.isBlank() || password.isBlank()) {
            Toast.makeText(this, "Email y contraseña son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show()
            return
        }

        // Login con Firebase
        showLoading(true)
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    Toast.makeText(this, "¡Bienvenido!", Toast.LENGTH_SHORT).show()
                    navigateToMain()
                } else {
                    val errorMessage = when {
                        task.exception?.message?.contains("password") == true ->
                            "Contraseña incorrecta"
                        task.exception?.message?.contains("no user") == true ->
                            "Usuario no encontrado"
                        task.exception?.message?.contains("network") == true ->
                            "Error de conexión"
                        else ->
                            "Error al iniciar sesión: ${task.exception?.message}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun performRegister(email: String, password: String, confirmPassword: String) {
        if (email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
            Toast.makeText(this, "Todos los campos son obligatorios", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(this, "Email no válido", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.length < 6) {
            Toast.makeText(this, "La contraseña debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
            return
        }

        if (password != confirmPassword) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            return
        }

        showLoading(true)
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                showLoading(false)
                if (task.isSuccessful) {
                    // Ir a selección de tipo de usuario
                    val intent = Intent(this, UserTypeSelectionActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    val errorMessage = when {
                        task.exception?.message?.contains("already in use") == true ->
                            "Este email ya está registrado"
                        task.exception?.message?.contains("network") == true ->
                            "Error de conexión"
                        else ->
                            "Error: ${task.exception?.message}"
                    }
                    Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
                }
            }
    }
    private fun switchToRegisterMode() {
        isLoginMode = false
        binding.tvTitle.text = "Crear Cuenta"
        binding.btnLogin.text = "Registrarse"
        binding.tilConfirmPassword.visibility = View.VISIBLE
        binding.tvToggleMode.text = "¿Ya tienes cuenta? Inicia sesión"
        clearFields()
    }

    private fun switchToLoginMode() {
        isLoginMode = true
        binding.tvTitle.text = "Iniciar Sesión"
        binding.btnLogin.text = "Iniciar Sesión"
        binding.tilConfirmPassword.visibility = View.GONE
        binding.tvToggleMode.text = "¿No tienes cuenta? Regístrate"
        clearFields()
    }

    private fun clearFields() {
        binding.etEmail.text?.clear()
        binding.etPassword.text?.clear()
        binding.etConfirmPassword.text?.clear()
    }

    private fun showLoading(show: Boolean) {
        binding.progressBar.visibility = if (show) View.VISIBLE else View.GONE
        binding.btnLogin.isEnabled = !show
        binding.etEmail.isEnabled = !show
        binding.etPassword.isEnabled = !show
        binding.etConfirmPassword.isEnabled = !show
    }

    private fun navigateToMain() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}
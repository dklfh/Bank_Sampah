package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import com.google.firebase.auth.FirebaseAuth
import androidx.constraintlayout.motion.widget.MotionLayout

class Splash_screen : AppCompatActivity() {
    private lateinit var firebaseAuth: FirebaseAuth

    companion object {
        private const val PREFS_NAME = "LoginPrefs"
        private const val IS_LOGGED_IN = "isLoggedIn"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        firebaseAuth = FirebaseAuth.getInstance()

        // Lakukan pengecekan data tanpa memunculkan halaman login pertama
        if (isLoggedIn()) {
            // Cek apakah akun masih valid
            firebaseAuth.currentUser?.reload()?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Jika akun masih valid, arahkan ke halaman kalkulator
                    navigateToCalculator()
                } else {
                    // Jika akun tidak valid, arahkan ke halaman login
                    navigateToLogin()
                }
            }
        } else {
            // Jika belum login, arahkan ke halaman login
            navigateToLogin()
        }

//         You can optionally add a delay before moving to the next screen
        Handler(mainLooper).postDelayed({
            val motionLayout = findViewById<MotionLayout>(R.id.motion_layout)
            motionLayout.transitionToEnd() //
        }, 5000) // Delay for 2 seconds
    }

    private fun startMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

    private fun isLoggedIn(): Boolean {
        val prefs = getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
        return prefs.getBoolean(IS_LOGGED_IN, false)
    }

    private fun navigateToCalculator() {
        val intent = Intent(this, cobanavbar::class.java)
        startActivity(intent)
        finish()
    }

    private fun navigateToLogin() {
        // Mengarahkan pengguna ke halaman login
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
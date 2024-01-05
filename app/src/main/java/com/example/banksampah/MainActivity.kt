package com.example.banksampah

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.Toast.LENGTH_SHORT
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthCredential
import com.google.firebase.auth.GoogleAuthProvider

class MainActivity : AppCompatActivity() {
    lateinit var btngoogle : Button
    lateinit var btnfacebook : Button
    lateinit var btntwitter : Button
    lateinit var googleSignInClient : GoogleSignInClient
    lateinit var progressDialog: ProgressDialog

    var firebaseAuth = FirebaseAuth.getInstance()

    companion object {
        private const val RC_SIGN_IN = 1001
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btngoogle = findViewById(R.id.btn_google)
        btnfacebook = findViewById(R.id.btn_facebook)
        btntwitter = findViewById(R.id.btn_twitter)

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Logging")
        progressDialog.setMessage("Silahkan Tunggu...")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        btngoogle.setOnClickListener{
            val signInClient = googleSignInClient.signInIntent
            startActivityForResult(signInClient, RC_SIGN_IN)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            //MENANGANI PROSES LOGIN GOOGLE
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                //JIKA BERHASIL
                val account = task.getResult(ApiException::class.java)!!
                firebaseAutWithGoogle(account.idToken!!)
            }catch (e: ApiException) {
                e.printStackTrace()
                Toast.makeText(applicationContext, e.localizedMessage, LENGTH_SHORT).show()
            }
        }
    }

    private fun firebaseAutWithGoogle(idtoken: String) {
        progressDialog.show()
        val credential = GoogleAuthProvider.getCredential(idtoken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnSuccessListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            .addOnFailureListener{ error ->
                Toast.makeText(this, error.localizedMessage, LENGTH_SHORT).show()
            }
            .addOnCompleteListener{
                progressDialog.dismiss()
            }
    }
}
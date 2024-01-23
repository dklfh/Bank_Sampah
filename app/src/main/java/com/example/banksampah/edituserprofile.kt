package com.example.banksampah

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.example.banksampah.databinding.ActivityEdituserprofileBinding
import com.example.banksampah.databinding.ActivityMainBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class edituserprofile : AppCompatActivity() {

    private lateinit var binding: ActivityEdituserprofileBinding
    private lateinit var auth:FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityEdituserprofileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth=FirebaseAuth.getInstance()
        val uid =auth.currentUser?.uid
        databaseReference=FirebaseDatabase.getInstance().getReference("user")
        binding.saveBtn.setOnClickListener{

            val username=binding.username.text.toString()
            val user=user(username)
            if (uid!=null){
                databaseReference.child(uid).setValue(user).addOnCompleteListener{
                    if(it.isSuccessful){

                    }else{
                        Toast.makeText(this@edituserprofile,"gagal update profile",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
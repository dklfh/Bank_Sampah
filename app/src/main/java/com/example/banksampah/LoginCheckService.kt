package com.example.banksampah

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.auth.FirebaseAuth

class LoginCheckService : JobIntentService() {

    private val mAuth = FirebaseAuth.getInstance()

    companion object {
        private const val JOB_ID = 1001

        fun enqueueWork(context: Context, intent: Intent) {
            enqueueWork(context, LoginCheckService::class.java, JOB_ID, intent)
        }
    }

    override fun onHandleWork(intent: Intent) {
        if (mAuth.currentUser != null) {
            // User is already logged in, send a broadcast to the MainActivity
            LocalBroadcastManager.getInstance(this)
                .sendBroadcast(Intent("com.example.banksampah.LOGIN_SUCCESS"))
        }
    }
}

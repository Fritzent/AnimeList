package com.example.animelist

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent
import android.os.Handler
import android.os.Looper

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Handler(Looper.getMainLooper()).postDelayed({
            // Your Code
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            finish()
        }, 3000)
    }
}
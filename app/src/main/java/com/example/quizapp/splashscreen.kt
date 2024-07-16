package com.example.quizapp

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity



class Splashscreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splashscreen)

        //for holding screen

       Handler(Looper.getMainLooper()).postDelayed({
           val intent= Intent(this,MainActivity::class.java)
           startActivity(intent)
           finish()
       },3000)

    }
}
package com.gailo22.module1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class Module1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_module1)

        val intent: Intent = Intent(this, Module1Service::class.java)
        startService(intent)
    }
}

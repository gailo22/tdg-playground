package com.gailo22.app2

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager

class MainActivity : AppCompatActivity() {

    val ACTION_HELLO = "com.gailo22.app1.HELLO"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intentFilter = IntentFilter()
        intentFilter.addAction(ACTION_HELLO)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)

        registerReceiver(broadCastReceiver, intentFilter)
    }

    override fun onDestroy() {
        super.onDestroy()

        unregisterReceiver(broadCastReceiver)
    }

    val broadCastReceiver = object : BroadcastReceiver() {
        override fun onReceive(contxt: Context?, intent: Intent?) {

            println("onReceive")
            when (intent?.action) {
                ACTION_HELLO -> handleHelloIntent(intent)
                Intent.ACTION_POWER_DISCONNECTED -> handleDisconnectedIntent(intent)
            }
        }
    }

    private fun handleHelloIntent(intent: Intent?) {
        println("received intent")
        val message = "Broadcast intent detected " + intent?.extras?.get(Intent.EXTRA_TEXT)
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    private fun handleDisconnectedIntent(intent: Intent?) {
        println("received intent")
        val message = "Broadcast intent detected " + intent?.action
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}

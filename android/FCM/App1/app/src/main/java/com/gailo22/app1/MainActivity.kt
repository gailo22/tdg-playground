package com.gailo22.app1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings

class MainActivity : AppCompatActivity() {

    val ACTION_HELLO = "com.gailo22.app1.HELLO"
    // firebase FCM
    val remoteConfig = FirebaseRemoteConfig.getInstance()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendIntentButton: Button = findViewById(R.id.send_intent_button)
        sendIntentButton.setOnClickListener {
            sendIntent()
        }

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(1)
            .build()
        remoteConfig.setConfigSettingsAsync(configSettings)

        val helloRemoteConfig = findViewById<TextView>(R.id.hello_config)

        // fetch
        remoteConfig.fetchAndActivate()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val updated = task.getResult()
                    Log.d(TAG, "Config params updated: $updated")
                    Toast.makeText(this, "Fetch and activate succeeded", Toast.LENGTH_SHORT).show()

                    println(task.getResult().toString())
                    helloRemoteConfig.text = remoteConfig.getString("John")
                } else {
                    Toast.makeText(this, "Fetch failed", Toast.LENGTH_SHORT).show()
                }
                displayWelcomeMessage()
            }

        // messaging
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            val msg = getString(R.string.msg_token_fmt, token)
            Log.d(TAG, msg)
            Toast.makeText(baseContext, msg, Toast.LENGTH_SHORT).show()
        })
    }

    private fun displayWelcomeMessage() {
        Toast.makeText(this, remoteConfig.getString("hello"), Toast.LENGTH_LONG).show()
    }

    private fun sendIntent() {
        val sendIntent = Intent().apply {
            action = ACTION_HELLO
            putExtra(Intent.EXTRA_TEXT, "Hello intent")
            flags = Intent.FLAG_INCLUDE_STOPPED_PACKAGES
        }

        println(sendIntent)
        sendBroadcast(sendIntent)
    }

    companion object {
        private const val TAG = "FCM"
    }

}

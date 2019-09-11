package com.gailo22.iap.iap_application

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseOptions
import com.google.firebase.firestore.FirebaseFirestore

class MainActivity : AppCompatActivity() {

    companion object {
        private val TAG = "MainActivity"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val db = FirebaseFirestore.getInstance()

        // Manually configure Firebase Options
        val options = FirebaseOptions.Builder()
            .setProjectId("sample-firestore-25b7d")
            .setApplicationId("1:185200102540:android:481b6b4877a4a554")
            .setApiKey("AIzaSyBkgTWl0ClcqXeyzGZ_jewI2ORhrFj0aaY")
            .setDatabaseUrl("https://sample-firestore-25b7d.firebaseio.com")
            .build()

        FirebaseApp.initializeApp(this, options, "app2")
        val app2 = FirebaseApp.getInstance("app2")

        val db2 = FirebaseFirestore.getInstance(app2)

        findViewById<Button>(R.id.button1).setOnClickListener { _ -> addUser(db) };
        findViewById<Button>(R.id.button2).setOnClickListener { _ -> addUser(db2) };
    }

    fun addUser(db: FirebaseFirestore) {
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada android",
            "last" to "Lovelace",
            "born" to 1815
        )

        // Add a new document with a generated ID
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}

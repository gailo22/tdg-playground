package com.example.myrxandroidapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Transformations.map
import io.reactivex.Observable

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()
    }

    private fun init() {
        map()
    }

    private fun map() {
        Observable.just(1,2,3,4,5)
                .map { it * 3 }
                .subscribe(
                        {
                            Log.d("map", it.toString())
                        }
                )
    }
}

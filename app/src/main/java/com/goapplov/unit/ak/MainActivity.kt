package com.goapplov.unit.ak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.goapplovunit.ON_AD_LOADED
import com.goapplovunit.idAppOpen
import com.goapplovunit.requestAppOpen

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestAppOpen(idAppOpen) {
            when (it) {
                ON_AD_LOADED -> {

                }
                else -> {

                }
            }
        }
    }
}
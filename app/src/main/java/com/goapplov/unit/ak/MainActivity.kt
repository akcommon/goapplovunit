package com.goapplov.unit.ak

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import com.goapplovunit.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val adsContainer: LinearLayout = findViewById(R.id.adsContainer)
        val listColor = intArrayOf(R.color.white, R.color.black, R.color.white, R.color.white)

        requestNative(color = listColor, idNative) { layout, status ->
            if (layout != null && status == ON_AD_LOADED) {
                if (adsContainer.childCount > 0) adsContainer.removeAllViews()
                adsContainer.addView(layout)
            } else {
                Log.e("MainActivity ", status)
            }
        }

       /* requestNativeApplovin("id") { layout, status ->
            if (layout != null && status == ON_AD_LOADED) {
                if (adsContainer.childCount > 0) adsContainer.removeAllViews()
                adsContainer.addView(layout)
            } else {
                Log.e("MainActivity ", status)
            }
        }*/

    }
}
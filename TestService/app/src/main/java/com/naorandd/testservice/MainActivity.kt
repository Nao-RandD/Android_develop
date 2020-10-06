package com.naorandd.testservice

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {
    //private lateinit var intent: Intent
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 開始ボタン
        val buttonStart = findViewById<Button>(R.id.button_start)
        buttonStart.setOnClickListener {
            val intent = Intent(this@MainActivity, TestIntentService::class.java)
            startService(intent)
        }
    }
}
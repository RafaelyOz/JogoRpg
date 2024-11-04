package com.example.mypersistencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnIniciar = findViewById<Button>(R.id.btnIniciar)
        val btnVerPersonagens = findViewById<Button>(R.id.btnVerPersonagens)

        btnIniciar.setOnClickListener {
            val intent = Intent(this, ClassRaceActivity::class.java)
            startActivity(intent)
        }

        btnVerPersonagens.setOnClickListener {
            val intent = Intent(this, ListPersonagensActivity::class.java)
            startActivity(intent)
        }
    }
}

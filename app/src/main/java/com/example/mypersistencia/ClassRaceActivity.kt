package com.example.mypersistencia

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class ClassRaceActivity : AppCompatActivity() {

    private lateinit var classeEscolhida: String
    private lateinit var racaEscolhida: String
    private lateinit var nomePersonagem: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_class_race)

        val spinnerRaca = findViewById<Spinner>(R.id.spinnerRaca)
        val spinnerClasse = findViewById<Spinner>(R.id.spinnerClasse)
        val btnAvancar = findViewById<Button>(R.id.buttonConfirmarEscolhas)
        val editTextNome = findViewById<EditText>(R.id.editTextNomePersonagem)


        racaEscolhida = "Humano"
        classeEscolhida = "Guerreiro"


        val adapterRaca = ArrayAdapter.createFromResource(
            this,
            R.array.racas_array,
            android.R.layout.simple_spinner_item
        )
        adapterRaca.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerRaca.adapter = adapterRaca


        val adapterClasse = ArrayAdapter.createFromResource(
            this,
            R.array.classes_array,
            android.R.layout.simple_spinner_item
        )
        adapterClasse.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerClasse.adapter = adapterClasse


        spinnerRaca.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                racaEscolhida = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }


        spinnerClasse.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                classeEscolhida = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {

            }
        }

        btnAvancar.setOnClickListener {
            nomePersonagem = editTextNome.text.toString().trim()
            if (nomePersonagem.isNotEmpty()) {
                val intent = Intent(this, AttributesActivity::class.java)
                intent.putExtra("NOME", nomePersonagem)
                intent.putExtra("CLASSE", classeEscolhida)
                intent.putExtra("RACA", racaEscolhida)
                startActivity(intent)
            } else {
                editTextNome.error = "Por favor, insira um nome para o personagem."
            }
        }
    }
}
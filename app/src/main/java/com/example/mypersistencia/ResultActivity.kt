package com.example.mypersistencia

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ResultActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_resultados)

        dbHelper = DatabaseHelper(this)

        // Definição de variáveis
        val nome = intent.getStringExtra("NOME") ?: "Personagem Sem Nome"
        val forca = intent.getIntExtra("FORCA", 8)
        val destreza = intent.getIntExtra("DESTREZA", 8)
        val constituicao = intent.getIntExtra("CONSTITUICAO", 8)
        val inteligencia = intent.getIntExtra("INTELIGENCIA", 8)
        val sabedoria = intent.getIntExtra("SABEDORIA", 8)
        val carisma = intent.getIntExtra("CARISMA", 8)
        val classe = intent.getStringExtra("CLASSE") ?: "Classe Não Selecionada"
        val raca = intent.getStringExtra("RACA") ?: "Raça Não Selecionada"

        // Cálculo dos modificadores
        val modForca = (forca - 10) / 2
        val modDestreza = (destreza - 10) / 2
        val modConstituicao = (constituicao - 10) / 2
        val modInteligencia = (inteligencia - 10) / 2
        val modSabedoria = (sabedoria - 10) / 2
        val modCarisma = (carisma - 10) / 2
        val pontosDeVida = 10 + modConstituicao

        // Exibir resultados
        val txtResultado = findViewById<TextView>(R.id.txtResultado)
        txtResultado.text = """
            Nome do Personagem: $nome
            Classe: $classe
            Raça: $raca
            Pontos de Vida: $pontosDeVida
            Força: $forca (Modificador: $modForca)
            Destreza: $destreza (Modificador: $modDestreza)
            Constituição: $constituicao (Modificador: $modConstituicao)
            Inteligência: $inteligencia (Modificador: $modInteligencia)
            Sabedoria: $sabedoria (Modificador: $modSabedoria)
            Carisma: $carisma (Modificador: $modCarisma)
        """.trimIndent()

        // Botão de salvar personagem
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)
        btnSalvar.setOnClickListener {
            val character = Character(
                name = nome,
                characterClass = classe,
                race = raca,
                strength = forca,
                dexterity = destreza,
                constitution = constituicao,
                intelligence = inteligencia,
                wisdom = sabedoria,
                charisma = carisma
            )

            if (dbHelper.insertCharacter(character) != -1L) {
                Toast.makeText(this, "Personagem salvo com sucesso!", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Erro ao salvar o personagem.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

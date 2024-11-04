package com.example.mypersistencia


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mypersistencia.ResultActivity
import kotlin.reflect.KMutableProperty0

class AttributesActivity : AppCompatActivity() {

    private var forca = 8
    private var destreza = 8
    private var constituicao = 8
    private var inteligencia = 8
    private var sabedoria = 8
    private var carisma = 8
    private var pontosRestantes = 27

    private lateinit var raca: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_attributes)

        raca = intent.getStringExtra("RACA") ?: "Humano"
        val classe = intent.getStringExtra("CLASSE")
        val nomePersonagem = intent.getStringExtra("NOME")

        val btnConcluir = findViewById<Button>(R.id.btnConcluir)
        val txtPontosRestantes = findViewById<TextView>(R.id.txtPontosRestantes)

        atualizarPontosRestantes(txtPontosRestantes)

        btnConcluir.setOnClickListener {
            if (pontosRestantes < 27) {
                Toast.makeText(this, "Você não distribuiu todos os pontos. Você pode continuar assim mesmo.", Toast.LENGTH_SHORT).show()
            }
            aplicarBonusRaca()
            val intent = Intent(this, ResultActivity::class.java).apply {
                putExtra("FORCA", forca)
                putExtra("DESTREZA", destreza)
                putExtra("CONSTITUICAO", constituicao)
                putExtra("INTELIGENCIA", inteligencia)
                putExtra("SABEDORIA", sabedoria)
                putExtra("CARISMA", carisma)
                putExtra("RACA", raca)
                putExtra("CLASSE", classe)
                putExtra("NOME", nomePersonagem)
                // Aqui você pode adicionar os modificadores calculados
                putExtra("MOD_FORCA", calcularModificador(forca))
                putExtra("MOD_DESTREZA", calcularModificador(destreza))
                putExtra("MOD_CONSTITUICAO", calcularModificador(constituicao))
                putExtra("MOD_INTELIGENCIA", calcularModificador(inteligencia))
                putExtra("MOD_SABEDORIA", calcularModificador(sabedoria))
                putExtra("MOD_CARISMA", calcularModificador(carisma))
            }
            startActivity(intent)
        }

        inicializarBotoes()
    }

    private fun calcularModificador(atributo: Int): Int {
        return (atributo - 10) / 2
    }

    private fun aplicarBonusRaca() {
        when (raca) {
            "Humano" -> {
                forca += 1
                destreza += 1
                constituicao += 1
                inteligencia += 1
                sabedoria += 1
                carisma += 1
            }
            "Elfo" -> {
                destreza += 2
                inteligencia += 1
            }
            "Anão" -> {
                forca += 2
                constituicao += 1
            }
            "MeioELfo" -> {
                carisma += 2
            }
            "MeioOrc" -> {
                forca += 2
                constituicao += 1
            }
            "Halfling" -> {
                destreza += 2
            }
            "Gnomo" -> {
                inteligencia += 2
            }



        }
    }

    private fun inicializarBotoes() {
        setIncrementDecrementListeners(R.id.buttonIncrementForca, R.id.buttonDecrementForca, ::forca)
        setIncrementDecrementListeners(R.id.buttonIncrementDestreza, R.id.buttonDecrementDestreza, ::destreza)
        setIncrementDecrementListeners(R.id.buttonIncrementConstituicao, R.id.buttonDecrementConstituicao, ::constituicao)
        setIncrementDecrementListeners(R.id.buttonIncrementInteligencia, R.id.buttonDecrementInteligencia, ::inteligencia)
        setIncrementDecrementListeners(R.id.buttonIncrementSabedoria, R.id.buttonDecrementSabedoria, ::sabedoria)
        setIncrementDecrementListeners(R.id.buttonIncrementCarisma, R.id.buttonDecrementCarisma, ::carisma)
    }

    private fun setIncrementDecrementListeners(incrementId: Int, decrementId: Int, atributo: KMutableProperty0<Int>) {
        findViewById<Button>(incrementId).setOnClickListener {
            if (pontosRestantes > 0 && atributo.get() < 15) {
                atributo.set(atributo.get() + 1)
                pontosRestantes--
                atualizarInterface()
            }
        }

        findViewById<Button>(decrementId).setOnClickListener {
            if (atributo.get() > 8) {
                atributo.set(atributo.get() - 1)
                pontosRestantes++
                atualizarInterface()
            }
        }
    }

    private fun atualizarAtributo(textViewId: Int, nomeAtributo: String, valor: Int) {
        findViewById<TextView>(textViewId).text = "$nomeAtributo: $valor"
    }

    private fun atualizarInterface() {
        atualizarAtributo(R.id.textValorForca, "Força", forca)
        atualizarAtributo(R.id.textValorDestreza, "Destreza", destreza)
        atualizarAtributo(R.id.textValorConstituicao, "Constituição", constituicao)
        atualizarAtributo(R.id.textValorInteligencia, "Inteligência", inteligencia)
        atualizarAtributo(R.id.textValorSabedoria, "Sabedoria", sabedoria)
        atualizarAtributo(R.id.textValorCarisma, "Carisma", carisma)
        atualizarPontosRestantes(findViewById(R.id.txtPontosRestantes))
    }


    private fun atualizarPontosRestantes(txtPontosRestantes: TextView) {
        txtPontosRestantes.text = "Pontos Restantes: $pontosRestantes"
    }
}

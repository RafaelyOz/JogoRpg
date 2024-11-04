package com.example.mypersistencia

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class EditCharacterActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private var characterId: Int = -1

    private var forca = 8
    private var destreza = 8
    private var constituicao = 8
    private var inteligencia = 8
    private var sabedoria = 8
    private var carisma = 8

    private lateinit var raca: String
    private lateinit var classe: String
    private var bonusAplicado = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_character)

        dbHelper = DatabaseHelper(this)
        characterId = intent.getIntExtra("CHARACTER_ID", -1)


        val character = dbHelper.getCharacter(characterId)
        character?.let {
            forca = it.strength
            destreza = it.dexterity
            constituicao = it.constitution
            inteligencia = it.intelligence
            sabedoria = it.wisdom
            carisma = it.charisma
            raca = it.race
            classe = it.characterClass


            findViewById<EditText>(R.id.etName).setText(it.name)


            setupSpinners(classe, raca)

            atualizarCampos()
        } ?: run {

            aplicarBonusRaca()
            atualizarCampos()
            bonusAplicado = true
        }

        val btnAtualizar = findViewById<Button>(R.id.btnUpdate)
        val btnDeletar = findViewById<Button>(R.id.btnDelete)


        btnAtualizar.setOnClickListener {
            val nome = findViewById<EditText>(R.id.etName).text.toString().trim()
            val classeSelecionada = findViewById<Spinner>(R.id.spinnerClass).selectedItem.toString()
            val racaSelecionada = findViewById<Spinner>(R.id.spinnerRace).selectedItem.toString()

            if (nome.isNotEmpty()) {
                aplicarBonusRaca()
                val character = Character(
                    id = characterId,
                    name = nome,
                    characterClass = classeSelecionada,
                    race = racaSelecionada,
                    strength = forca,
                    dexterity = destreza,
                    constitution = constituicao,
                    intelligence = inteligencia,
                    wisdom = sabedoria,
                    charisma = carisma
                )

                if (dbHelper.updateCharacter(character) > 0) {
                    setResult(RESULT_OK)
                    Toast.makeText(this, "Personagem atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao atualizar o personagem.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Por favor, preencha todos os campos corretamente.", Toast.LENGTH_SHORT).show()
            }
        }


        btnDeletar.setOnClickListener {
            confirmarDeletar()
        }
    }

    private fun setupSpinners(classe: String, raca: String) {
        val classesValidas = resources.getStringArray(R.array.classes_array).toList()
        val racasValidas = resources.getStringArray(R.array.racas_array).toList()

        val classSpinner: Spinner = findViewById(R.id.spinnerClass)
        val raceSpinner: Spinner = findViewById(R.id.spinnerRace)


        val classAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, classesValidas)
        classAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        classSpinner.adapter = classAdapter
        classSpinner.setSelection(classesValidas.indexOf(classe))


        val raceAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, racasValidas)
        raceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        raceSpinner.adapter = raceAdapter
        raceSpinner.setSelection(racasValidas.indexOf(raca))
    }

    private fun confirmarDeletar() {
        AlertDialog.Builder(this)
            .setTitle("Deletar Personagem")
            .setMessage("Tem certeza que deseja deletar este personagem?")
            .setPositiveButton("Sim") { dialog: DialogInterface, _: Int ->
                if (dbHelper.deleteCharacter(characterId) > 0) {
                    setResult(RESULT_OK) // Define o resultado como OK ao deletar
                    Toast.makeText(this, "Personagem deletado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao deletar o personagem.", Toast.LENGTH_SHORT).show()
                }
                dialog.dismiss()
            }
            .setNegativeButton("Não") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
            .create()
            .show()
    }

    private fun atualizarCampos() {
        findViewById<EditText>(R.id.etStrength).setText("Força: $forca")
        findViewById<EditText>(R.id.etDexterity).setText("Destreza: $destreza")
        findViewById<EditText>(R.id.etConstitution).setText("Constituição: $constituicao")
        findViewById<EditText>(R.id.etIntelligence).setText("Inteligência: $inteligencia")
        findViewById<EditText>(R.id.etWisdom).setText("Sabedoria: $sabedoria")
        findViewById<EditText>(R.id.etCharisma).setText("Carisma: $carisma")
    }

    private fun aplicarBonusRaca() {
        if (bonusAplicado) return

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
            "MeioElfo" -> {
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
        bonusAplicado = true
    }
}

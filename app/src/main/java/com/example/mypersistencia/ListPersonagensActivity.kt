package com.example.mypersistencia

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts

class ListPersonagensActivity : AppCompatActivity() {

    private lateinit var dbHelper: DatabaseHelper
    private lateinit var characters: List<Character>
    private lateinit var editCharacterLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character_list)

        dbHelper = DatabaseHelper(this)


        editCharacterLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                refreshCharacterList()
            }
        }

        refreshCharacterList()

        val characterList = findViewById<ListView>(R.id.character_list)
        characterList.setOnItemClickListener { _, _, position, _ ->
            val selectedCharacter = characters[position]
            val intent = Intent(this, EditCharacterActivity::class.java).apply {
                putExtra("CHARACTER_ID", selectedCharacter.id)
            }
            editCharacterLauncher.launch(intent)
        }


        characterList.setOnItemLongClickListener { _, _, position, _ ->
            val selectedCharacter = characters[position]


            selectedCharacter.id?.let { characterId ->
                showDeleteConfirmationDialog(characterId)
            } ?: run {
                Toast.makeText(this, "ID do personagem não encontrado.", Toast.LENGTH_SHORT).show()
            }
            true
        }


        val btnBackToMain = findViewById<Button>(R.id.btnBackToMain)
        btnBackToMain.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun showDeleteConfirmationDialog(characterId: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deletar Personagem")
        builder.setMessage("Você tem certeza que deseja deletar este personagem?")
        builder.setPositiveButton("Deletar") { dialog: DialogInterface, _: Int ->
            val rowsDeleted = dbHelper.deleteCharacter(characterId)
            if (rowsDeleted > 0) {
                Toast.makeText(this, "Personagem deletado com sucesso!", Toast.LENGTH_SHORT).show()
                refreshCharacterList()
            } else {
                Toast.makeText(this, "Erro ao deletar o personagem.", Toast.LENGTH_SHORT).show()
            }
            dialog.dismiss()
        }
        builder.setNegativeButton("Cancelar") { dialog: DialogInterface, _: Int -> dialog.dismiss() }
        builder.show()
    }

    private fun refreshCharacterList() {
        characters = dbHelper.getAllCharacters()
        val characterList = findViewById<ListView>(R.id.character_list)
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, characters.map { it.name })
        characterList.adapter = adapter
    }
}

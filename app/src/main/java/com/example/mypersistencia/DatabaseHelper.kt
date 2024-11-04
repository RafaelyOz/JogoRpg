package com.example.mypersistencia

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "DndCharacters.db"
        const val DATABASE_VERSION = 1

        const val TABLE_NAME = "characters"
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_CLASS = "class"
        const val COLUMN_RACE = "race"
        const val COLUMN_STRENGTH = "strength"
        const val COLUMN_DEXTERITY = "dexterity"
        const val COLUMN_CONSTITUTION = "constitution"
        const val COLUMN_INTELLIGENCE = "intelligence"
        const val COLUMN_WISDOM = "wisdom"
        const val COLUMN_CHARISMA = "charisma"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_NAME TEXT,
                $COLUMN_CLASS TEXT,
                $COLUMN_RACE TEXT,
                $COLUMN_STRENGTH INTEGER,
                $COLUMN_DEXTERITY INTEGER,
                $COLUMN_CONSTITUTION INTEGER,
                $COLUMN_INTELLIGENCE INTEGER,
                $COLUMN_WISDOM INTEGER,
                $COLUMN_CHARISMA INTEGER
            )
        """
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertCharacter(character: Character): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, character.name)
            put(COLUMN_CLASS, character.characterClass)
            put(COLUMN_RACE, character.race)
            put(COLUMN_STRENGTH, character.strength)
            put(COLUMN_DEXTERITY, character.dexterity)
            put(COLUMN_CONSTITUTION, character.constitution)
            put(COLUMN_INTELLIGENCE, character.intelligence)
            put(COLUMN_WISDOM, character.wisdom)
            put(COLUMN_CHARISMA, character.charisma)
        }
        return db.insert(TABLE_NAME, null, values)
    }

    fun updateCharacter(character: Character): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_NAME, character.name)
            put(COLUMN_CLASS, character.characterClass)
            put(COLUMN_RACE, character.race)
            put(COLUMN_STRENGTH, character.strength)
            put(COLUMN_DEXTERITY, character.dexterity)
            put(COLUMN_CONSTITUTION, character.constitution)
            put(COLUMN_INTELLIGENCE, character.intelligence)
            put(COLUMN_WISDOM, character.wisdom)
            put(COLUMN_CHARISMA, character.charisma)
        }
        return db.update(TABLE_NAME, values, "$COLUMN_ID = ?", arrayOf(character.id.toString()))
    }

    fun deleteCharacter(characterId: Int): Int {
        val db = writableDatabase
        return db.delete(TABLE_NAME, "$COLUMN_ID = ?", arrayOf(characterId.toString()))
    }

    fun getAllCharacters(): List<Character> {
        val db = readableDatabase
        val cursor = db.query(TABLE_NAME, null, null, null, null, null, null)
        val characters = mutableListOf<Character>()
        with(cursor) {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(COLUMN_ID))
                val name = getString(getColumnIndexOrThrow(COLUMN_NAME))
                val characterClass = getString(getColumnIndexOrThrow(COLUMN_CLASS))
                val race = getString(getColumnIndexOrThrow(COLUMN_RACE))
                val strength = getInt(getColumnIndexOrThrow(COLUMN_STRENGTH))
                val dexterity = getInt(getColumnIndexOrThrow(COLUMN_DEXTERITY))
                val constitution = getInt(getColumnIndexOrThrow(COLUMN_CONSTITUTION))
                val intelligence = getInt(getColumnIndexOrThrow(COLUMN_INTELLIGENCE))
                val wisdom = getInt(getColumnIndexOrThrow(COLUMN_WISDOM))
                val charisma = getInt(getColumnIndexOrThrow(COLUMN_CHARISMA))

                characters.add(Character(id, name, characterClass, race, strength, dexterity, constitution, intelligence, wisdom, charisma))
            }
        }
        cursor.close()
        return characters
    }

    fun getCharacter(characterId: Int): Character? {
        val db = readableDatabase
        val cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(characterId.toString()),
            null,
            null,
            null
        )

        return if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val name = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NAME))
            val characterClass = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CLASS))
            val race = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RACE))
            val strength = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_STRENGTH))
            val dexterity = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_DEXTERITY))
            val constitution = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CONSTITUTION))
            val intelligence = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_INTELLIGENCE))
            val wisdom = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_WISDOM))
            val charisma = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_CHARISMA))

            Character(id, name, characterClass, race, strength, dexterity, constitution, intelligence, wisdom, charisma)
        } else {
            null
        }.also { cursor.close() }
    }
}

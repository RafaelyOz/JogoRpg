package com.example.mypersistencia

data class Character(
    val id: Int? = null,
    val name: String,
    val characterClass: String,
    val race: String,
    val strength: Int,
    val dexterity: Int,
    val constitution: Int,
    val intelligence: Int,
    val wisdom: Int,
    val charisma: Int
)

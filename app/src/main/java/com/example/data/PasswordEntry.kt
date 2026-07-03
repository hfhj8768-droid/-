package com.example.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwords")
data class PasswordEntry(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val username: String,
    val passwordHash: String, // Keeping it simple for demo, will just store raw or base64. Let's call it password
    val url: String = "",
    val timestamp: Long = System.currentTimeMillis()
)

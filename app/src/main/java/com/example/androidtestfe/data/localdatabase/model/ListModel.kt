package com.example.androidtestfe.data.localdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val username: String,
    val email: String,
    val password: String,
    val role: String
)
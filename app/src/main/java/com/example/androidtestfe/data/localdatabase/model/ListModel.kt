package com.example.androidtestfe.data.localdatabase.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ListModel(
    @PrimaryKey
    val id: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String,
    val role: String
)
package ru.tomindapps.testideasworld.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Favorite(
    @PrimaryKey val id: Long,
    val imageId: String,
    val localImagePath:String
)
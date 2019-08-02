package ru.tomindapps.testideasworld.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import ru.tomindapps.testideasworld.converters.UnsplashLinksConverter
import ru.tomindapps.testideasworld.converters.UnsplashUrlsConverter
import ru.tomindapps.testideasworld.converters.UnsplashUsersConverter


@Entity(tableName = "photos")
data class Photo(
    @PrimaryKey val id: String,
    val created_at: String,
    val width: Int,
    val height: Int,
    val color: String? = "#000000",
    val likes: Int,
    val description: String?,
    val urls: UnsplashUrls,
    val links: UnsplashLinks,
    val user: UnsplashUser
)
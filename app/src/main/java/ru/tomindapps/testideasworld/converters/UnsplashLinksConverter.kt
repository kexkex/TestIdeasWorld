package ru.tomindapps.testideasworld.converters

import androidx.room.TypeConverter
import ru.tomindapps.testideasworld.models.UnsplashLinks

class UnsplashLinksConverter {

    @TypeConverter
    fun fromLinks(links: UnsplashLinks): String{
        return "${links.self},${links.html}"
    }

    @TypeConverter
    fun toLinks(string: String): UnsplashLinks{
        var list = emptyList<String>()
        list = string.split(",")
        return UnsplashLinks(self = list.get(0), html = list.get(1))
    }
}
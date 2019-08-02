package ru.tomindapps.testideasworld.converters

import androidx.room.TypeConverter
import ru.tomindapps.testideasworld.models.UnsplashLinks
import ru.tomindapps.testideasworld.models.UnsplashUrls

class UnsplashUrlsConverter {

    @TypeConverter
    fun fromUrls(urls: UnsplashUrls):String{
        return urls.small
    }

    @TypeConverter
    fun toUrls(string: String): UnsplashUrls{
        return UnsplashUrls(small = string)
    }

}
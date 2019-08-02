package ru.tomindapps.testideasworld.converters

import androidx.room.TypeConverter
import ru.tomindapps.testideasworld.models.UnsplashLinks
import ru.tomindapps.testideasworld.models.UnsplashUrls
import ru.tomindapps.testideasworld.models.UnsplashUser

class UnsplashUsersConverter {

    @TypeConverter
    fun fromUsers(users: UnsplashUser): String{
        return "${users.id},${users.username},${users.name},${users.total_likes},${users.total_photos},${users.total_collection}"
    }

    @TypeConverter
    fun toUsers(string: String): UnsplashUser{
        var list = emptyList<String>()

        list = string.split(",")
        return UnsplashUser(id = list.get(0), username = list.get(1), name = list.get(2),
            total_likes = list.get(3).toInt(), total_photos = list.get(4).toInt(), total_collection = list.get(5).toInt())
    }
}
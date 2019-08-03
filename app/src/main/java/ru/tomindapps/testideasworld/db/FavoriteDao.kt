package ru.tomindapps.testideasworld.db

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.tomindapps.testideasworld.models.Favorite
import ru.tomindapps.testideasworld.models.Photo

@Dao
interface FavoriteDao {
    @Insert
    fun insert(favorite: Favorite)

    @Delete
    fun delete(favorite: Favorite)

    @Query("Select * from favorites")
    fun selectAll():List<Favorite>

    @Query("Select * from favorites where id like :id")
    fun selectById(id: String): Favorite

    @Query("Delete from favorites where id like :id")
    fun deleteById(id: String)
}
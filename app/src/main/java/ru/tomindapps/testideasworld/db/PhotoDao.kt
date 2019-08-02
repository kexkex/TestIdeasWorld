package ru.tomindapps.testideasworld.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import ru.tomindapps.testideasworld.models.Photo

@Dao
interface PhotoDao {

    @Insert
    fun insert(photo: Photo)

    @Delete
    fun delete(photo: Photo)

    @Query("Select * from photos")
    fun selectAll():ArrayList<Photo>

    @Query("Select * from photos where id like :id")
    fun selectById(id: String): Photo


}
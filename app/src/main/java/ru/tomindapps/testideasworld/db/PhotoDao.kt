package ru.tomindapps.testideasworld.db

import androidx.lifecycle.LiveData
import androidx.room.*
import ru.tomindapps.testideasworld.models.Photo

@Dao
interface PhotoDao {

    @Insert
    fun insert(photo: Photo)

    @Delete
    fun delete(photo: Photo)

    @Update
    fun update(photo: Photo)

    @Query("Select * from photos")
    fun selectAll():List<Photo>

    @Query("Select * from photos where id like :id")
    fun selectById(id: String): Photo


}
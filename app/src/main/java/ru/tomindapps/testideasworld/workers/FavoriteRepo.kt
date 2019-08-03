package ru.tomindapps.testideasworld.workers

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.lifecycle.MutableLiveData
import ru.tomindapps.testideasworld.db.FavoriteDao
import ru.tomindapps.testideasworld.db.PhotoDao
import ru.tomindapps.testideasworld.models.Favorite
import ru.tomindapps.testideasworld.models.Photo

class FavoriteRepo (val favoriteDao: FavoriteDao, val context: Context){

    var favorites: MutableLiveData<ArrayList<Favorite>> = MutableLiveData()

    fun getPhotosFromSource(){
        var fav = favoriteDao.selectAll() as ArrayList<Favorite>
        favorites.postValue(fav)


    }

    fun addFavorite(favorite: Favorite) {
        favoriteDao.insert(favorite)
        getPhotosFromSource()
    }

}
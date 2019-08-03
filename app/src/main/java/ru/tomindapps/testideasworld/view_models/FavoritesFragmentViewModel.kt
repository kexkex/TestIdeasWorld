package ru.tomindapps.testideasworld.view_models

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import ru.tomindapps.testideasworld.db.AppDatabase
import ru.tomindapps.testideasworld.models.Favorite
import ru.tomindapps.testideasworld.workers.FavoriteRepo

class FavoritesFragmentViewModel(app: Application): AndroidViewModel(app){
    private val favoriteRepo: FavoriteRepo
    var favorites: MutableLiveData<ArrayList<Favorite>>

    init {
        val dao = AppDatabase.getInstance(app, viewModelScope).favoriteDao()
        favoriteRepo = FavoriteRepo(dao, app.applicationContext)
        favorites = favoriteRepo.favorites
    }

    fun loadFavorites(){
        DoGet().execute()
        /*viewModelScope.launch(Dispatchers.IO){
            favoriteRepo.getPhotosFromSource()
        }*/
    }

    inner class DoGet: AsyncTask<Unit, Unit, Unit>(){
        override fun doInBackground(vararg params: Unit?) {
            favoriteRepo.getPhotosFromSource()
        }

    }
}
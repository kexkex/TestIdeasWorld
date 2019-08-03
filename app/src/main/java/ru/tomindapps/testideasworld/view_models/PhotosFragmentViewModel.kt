package ru.tomindapps.testideasworld.view_models

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tomindapps.testideasworld.db.AppDatabase
import ru.tomindapps.testideasworld.models.Favorite
import ru.tomindapps.testideasworld.models.Photo
import ru.tomindapps.testideasworld.workers.FavoriteRepo
import ru.tomindapps.testideasworld.workers.PhotoRepo

class PhotosFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val photoRepo: PhotoRepo
    private val favoriteRepo: FavoriteRepo
    var photos: MutableLiveData<ArrayList<Photo>>

    init {
        val pdao = AppDatabase.getInstance(app, viewModelScope).photoDao()
        photoRepo = PhotoRepo(pdao,app.baseContext)
        val fdao = AppDatabase.getInstance(app, viewModelScope).favoriteDao()
        favoriteRepo = FavoriteRepo(fdao, app.applicationContext)
        photos = photoRepo.photos
    }

    fun loadPhotos(){
        DoGet().execute()

        /*viewModelScope.launch(Dispatchers.IO){
            photoRepo.getPhotosFromSource()
        }*/
    }

    fun updateFavoriteStatus(photoList: ArrayList<Photo>, pos: Int) {
        val pair = Pair(photoList, pos)
        DoUpdate().execute(pair)
    }

    fun addToFavorites(photos: ArrayList<Photo>, position: Int) {
        if (photos[position].favorite == 0) {
            val pair = Pair(photos, position)
            DoAdd().execute(pair)
        }
    }

    inner class DoGet: AsyncTask<Unit, Unit, ArrayList<Photo>>(){
        override fun doInBackground(vararg params: Unit?): ArrayList<Photo> {
            return photoRepo.getPhotosFromSource()
        }

        override fun onPostExecute(result: ArrayList<Photo>?) {
            photoRepo.photos.postValue(result)
            photoRepo.saveLoaded(result!!)
        }

    }

    inner class DoUpdate: AsyncTask<Pair<ArrayList<Photo>,Int>, Unit, Unit>(){
        override fun doInBackground(vararg params: Pair<ArrayList<Photo>,Int>?) {
            photoRepo.updateFavoriteStatus(params[0])

        }

    }

    inner class DoAdd: AsyncTask<Pair<ArrayList<Photo>,Int>, Unit, Unit>(){
        override fun doInBackground(vararg params: Pair<ArrayList<Photo>,Int>?) {
            val pair = params[0]
            val photo = pair!!.first[pair.second]
            val favorite = Favorite(
                photo.id,
                photo.created_at,
                photo.width,
                photo.height,
                photo.color,
                photo.likes,
                photo.description,
                photo.urls,
                photo.user,
                photo.favorite
            )
            favoriteRepo.addFavorite(favorite)

        }

    }


}
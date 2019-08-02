package ru.tomindapps.testideasworld.view_models

import android.app.Application
import android.os.AsyncTask
import androidx.lifecycle.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.tomindapps.testideasworld.db.AppDatabase
import ru.tomindapps.testideasworld.models.Photo
import ru.tomindapps.testideasworld.workers.PhotoRepo

class PhotosFragmentViewModel(app: Application) : AndroidViewModel(app) {

    private val photoRepo: PhotoRepo
    var photos: MutableLiveData<ArrayList<Photo>>

    init {
        val dao = AppDatabase.getInstance(app, viewModelScope).photoDao()
        photoRepo = PhotoRepo(dao,app.baseContext)
        photos = photoRepo.photos
    }

    fun loadPhotos(){

        viewModelScope.launch(Dispatchers.IO){
            photoRepo.getPhotosFromSource()
        }
    }


}
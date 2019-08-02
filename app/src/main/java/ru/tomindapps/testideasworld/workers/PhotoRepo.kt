package ru.tomindapps.testideasworld.workers

import android.content.Context
import android.net.ConnectivityManager
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import ru.tomindapps.testideasworld.db.PhotoDao
import ru.tomindapps.testideasworld.models.Photo

class PhotoRepo (val photoDao: PhotoDao, val context: Context) {

    var photos: MutableLiveData<ArrayList<Photo>> = MutableLiveData()


    fun getPhotoListFromDb(): ArrayList<Photo>{
        return photoDao.selectAll() as ArrayList<Photo>
    }


    fun getPhotoListFromWeb(): ArrayList<Photo>{
        return PhotoLoader.loadPhotos()
    }

    @WorkerThread
    fun getPhotosFromSource(){

        if (!isNetworkConnected(context)) photos.postValue(getPhotoListFromDb())
        else photos.postValue(getPhotoListFromWeb())

    }

    private fun isNetworkConnected(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }
}
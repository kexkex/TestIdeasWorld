package ru.tomindapps.testideasworld.workers

import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import android.util.Log
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.squareup.picasso.Picasso
import ru.tomindapps.testideasworld.db.PhotoDao
import ru.tomindapps.testideasworld.models.Photo

class PhotoRepo (val photoDao: PhotoDao, val context: Context) {

    var photosFromDb = ArrayList<Photo>()
    var photos: MutableLiveData<ArrayList<Photo>> = MutableLiveData()

    fun getPhotoListFromDb(): ArrayList<Photo>{
        return photoDao.selectAll() as ArrayList<Photo>
    }

    fun getPhotoListFromWeb(): ArrayList<Photo> {
        val photosFromWeb = PhotoLoader.loadPhotos()
        for (photo in photosFromWeb){
            if (photosFromDb.contains(photo))
                photosFromWeb.remove(photo)
        }
        return photosFromWeb
    }


    fun saveLoaded(photos: ArrayList<Photo>){
        for (photo in photos) {
            Picasso.get().load(photo.urls).into(PhotoLoader.getTarget(photo.id))
            photo.urls = "${Environment.getExternalStorageDirectory()}/Download/${photo.id}.jpg"
        }
    }

    //@WorkerThread
    fun getPhotosFromSource():ArrayList<Photo>{
        photosFromDb = getPhotoListFromDb()
        photosFromDb.addAll(getPhotoListFromWeb())
        return photosFromDb

    }

    private fun isNetworkConnected(context: Context): Boolean {

        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        return networkInfo != null && networkInfo.isConnected
    }

    fun updateFavoriteStatus(pair: Pair<ArrayList<Photo>, Int>?) {
        if (pair!=null) {
            Log.d("Main", "none")
            var arr = pair.first
            var photo = arr[pair.second]
            photo.favorite = 1 - photo.favorite
            arr[pair.second] = photo
            photos.postValue(arr)
            photoDao.update(photo)
        }

    }
}
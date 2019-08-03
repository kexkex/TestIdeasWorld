package ru.tomindapps.testideasworld.workers

import android.content.Context
import android.net.ConnectivityManager
import android.os.Environment
import android.util.Log
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

        return PhotoLoader.loadPhotos()
    }


    fun saveLoaded(photos: ArrayList<Photo>){
        for (photo in photos) {
            Picasso.get().load(photo.urls).into(PhotoLoader.getTarget(photo.id))
            photo.urls = "${Environment.getExternalStorageDirectory()}/Download/${photo.id}.jpeg"
        }
    }

    //@WorkerThread
    fun getPhotosFromSource():ArrayList<Photo>{
        var photos = getPhotoListFromDb()
        var photosIds = ArrayList<String>()
        for (photo in photos) photosIds.add(photo.id)
        var photosWeb = getPhotoListFromWeb()
        for (photo in photosWeb) if (!photosIds.contains(photo.id)) {
            photos.add(photo)
            photoDao.insert(photo)
        } else {photoDao.update(photo)}

        return photos

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
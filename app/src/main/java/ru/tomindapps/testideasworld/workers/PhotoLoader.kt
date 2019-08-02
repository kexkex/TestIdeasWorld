package ru.tomindapps.testideasworld.workers

import android.os.AsyncTask
import android.os.Handler
import android.util.Log
import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.httpGet
import io.github.rybalkinsd.kohttp.ext.httpGetAsync
import kotlinx.coroutines.Deferred
import okhttp3.Response
import ru.tomindapps.testideasworld.models.Photo
import ru.tomindapps.testideasworld.models.UnsplashPhoto

object PhotoLoader {

    private val BASE_URL = "https://api.unsplash.com/"
    private val CLIENT_ID = "client_id=4ac598f7d1a250512b11c07cbc37ebc7f7d55b48e9b88f5c058c173d91a561a1"
    private val BASE_PHOTOS_COUNT = 50

    fun loadPhotos() = castToPhotos(parseJson())

    fun castToPhotos(unsplashArr: ArrayList<UnsplashPhoto>): ArrayList<Photo>{
        var arr = ArrayList<Photo>()
        for (unsplash in unsplashArr) {
            arr.add(Photo(
                unsplash.id,
                unsplash.created_at,
                unsplash.width,
                unsplash.height,
                unsplash.color,
                unsplash.likes,
                unsplash.description,
                unsplash.urls.small,
                unsplash.user.username
                ))
        }
        return arr
    }


    fun getJson():String?{
        val url = BASE_URL + "photos/?" + CLIENT_ID + "&per_page=" + BASE_PHOTOS_COUNT
        var response = url.httpGet()
        return response?.asString()
    }

    fun parseJson():ArrayList<UnsplashPhoto>{
        val gson = Gson()
        val type = object: TypeToken<ArrayList<UnsplashPhoto>>(){}.type
        val fromJson = gson.fromJson(getJson(),type) as ArrayList<UnsplashPhoto>
        return fromJson
    }


}
package ru.tomindapps.testideasworld.workers

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Environment
import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import io.github.rybalkinsd.kohttp.ext.asString
import io.github.rybalkinsd.kohttp.ext.httpGet
import ru.tomindapps.testideasworld.models.Photo
import ru.tomindapps.testideasworld.models.UnsplashPhoto
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception

object PhotoLoader {

    private val BASE_URL = "https://api.unsplash.com/"
    private val CLIENT_ID = "client_id=4ac598f7d1a250512b11c07cbc37ebc7f7d55b48e9b88f5c058c173d91a561a1"
    val BASE_PHOTO_COUNT = 50
    var photoCount = BASE_PHOTO_COUNT

    fun loadPhotos():ArrayList<Photo> = castToPhotos(parseJson())

    fun getTarget(id: String): Target{
        return object:Target{
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {

            }

            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                Log.d("Main", e.toString())
            }

            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {

                var file = File("${Environment.getExternalStorageDirectory()}/Download/${id}.jpg")
                Log.d("Main", file.canRead().toString())
                Log.d("Main", file.absolutePath)

                if (!file.canRead()) {

                    try {

                        file.createNewFile()
                        val oStream = FileOutputStream(file)
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, oStream)
                        oStream.flush()
                        oStream.close()
                        Log.d("Main", file.absolutePath)
                    } catch (e: Exception) {
                        Log.d("Main", e.message)
                    }
                }
            }

        }
    }

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
                unsplash.user.username,
                0
                ))
        }
        return arr
    }


    fun getJson():String?{
        val url = BASE_URL + "photos/?" + CLIENT_ID + "&per_page=" + photoCount
        var response = url.httpGet()
        return response.asString()
    }

    fun parseJson():ArrayList<UnsplashPhoto>{
        val gson = Gson()
        val type = object: TypeToken<ArrayList<UnsplashPhoto>>(){}.type
        val fromJson = gson.fromJson(getJson(),type) as ArrayList<UnsplashPhoto>
        return fromJson
    }


}
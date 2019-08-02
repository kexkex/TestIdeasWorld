package ru.tomindapps.testideasworld.views

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.tomindapps.testideasworld.R

import androidx.annotation.NonNull
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {

    private val WRITE_REQUEST_CODE = 1
    private var toolbar: ActionBar? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val permissions = arrayOf<String>(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        ActivityCompat.requestPermissions(this,permissions, WRITE_REQUEST_CODE)

 //       toolbar = getSupportActionBar()

        val navigation = findViewById<BottomNavigationView>(R.id.navigation)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener())

        loadFragment(PhotosFragment())

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode){
            WRITE_REQUEST_CODE -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    Log.d("Main","ok")
                } else {Log.d("Main","fail")}
            }
        }
    }

    fun mOnNavigationItemSelectedListener() =  object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val fragment: Fragment
            when (item.itemId) {
                R.id.navigation_info -> {
                    //                  toolbar.setTitle("Info")
                    fragment = InfoFragment()
                    loadFragment(fragment)
                    return true
                }
                R.id.navigation_photos -> {
                    //                 toolbar.setTitle("Photos")
                    fragment = PhotosFragment()
                    loadFragment(fragment)
                    return true
                }
                R.id.navigation_favorite -> {
                    //                 toolbar.setTitle("Favorite")
                    fragment = FavoritesFragment()
                    loadFragment(fragment)
                    return true
                }
            }
            return false
        }
    }

    fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        with(transaction){
            replace(R.id.frame_container, fragment)
            addToBackStack(null)
            commit()
        }
    }




}

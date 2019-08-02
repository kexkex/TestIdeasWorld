package ru.tomindapps.testideasworld.views

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.ActionBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import ru.tomindapps.testideasworld.R

import androidx.annotation.NonNull
import androidx.fragment.app.Fragment


class MainActivity : AppCompatActivity() {


    private var toolbar: ActionBar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

 //       toolbar = getSupportActionBar()

        val navigation = findViewById(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener())

        loadFragment(PhotosFragment())

    }

    fun mOnNavigationItemSelectedListener() =  object : BottomNavigationView.OnNavigationItemSelectedListener {

        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val fragment: Fragment
            when (item.getItemId()) {
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
        val transaction = getSupportFragmentManager().beginTransaction()
        with(transaction){
            replace(R.id.frame_container, fragment)
            addToBackStack(null)
            commit()
        }
    }




}

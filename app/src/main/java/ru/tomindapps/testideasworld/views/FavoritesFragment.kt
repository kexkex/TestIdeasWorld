package ru.tomindapps.testideasworld.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.tomindapps.testideasworld.R
import ru.tomindapps.testideasworld.adapters.FavoriteAdapter
import ru.tomindapps.testideasworld.view_models.FavoritesFragmentViewModel

class FavoritesFragment : Fragment(), FavoriteAdapter.MyAdapterListener {
    override fun onFavoriteClicked(position: Int) {

    }

    override fun onRowClicked(position: Int) {
        var intent = Intent(this.context, ElementActivity::class.java)
        var photo = favoritesFragmentViewModel.favorites.value!![position]
        intent.putExtra("imgPath", photo.urls)
        intent.putExtra("authorName", photo.user)
        startActivity(intent)

    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter
    private lateinit var favoritesFragmentViewModel: FavoritesFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorites, container, false)

        val mLayoutManager = GridLayoutManager(context, 3)

        favoriteAdapter = FavoriteAdapter(this)
        favoritesFragmentViewModel = ViewModelProviders.of(this.activity!!).get(FavoritesFragmentViewModel::class.java)
        favoritesFragmentViewModel.favorites.observe(this.activity!!, Observer { photos -> photos.let { favoriteAdapter.setupPhotoList(it) } })
        favoritesFragmentViewModel.loadFavorites()


        recyclerView = view.findViewById(R.id.rv_favorites)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = favoriteAdapter

        return view


    }

}

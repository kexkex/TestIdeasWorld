package ru.tomindapps.testideasworld.views

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import ru.tomindapps.testideasworld.R
import ru.tomindapps.testideasworld.adapters.PhotoAdapter
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import ru.tomindapps.testideasworld.view_models.PhotosFragmentViewModel


class PhotosFragment : Fragment(), PhotoAdapter.MyAdapterListener {
    override fun onFavoriteClicked(position: Int) {
        var photos = photosFragmentViewModel.photos.value!!
        photosFragmentViewModel.updateFavoriteStatus(photos, position)
        photosFragmentViewModel.addToFavorites(photos, position)

    }

    override fun onRowClicked(position: Int) {
        var intent = Intent(this.context, ElementActivity::class.java)
        var photo = photosFragmentViewModel.photos.value!![position]
        intent.putExtra("imgPath", photo.urls)
        intent.putExtra("authorName", photo.user)
        startActivity(intent)
    }

    private lateinit var recyclerView: RecyclerView
    private lateinit var photoAdapter: PhotoAdapter
    private lateinit var photosFragmentViewModel: PhotosFragmentViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_photos, container, false)

        val mLayoutManager = GridLayoutManager(context, 3)

        photoAdapter = PhotoAdapter(this)
        photosFragmentViewModel = ViewModelProviders.of(this.activity!!).get(PhotosFragmentViewModel::class.java)
        photosFragmentViewModel.photos.observe(this.activity!!, Observer { photos -> photos.let { photoAdapter.setupPhotoList(it) } })
        photosFragmentViewModel.loadPhotos()


        recyclerView = view.findViewById(R.id.rv_photos)
        recyclerView.layoutManager = mLayoutManager
        recyclerView.itemAnimator = DefaultItemAnimator()
        recyclerView.isNestedScrollingEnabled = false
        recyclerView.adapter = photoAdapter

        return view
    }



}

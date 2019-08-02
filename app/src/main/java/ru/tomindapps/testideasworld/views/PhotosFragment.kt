package ru.tomindapps.testideasworld.views

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView

import ru.tomindapps.testideasworld.R
import ru.tomindapps.testideasworld.adapters.PhotoAdapter
import ru.tomindapps.testideasworld.models.Photo
import android.graphics.Rect
import android.os.AsyncTask
import android.util.Log
import android.util.TypedValue
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.Dispatchers
import ru.tomindapps.testideasworld.view_models.PhotosFragmentViewModel
import ru.tomindapps.testideasworld.workers.PhotoLoader


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


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

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment PhotosFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PhotosFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}

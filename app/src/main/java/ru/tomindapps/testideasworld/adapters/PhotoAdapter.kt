package ru.tomindapps.testideasworld.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.photos_item_row.view.*
import ru.tomindapps.testideasworld.R
import ru.tomindapps.testideasworld.models.Photo

class PhotoAdapter(listener: MyAdapterListener) :
RecyclerView.Adapter<PhotoAdapter.MyViewHolder>(){

    var listener: MyAdapterListener
    var photoList = emptyList<Photo>()

    init {
        this.listener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.photos_item_row, parent, false)
        return MyViewHolder(v)
    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(photoList[position])
        applyClickEvents(holder, position)

    }

    fun setupPhotoList(photos: ArrayList<Photo>){
        this.photoList = photos
        notifyDataSetChanged()
    }

    private fun applyClickEvents(holder: MyViewHolder, position: Int) {
        holder.rvItem.setOnClickListener{listener.onRowClicked(position) }
        holder.ivFavorite.setOnClickListener{ listener.onFavoriteClicked(position) }
    }

    inner class MyViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val ivThumbnail = itemView.findViewById<ImageView>(R.id.iv_thumbnail)
        val ivFavorite = itemView.findViewById<ImageView>(R.id.iv_favorite)
        val rvItem = itemView.findViewById<LinearLayout>(R.id.rv_item)


        fun bind(photo: Photo){
            Picasso.get().load(photo.urls).into(ivThumbnail)
        }
    }

    interface MyAdapterListener {
        fun onFavoriteClicked(position: Int)
        fun onRowClicked(position: Int)
    }
}
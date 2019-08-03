package ru.tomindapps.testideasworld.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.tomindapps.testideasworld.R
import ru.tomindapps.testideasworld.models.Favorite

class FavoriteAdapter (listener: MyAdapterListener) :
    RecyclerView.Adapter<FavoriteAdapter.MyViewHolder>(){

    var listener: MyAdapterListener = listener
    var photoList = emptyList<Favorite>()

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

    fun setupPhotoList(photos: ArrayList<Favorite>){
        this.photoList = photos
        notifyDataSetChanged()
    }

    private fun applyClickEvents(holder: MyViewHolder, position: Int) {
        holder.ivThumbnail.setOnClickListener{listener.onRowClicked(position) }
        holder.ivFavorite.setOnClickListener{ listener.onFavoriteClicked(position) }
    }

    inner class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val ivThumbnail = itemView.findViewById<ImageView>(R.id.iv_thumbnail)
        val ivFavorite = itemView.findViewById<ImageView>(R.id.iv_favorite)
        val rvItem = itemView.findViewById<LinearLayout>(R.id.rv_item)


        fun bind(favorite: Favorite){
            Picasso.get().load(favorite.urls).into(ivThumbnail)
        }

    }

    interface MyAdapterListener {
        fun onFavoriteClicked(position: Int)
        fun onRowClicked(position: Int)
    }
}
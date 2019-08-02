package ru.tomindapps.testideasworld.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import ru.tomindapps.testideasworld.R

class ElementActivity : AppCompatActivity() {

    private lateinit var tvName: TextView
    private lateinit var ivBig: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_element)

        tvName = findViewById(R.id.tvName)
        ivBig = findViewById(R.id.ivBig)

        var imgPath = ""
        var authorName = ""

        try {
            var bundle = intent.extras
            imgPath = bundle.getString("imgPath")
            Picasso.get().load(imgPath).into(ivBig)
            authorName = bundle.getString("authorName")
            tvName.text = authorName
        } catch (ex: Exception) {
        }
    }
}

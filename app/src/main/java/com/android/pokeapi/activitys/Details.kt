package com.android.pokeapi.activitys

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.android.pokeapi.R
import com.android.pokeapi.utils.UriImage

class Details : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        //intent
        val intent = intent
        val id = intent.getStringExtra(MainActivity.Companion.ID)
        val name = intent.getStringExtra(MainActivity.Companion.NAME)
        val height = intent.getStringExtra(MainActivity.Companion.HEIGHT)
        val weight = intent.getStringExtra(MainActivity.Companion.WEIGHT)

        //components
        val mName = findViewById<View>(R.id.txttitle) as TextView
        val mHeight = findViewById<View>(R.id.txtheight) as TextView
        val mWeight = findViewById<View>(R.id.txtweight) as TextView
        val mImageSend = findViewById<View>(R.id.imgSend) as ImageView

        val imageUri =
            Uri.parse("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${id}.png")

        //setcomponents
        mName.text = name.toString()
        mHeight.text = height.toString()
        mWeight.text = weight.toString()
        UriImage.DownloadImageFromInternet(findViewById(R.id.imageView))
            .execute(imageUri.toString())

        //onclick
        mImageSend.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT, "Olá, veja que bacana este pokémon.\n\n" +
                            "Pokémon: $name\nHeight: $height \nWeight: $weight \nPhoto: $imageUri"
                )
                type = "text/plain"
            }
            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }
    }
}
package com.android.pokeapi.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.android.pokeapi.R
import com.android.pokeapi.models.ItemResults
import com.android.pokeapi.utils.UriImage

class ListAdapter(context: Context, private val elements: List<ItemResults>) : ArrayAdapter<ItemResults?>(context!!, R.layout.item_menu_layout, elements) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        //Replace
        var id = elements[position].url?.replace("https://pokeapi.co/api/v2/pokemon/", "", ignoreCase = true)
        var photoId  = id?.replace("/", "", ignoreCase = true)

        //Inflater
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val rowView = inflater.inflate(R.layout.item_menu_layout, parent, false)
        val name = rowView.findViewById<View>(R.id.txtname) as TextView
        val imageUri = Uri.parse("https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/${photoId}.png")


        //SetComponents
        UriImage.DownloadImageFromInternet(rowView.findViewById(R.id.imageView)).execute(imageUri.toString())
        name.text =  photoId + " | " + elements[position].name?.toUpperCase()

        return rowView
    }
}



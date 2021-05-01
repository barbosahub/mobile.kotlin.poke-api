package com.android.pokeapi.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class ItemResults {
    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("url")
    @Expose
    var url: String? = null

}
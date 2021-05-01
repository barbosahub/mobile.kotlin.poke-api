package com.android.pokeapi.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Pokemon {
    @SerializedName("id")
    @Expose
    var id: Int? = null

    @SerializedName("name")
    @Expose
    var name: String? = null

    @SerializedName("weight")
    @Expose
    var weight: Int? = null

    @SerializedName("height")
    @Expose
    var height: Int? = null

}
package com.android.pokeapi.interfaces

import com.android.pokeapi.models.ItemPokemon
import com.android.pokeapi.models.Pokemon
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path


interface IGitApi {
    @Headers("Content-type: application/json")

    @GET("api/v2/pokemon/")
    fun findList(): Call<ItemPokemon>

    @GET("api/v2/pokemon/{id}")
    fun findById(@Path("id") id: String?): Call<Pokemon?>?

}
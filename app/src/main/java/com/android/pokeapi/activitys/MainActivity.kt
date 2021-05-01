package com.android.pokeapi.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.android.pokeapi.R
import com.android.pokeapi.adapters.ListAdapter
import com.android.pokeapi.models.ItemPokemon
import com.android.pokeapi.models.Pokemon
import com.android.pokeapi.retrofit.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    private var context = this@MainActivity
    lateinit var listView: ListView
    lateinit var pullToRefresh: SwipeRefreshLayout
    lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initComponents()

        pullToRefresh.setOnRefreshListener {
            progressBar(true)
            pullToRefresh.isRefreshing = false
            getPokemonList()
        }
        progressBar(true)
        getPokemonList()
    }

    private fun getPokemonList() {
        val call: Call<ItemPokemon> = ApiClient.get.findList()
        call.enqueue(object : Callback<ItemPokemon> {

            override fun onResponse(call: Call<ItemPokemon>, response: Response<ItemPokemon>) {
                val list = response!!.body()
                val lista = list?.results
                if (list != null) {
                    val adapter = lista?.let { ListAdapter(context, it) }

                    listView!!.adapter = adapter
                    progressBar(false)
                    listView!!.onItemClickListener =
                        AdapterView.OnItemClickListener { adapterView, view, i, l ->
                            getPokemonByName(lista?.get(i)?.name.toString())
                        }
                }
            }

            override fun onFailure(call: Call<ItemPokemon>, t: Throwable?) {
                showToast(t?.message.toString())
            }
        })
    }

    private fun getPokemonByName(name : String) {
        val call: Call<Pokemon?>? = ApiClient.get.findById(name)
        call?.enqueue(object : Callback<Pokemon?> {

            override fun onResponse(call: Call<Pokemon?>, response: Response<Pokemon?>) {
                val pokemon = response!!.body()
                if (response.code() == 200) {
                    val intent = Intent(context, Details::class.java)
                    intent.putExtra(ID, pokemon?.id.toString())
                    intent.putExtra(NAME, pokemon?.name.toString())
                    intent.putExtra(HEIGHT, pokemon?.height.toString())
                    intent.putExtra(WEIGHT, pokemon?.weight.toString())
                    startActivity(intent)
                }else{
                    showToast("error ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Pokemon?>, t: Throwable) {
                showToast(t?.message.toString())
            }
        })
    }

    private fun initComponents() {
        listView = findViewById(R.id.listview)
        pullToRefresh = findViewById<View>(R.id.pulltorefresh) as SwipeRefreshLayout
        progressBar = findViewById<ProgressBar>(R.id.progressBar)
    }

    private fun progressBar(isVisible: Boolean) {
        progressBar.isVisible = isVisible
    }

    fun showToast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val ID = "package com.android.pokeapi.activitys.ID"
        const val NAME = "package com.android.pokeapi.activitys.NAME"
        const val HEIGHT = "package com.android.pokeapi.activitys.HEIGHT"
        const val WEIGHT = "package com.android.pokeapi.activitys.WEIGHT"
    }
}


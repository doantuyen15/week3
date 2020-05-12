package com.exercises.week3

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var adapter : MoviesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val data = getDataJson(DataCenter.getMovieJsonString()) as ArrayList<MoviesModel>
        adapter = MoviesAdapter(this, data, listener)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val toolbars: MenuInflater = menuInflater
        toolbars.inflate(R.menu.tool_bars, menu)
        if (viewMode == "List"){
            menu.findItem(R.id.mList).isVisible = false
            menu.findItem(R.id.mGrid).isVisible = true
        } else if (viewMode == "Grid") {
            menu.findItem(R.id.mList).isVisible = true
            menu.findItem(R.id.mGrid).isVisible = false
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mList -> {
                rv.layoutManager = LinearLayoutManager(this)
                viewMode = "List"
                rv.adapter = adapter
                invalidateOptionsMenu()
            }
            R.id.mGrid -> {
                rv.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                viewMode = "Grid"
                rv.adapter = adapter
                invalidateOptionsMenu()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getDataJson(JSON_DATA : String): List<MoviesModel>? {
        val movieSrc: JsonObject = Gson().fromJson(JSON_DATA, JsonObject::class.java)
        val getResults : JsonElement = movieSrc.getAsJsonArray("results")
        val typeToken = object  : TypeToken<List<MoviesModel>>() {}.type
        return Gson().fromJson(getResults, typeToken)
    }

    private val listener = object : MoviesAdapter.OnClickListener {
        override fun onClick(movie: MoviesModel) {
            startProfileActivity(movie)
        }
    }

    private fun startProfileActivity(movie: MoviesModel){
        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
        intent.putExtra(MOVIE_TITLE_KEY, movie.title)
        intent.putExtra(MOVIE_PATH_KEY, movie.poster_path)
        intent.putExtra(MOVIE_OVERVIEW_KEY, movie.overview)
        intent.putExtra(MOVIE_VOTE_KEY, movie.vote_average)
        intent.putExtra(MOVIE_DATE_KEY, movie.release_date)
        startActivity(intent)
    }

}
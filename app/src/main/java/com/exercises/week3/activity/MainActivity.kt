package com.exercises.week3.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.exercises.week3.R
import com.exercises.week3.fragment.MovieFragment
import com.exercises.week3.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MovieFragment.FragHomeCallback {

    private var favorite = ArrayList<MoviesModel>()

    override fun onCreate(dataTransfer: Bundle?) {
        super.onCreate(dataTransfer)
        setContentView(R.layout.activity_main)
        if (supportFragmentManager.backStackEntryCount == 0) {
            setMovieFragment(getDataJson(DataCenter.getNowPlayingMovieJson()))
        }
        bottomMenu.setOnNavigationItemSelectedListener(navigationListener)
    }

    private var navigationListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {
                if (!it.isChecked) setMovieFragment(getDataJson(DataCenter.getNowPlayingMovieJson()))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_toprating -> {
                if (!it.isChecked) setMovieFragment(getDataJson(DataCenter.getTopRateMovieJson()))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                if (!it.isChecked) favorite.let { it1 -> setMovieFragment(it1) }
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun setMovieFragment(data: ArrayList<MoviesModel>) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MovieFragment(data))
            .commit()
    }

    override fun moveToProfileCallback(movieInfo: MoviesModel) {
        startProfileActivity(movieInfo)
    }

    private fun startFragmentFavorite(favorite: ArrayList<MoviesModel>) {

    }

    private fun getDataJson(JSON_DATA : String): ArrayList<MoviesModel> {
        val movieSrc: JsonObject = Gson().fromJson(JSON_DATA, JsonObject::class.java)
        val getResults : JsonElement = movieSrc.getAsJsonArray("results")
        val typeToken = object  : TypeToken<List<MoviesModel>>() {}.type
        return Gson().fromJson(getResults, typeToken)
    }

    private fun startProfileActivity(movie: MoviesModel){
        val bundle = Bundle()
        bundle.putParcelable("data", movie)
        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
        intent.putExtras(bundle)
//        intent.putExtra(MOVIE_TITLE_KEY, movie.title)
//        intent.putExtra(MOVIE_PATH_KEY, movie.poster_path)
//        intent.putExtra(MOVIE_OVERVIEW_KEY, movie.overview)
//        intent.putExtra(MOVIE_VOTE_KEY, movie.vote_average)
//        intent.putExtra(MOVIE_DATE_KEY, movie.release_date)

        startActivityForResult(intent, 3000)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 3000){
            if(resultCode == Activity.RESULT_OK){
                val bundle = data?.extras
                bundle?.let{
                    val movie = it.getParcelable("data") as? MoviesModel
                    if (movie != null && !favorite.contains(movie)) {
                        favorite.add(movie)
                    }
                }
            }
        }
    }
}
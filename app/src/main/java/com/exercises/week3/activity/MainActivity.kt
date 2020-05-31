package com.exercises.week3.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.exercises.week3.R
import com.exercises.week3.adapter.MoviesAdapter
import com.exercises.week3.fragment.FavoriteFragment
import com.exercises.week3.fragment.MovieFragment
import com.exercises.week3.model.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity(), MovieFragment.FragHomeCallback, FavoriteFragment.FragFavoriteCallback {

    private var favorite = ArrayList<MoviesModel>()
    private var movieData = ArrayList<MoviesModel>()
    private lateinit var dao: MoviesDAO
    private lateinit var moviesAdapter: MoviesAdapter


    override fun onCreate(dataTransfer: Bundle?) {
        super.onCreate(dataTransfer)
        setContentView(R.layout.activity_main)
        initRoomDatabase()
        loadCurrentState()
        getFavoriteMovies()
        bottomMenu.setOnNavigationItemSelectedListener(navigationListener)
    }

    private fun loadCurrentState(){
        val preference = getSharedPreferences(AppConfig, Context.MODE_PRIVATE)
        if (supportFragmentManager.backStackEntryCount == 0) {
//            setMovieFragment(getDataJson(DataCenter.getNowPlayingMovieJson()))
            when(preference.getInt("currentState", R.id.navigation_favorite)){
                R.id.navigation_home -> {
//                    getDataFromApi("Now")
                    startMovieFragment("Now")
                    bottomMenu.menu.findItem(R.id.navigation_home).isChecked = true
                }
                R.id.navigation_toprating -> {
//                    getDataFromApi("Top")
                    startMovieFragment("Top")
                    bottomMenu.menu.findItem(R.id.navigation_toprating).isChecked = true
                }
                R.id.navigation_favorite -> {
                    startFragmentFavorite()
                    bottomMenu.menu.findItem(R.id.navigation_favorite).isChecked = true
                }
            }
        }
    }

    private fun saveCurrentState(pos: Int) {
        val preference = getSharedPreferences(AppConfig, Context.MODE_PRIVATE)
        preference.edit()
            .putInt("currentState", pos)
            .apply()
    }

    private var navigationListener = BottomNavigationView.OnNavigationItemSelectedListener {
        when (it.itemId) {
            R.id.navigation_home -> {
                if (!it.isChecked) {
//                    getDataFromApi("Now")
                    startMovieFragment("Now")
                }
                saveCurrentState(R.id.navigation_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_toprating -> {
                if (!it.isChecked) {
//                    getDataFromApi("Top")
                    startMovieFragment("Top")
                }
                saveCurrentState(R.id.navigation_toprating)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_favorite -> {
                if (!it.isChecked) startFragmentFavorite()
                saveCurrentState(R.id.navigation_favorite)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun initRoomDatabase() {
        val db = MoviesDatabase.invoke(this)
        dao = db.moviesDAO()
    }

    private fun getFavoriteMovies() {
        val movies = dao.getAll() // get Students from ROOM database
        this.favorite.addAll(movies) // add to student list
//        moviesAdapter.notifyDataSetChanged() // notify data changed
    }

    private fun startMovieFragment(destination: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, MovieFragment(destination))
            .addToBackStack(null)
            .commit()
    }

    override fun moveToProfileCallback(movieInfo: MoviesModel) {
        startProfileActivity(movieInfo)
    }

    private fun startFragmentFavorite() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, FavoriteFragment())
            .addToBackStack(null)
            .commit()
    }

//    private fun getDataJson(JSON_DATA : String): ArrayList<MoviesModel> {
//        val movieSrc: JsonObject = Gson().fromJson(JSON_DATA, JsonObject::class.java)
//        val getResults : JsonElement = movieSrc.getAsJsonArray("results")
//        val typeToken = object  : TypeToken<List<MoviesModel>>() {}.type
//        return Gson().fromJson(getResults, typeToken)
//    }

    private fun startProfileActivity(movie: MoviesModel){
        val bundle = Bundle()
        bundle.putParcelable("data", movie)
        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
        intent.putExtras(bundle)
        startActivityForResult(intent, 3000)
//        intent.putExtra(MOVIE_TITLE_KEY, movie.title)
//        intent.putExtra(MOVIE_PATH_KEY, movie.poster_path)
//        intent.putExtra(MOVIE_OVERVIEW_KEY, movie.overview)
//        intent.putExtra(MOVIE_VOTE_KEY, movie.vote_average)
//        intent.putExtra(MOVIE_DATE_KEY, movie.release_date)
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

//    private fun getDataFromApi(key: String) {
//        val callBack = object :Callback<MovieResponse> {
//            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
//                //todo something
//            }
//
//            override fun onResponse(
//                call: Call<MovieResponse>?,
//                response: Response<MovieResponse>?
//            ) {
//                response?.let {
//                    movieData = it.body().result
//                }
//            }
//        }
//        if (key == "Top") MovieService.getInstance().getApi(page).getTopRateMovie().enqueue(callBack)
//        else if (key == "Now") MovieService.getInstance().getApi(page).getNowPlaying().enqueue(callBack)
//        Log.d("Main", "************${movieData.size}")
//    }
}
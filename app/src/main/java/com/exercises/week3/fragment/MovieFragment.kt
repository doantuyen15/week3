package com.exercises.week3.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercises.week3.R
import com.exercises.week3.adapter.MoviesAdapter
import com.exercises.week3.model.MoviesModel
import com.exercises.week3.model.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class MovieFragment(des: String) : Fragment() {

    private val destination = des
    private var mData = ArrayList<MoviesModel>()
    private lateinit var adapter: MoviesAdapter
    private lateinit var db: MoviesDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = MoviesDatabase.invoke(activity as Context)
        getDataFromApi(0)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter(activity, mData, clickListener, bottomReachedListener)
        rvMovie.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater) {
        menuInflater.inflate(R.menu.tool_bars, menu)
        super.onCreateOptionsMenu(menu, menuInflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        itemGrid = menu.findItem(R.id.mGrid)
        itemList = menu.findItem(R.id.mList)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.mList -> {
                itemList.isVisible = false
                itemGrid.isVisible = true
                rvMovie.layoutManager = LinearLayoutManager(activity)
                rvMovie.adapter = adapter
            }
            R.id.mGrid -> {
                rvMovie.layoutManager = GridLayoutManager(activity, 2)
                itemGrid.isVisible = false
                itemList.isVisible = true
                rvMovie.adapter = adapter
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private lateinit var callBack: FragHomeCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = activity as FragHomeCallback
    }

    private val clickListener = object : MoviesAdapter.OnClickListener {
        override fun onClick(movie: MoviesModel) {
            callBack.moveToProfileCallback(movie)
        }

        override fun onLongClick(movie: MoviesModel) {
            val builder = AlertDialog.Builder(activity!!)
                .setMessage("Add this movie to favorites?")
                .setPositiveButton("YES") { _, _ ->
                    addToFavorite(movie)
                }
                .setNegativeButton("NO") { dialog, _ -> dialog?.dismiss() }
            val dialog = builder.create();
            dialog.show()
        }
    }

    private val bottomReachedListener = object : MoviesAdapter.OnBottomReached {
        override fun loadMore(position: Int) {
            getDataFromApi(position)
        }

    }

    private fun addToFavorite(movie: MoviesModel) {
        db.moviesDAO().insert(movie)
    }

    private fun getDataFromApi(position: Int) {
        val page = if (position != 0) {
            position.div(20)+1
        } else 1
        val callBack = object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>?, t: Throwable?) {
                Log.d("MovieFrag", "***********dut cap!")
            }

            override fun onResponse(
                call: Call<MovieResponse>?,
                response: Response<MovieResponse>?
            ) {
                response?.let {
                    mData.addAll(it.body().result)
                    adapter.notifyItemInserted(position)
                    Log.d("MovieFrag", "***********${page}")
                }
            }
        }
        if (destination == "Top") MovieService.getInstance().getApi().getTopRateMovie(page).enqueue(callBack)
        else if (destination == "Now") MovieService.getInstance().getApi().getNowPlaying(page).enqueue(callBack)
    }

    interface FragHomeCallback {
        fun moveToProfileCallback(movieInfo: MoviesModel)
    }


}
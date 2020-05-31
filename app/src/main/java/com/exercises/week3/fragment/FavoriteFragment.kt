package com.exercises.week3.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercises.week3.R
import com.exercises.week3.adapter.MoviesAdapter
import com.exercises.week3.model.*
import kotlinx.android.synthetic.main.fragment_home.*
import kotlin.collections.ArrayList

class FavoriteFragment: Fragment() {

    private lateinit var adapter: MoviesAdapter
    private lateinit var db: MoviesDatabase
    lateinit var dao: MoviesDAO
    private val favoriteMovies = ArrayList<MoviesModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = MoviesDatabase.invoke(activity as Context)
        initRoomDatabase()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
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

    private fun initRoomDatabase() {
        val db = MoviesDatabase.invoke(activity as Context)
        dao = db.moviesDAO()
        favoriteMovies.addAll(dao.getAll())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MoviesAdapter(activity, favoriteMovies, listener)
        rvMovie.adapter = adapter
    }

    private lateinit var callBack: FragFavoriteCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = activity as FragFavoriteCallback
    }

    private val listener = object : MoviesAdapter.OnClickListener {
        override fun onClick(movie: MoviesModel) {
            callBack.moveToProfileCallback(movie)
        }

        override fun onLongClick(movie: MoviesModel) {
            val builder = AlertDialog.Builder(activity as Context)
            builder
                .setMessage("Bạn muốn xóa phim: ${movie.title} ?")
                .setPositiveButton("OK") { _, _ ->
                    removeFromFavorite(movie)
                }
                .setNegativeButton(
                    "Cancel"
                ) { dialog, _ -> dialog?.dismiss() }

            val myDialog = builder.create();
            myDialog.show()
        }
    }

    private fun removeFromFavorite(movie: MoviesModel) {
        dao.delete(movie)
        favoriteMovies.remove(movie)
        adapter.notifyDataSetChanged()
    }

    interface FragFavoriteCallback {
        fun moveToProfileCallback(movieInfo: MoviesModel)
    }
}
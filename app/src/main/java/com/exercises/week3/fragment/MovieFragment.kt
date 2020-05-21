package com.exercises.week3.fragment

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.exercises.week3.R
import com.exercises.week3.adapter.MoviesAdapter
import com.exercises.week3.model.MoviesModel
import kotlinx.android.synthetic.main.fragment_home.*

class MovieFragment(data: ArrayList<MoviesModel>) : BaseFragment(){


    //    private var mAdapter = adapter
    private val mData = data
    private lateinit var adapter : MoviesAdapter
    private val isSorted = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = MoviesAdapter(activity, mData, clickListener)
        rvMovie.adapter = adapter
//        rvMovie.layoutManager = LinearLayoutManager(activity)

    }

    override fun onCreateOptionsMenu(menu: Menu, menuInflater: MenuInflater){
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

    private lateinit var callBack : FragHomeCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callBack = activity as FragHomeCallback
    }

    private val clickListener = object :
        MoviesAdapter.OnClickListener {
        override fun onClick(movie: MoviesModel) {
            callBack.moveToProfileCallback(movie)
        }
    }

//    private val longClickListener = object :
//        MoviesAdapter.OnLongClickListener {
//        override fun onLongClick(movie: MoviesModel) {
////            TODO("Not yet implemented")
//        }
//    }

//    override fun onSaveInstanceState(outState: Bundle) {
//        outState.putString("data", movie)
//        super.onSaveInstanceState(outState)
//    }

    interface FragHomeCallback{
        fun moveToProfileCallback(movieInfo: MoviesModel)
    }


}
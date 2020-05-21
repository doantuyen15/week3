//package com.exercises.week3.fragment
//
//import android.content.Context
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import com.exercises.week3.R
//import com.exercises.week3.adapter.MoviesAdapter
//import com.exercises.week3.model.MoviesModel
//import kotlinx.android.synthetic.main.fragment_home.*
//
//class FavoriteFragment (
//    adapter: MoviesAdapter,
//    data: ArrayList<MoviesModel>
//) : BaseFragment(){
//
//    private var mAdapter = adapter
//    private val mData = data
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?): View? {
//
//        return inflater.inflate(R.layout.fragment_home, container, false)
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        mAdapter = MoviesAdapter(activity, mData, listener)
//        rvMovie.adapter = mAdapter
//    }
//
//    private lateinit var callBack : HomeFragment.FragHomeCallback
//    override fun onAttach(context: Context) {
//        super.onAttach(context)
//        callBack = activity as HomeFragment.FragHomeCallback
//    }
////
//    private val listener = object :
//        MoviesAdapter.OnClickListener {
//        override fun onClick(movie: MoviesModel) {
//            callBack.moveToProfileCallback(movie)
//        }
//    }
//}
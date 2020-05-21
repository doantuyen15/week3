package com.exercises.week3.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.exercises.week3.R
import com.exercises.week3.model.*


class MoviesAdapter(private val context: FragmentActivity?, var data: ArrayList<MoviesModel>, private val itemClickListener: OnClickListener) : RecyclerView.Adapter<MoviesAdapter.MoviesVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesVH {
        val view = LayoutInflater.from(context).inflate(R.layout.movies_item_new, parent, false)
        return MoviesVH(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MoviesVH, position: Int) {
        holder.container.startAnimation(
            AnimationUtils.loadAnimation(
                context,
                R.anim.translate_in
            )
        )
        val movie = data[position]
//        holder.poster.visibility = View.GONE
//        holder.posterGrid.visibility = View.VISIBLE
//        holder.overview.visibility = View.GONE
        if (context != null) {
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .centerCrop()
                .into(holder.poster)
        }
        holder.title.text = movie.title
        //holder.overview.text = movie.overview
        holder.itemView.setOnClickListener {
            itemClickListener.onClick(movie)
        }
//        holder.itemView.setOnLongClickListener {
//            itemClickListener.onLongClick(movie)
//            true
//        }
    }

    interface OnClickListener {
        fun onClick(movie: MoviesModel)
//        fun onLongClick(movie: MoviesModel)
    }

//    interface OnLongClickListener {
//        fun onLongClick(movie: MoviesModel)
//    }

    class MoviesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val container = itemView.findViewById<CardView>(R.id.container)
        val poster = itemView.findViewById<ImageView>(R.id.ivItemPosterSmallNew)
        //        val posterGrid = itemView.findViewById<ImageView>(R.id.ivItemPosterNew)
        val title = itemView.findViewById<TextView>(R.id.tvItemTitleNew)
//        val overview = itemView.findViewById<TextView>(R.id.tvItemOverviewNew)
    }

}
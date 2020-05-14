package com.exercises.week3

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import de.hdodenhof.circleimageview.CircleImageView

class MoviesAdapter (private val context: Context, var data: ArrayList<MoviesModel>, private val listener: OnClickListener) : RecyclerView.Adapter<MoviesAdapter.MoviesVH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoviesVH {
        val view = LayoutInflater.from(context).inflate(R.layout.movies_item_new, parent, false)
        return MoviesVH(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: MoviesVH, position: Int) {
        val movie = data[position]
        if(viewMode == "List") {
            holder.poster.visibility = View.VISIBLE
            holder.posterGrid.visibility = View.GONE
            holder.overview.visibility = View.VISIBLE
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .centerCrop()
                .into(holder.poster)
        } else if (viewMode == "Grid"){
            holder.poster.visibility = View.GONE
            holder.posterGrid.visibility = View.VISIBLE
            holder.overview.visibility = View.GONE
            Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500${movie.poster_path}")
                .centerCrop()
                .into(holder.posterGrid)
        }
        holder.title.text = movie.title
        holder.overview.text = movie.overview
        holder.itemView.setOnClickListener {
            listener.onClick(movie)
        }
    }

    interface OnClickListener {
        fun onClick(movie: MoviesModel)
    }

    class MoviesVH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val poster = itemView.findViewById<ImageView>(R.id.ivItemPosterSmallNew)
        val posterGrid = itemView.findViewById<ImageView>(R.id.ivItemPosterNew)
        val title = itemView.findViewById<TextView>(R.id.tvItemTitleNew)
        val overview = itemView.findViewById<TextView>(R.id.tvItemOverviewNew)
    }

}
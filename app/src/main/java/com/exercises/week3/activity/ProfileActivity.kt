package com.exercises.week3.activity

import android.os.Bundle
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.exercises.week3.R
import com.exercises.week3.model.MoviesModel
import kotlinx.android.synthetic.main.activity_details.*

class ProfileActivity : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        val bundle = intent.extras
        val data =  bundle?.getParcelable("data") as? MoviesModel

        getAndDisplayData(data)
    }

    private fun getAndDisplayData(data: MoviesModel?) {
        if (data != null) {
            val title = data.title
            val overview = data.overview
            val posterPath = data.poster_path
            val vote = data.voteAverage
            val date = "Release date: ${data.releaseDate}"

            ratingBar.rating = vote.div(2).toFloat()
            ratingBar.onRatingBarChangeListener = this

            tvName.text = title
            tvDate.text = date
            tvOverView.text = overview
            Glide.with(this)
                .load("https://image.tmdb.org/t/p/w500/$posterPath")
                .centerCrop()
                .placeholder(R.drawable.place_holder)
                .into(ivPoster)
        }
    }
    override fun onRatingChanged(ratingBar: RatingBar?, rating: Float, fromUser: Boolean) {
        Toast.makeText(this, "Voted! $rating", Toast.LENGTH_LONG).show()
    }

}

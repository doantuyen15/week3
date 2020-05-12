package com.exercises.week3

import android.os.Bundle
import android.view.View
import android.widget.RatingBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_details.*

class ProfileActivity : AppCompatActivity(), RatingBar.OnRatingBarChangeListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)



        getAndDisplayData()

    }
    private fun getAndDisplayData() {
        val data = intent.extras
        if (data != null) {
            val title = data.getString("MOVIE_TITLE_KEY")
            val overview = data.getString("MOVIE_OVERVIEW_KEY")
            val posterPath = data.getString("MOVIE_PATH_KEY")
            val vote = data.getFloat("MOVIE_VOTE_KEY")
            val date = "Release date: ${data.getString("MOVIE_DATE_KEY")}"

            ratingBar.rating = vote.div(2)
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

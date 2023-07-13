package com.example.movieshub.util

import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieshub.R

@BindingAdapter("imageBind")
fun ImageView.imageFromUrl(url: String?) {
    Glide.with(this.context)
        .load(WebServiceUtil.IMAGEBASEURL + url)
        .placeholder(R.drawable.img_placeholder)
        .into(this)

}

@BindingAdapter("intToString")
fun TextView.textFromInt(votes: Int) {
    this.text = votes.toString() + " Votes"
}

@BindingAdapter("rattingFormat")
fun RatingBar.formatRatting(ratting: Double) {
    this.rating = (ratting / 2).toFloat()
}
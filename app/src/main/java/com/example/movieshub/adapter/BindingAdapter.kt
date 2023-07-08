package com.example.movieshub.adapter

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.example.movieshub.R

@BindingAdapter("android:ivIcon")
fun ImageView.ivIcon(url: String) {
    Glide.with(context).load(url).placeholder(R.drawable.nav_movie).into(this)
}

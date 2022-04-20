package com.mabbr.news

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun loadImagefromUrl(imageView: ImageView,url:String){
    Glide.with(imageView)
        .load(url)
        .into(imageView)
}
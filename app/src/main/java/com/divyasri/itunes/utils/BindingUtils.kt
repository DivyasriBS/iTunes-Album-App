package com.tw.gojek.utils

import android.content.Context
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageUrl(imageView: ImageView, url: String?) {
    val context: Context = imageView.context
    Glide.with(context).load(url).into(imageView)
}

@BindingAdapter("binding")
fun bindTextView(textview: AppCompatTextView, value: CharSequence) {
    textview.text = value.toString().toUpperCase()
}

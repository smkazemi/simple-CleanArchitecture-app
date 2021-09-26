package com.basalam.intern.android.util

import androidx.appcompat.widget.AppCompatImageView
import androidx.databinding.BindingAdapter
import coil.load
import com.basalam.intern.android.R
import com.google.android.material.imageview.ShapeableImageView


@BindingAdapter("imageUrl")
fun AppCompatImageView.bindImageUrl(url: String) {

    load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_image_24)
        error(R.drawable.ic_broken_image_24)
    }
}

@BindingAdapter("imageUrl")
fun ShapeableImageView.bindImageUrl(url: String) {

    load(url) {
        crossfade(true)
        placeholder(R.drawable.ic_image_24)
        error(R.drawable.ic_broken_image_24)
    }
}
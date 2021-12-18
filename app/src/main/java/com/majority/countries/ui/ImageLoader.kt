package com.majority.countries.ui

import android.widget.ImageView

interface ImageLoader {
    fun loadImage(imageView: ImageView, imageUrl: String?)
}
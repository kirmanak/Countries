package com.majority.countries.ui

import android.widget.ImageView
import com.squareup.picasso.Picasso
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageLoaderPicassoImpl @Inject constructor(
    private val picasso: Picasso,
) : ImageLoader {

    override fun loadImage(imageView: ImageView, imageUrl: String?) {
        Timber.v("loadImage() called with: imageView = $imageView, imageUrl = $imageUrl")
        val nonEmptyUrl = imageUrl?.takeUnless { it.isBlank() }
        picasso.load(nonEmptyUrl).into(imageView)
    }
}
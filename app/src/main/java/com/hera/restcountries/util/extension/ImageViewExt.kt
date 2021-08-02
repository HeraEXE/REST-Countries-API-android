package com.hera.restcountries.util.extension

import android.widget.ImageView
import coil.ImageLoader
import coil.decode.SvgDecoder
import coil.request.ImageRequest
import com.hera.restcountries.R

fun ImageView.loadSvg(url: String) {
    val imageLoader = ImageLoader.Builder(this.context)
        .componentRegistry { add(SvgDecoder(this@loadSvg.context)) }
        .build()

    val request = ImageRequest.Builder(this.context)
        .crossfade(true)
        .crossfade(500)
        .placeholder(R.drawable.image_placeholder)
        .error(R.drawable.image_placeholder)
        .data(url)
        .target(this)
        .build()

    imageLoader.enqueue(request)
}
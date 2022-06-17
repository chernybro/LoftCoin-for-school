package com.chernybro.loftcoin.utils.image_loader

import android.widget.ImageView
import com.chernybro.loftcoin.utils.image_loader.ImageLoader.ImageRequest

interface ImageLoader {

    fun load(url: String?): ImageRequest

    interface ImageRequest {
        fun into(view: ImageView)
    }
}
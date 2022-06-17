package com.chernybro.loftcoin.utils.image_loader

import android.widget.ImageView
import com.chernybro.loftcoin.utils.image_loader.ImageLoader.ImageRequest
import com.squareup.picasso.Picasso
import com.squareup.picasso.RequestCreator
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PicassoImageLoader @Inject constructor(private val picasso: Picasso) : ImageLoader {
    override fun load(url: String?): ImageRequest {
        return PicassoImageRequest(picasso.load(url))
    }

    private class PicassoImageRequest internal constructor(private val request: RequestCreator) :
        ImageRequest {
        override fun into(view: ImageView) {
            request.into(view)
        }
    }
}
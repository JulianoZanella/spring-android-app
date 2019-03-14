package com.julianozanella.springandroidapp.service

import android.widget.ImageView
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.config.ApiConfig
import com.squareup.picasso.Picasso


class ImageService {

    fun setCategoryImage(imgView: ImageView, id: String) {
        val url = "${ApiConfig.BUCKET_BASE_URL}/cat$id.jpg"
        setImage(imgView, url)
    }

    fun setProductImage(imgView: ImageView, id: String) {
        val url = "${ApiConfig.BUCKET_BASE_URL}/prod$id-small.jpg"
        setImage(imgView, url)
    }

    fun setProductLargeImage(imgView: ImageView, id: String) {
        val url = "${ApiConfig.BUCKET_BASE_URL}/prod$id.jpg"
        setImage(imgView, url)
    }

    private fun setImage(imgView: ImageView, url: String) {
        Picasso.get().load(url).placeholder(R.drawable.prod).into(imgView)
    }
}
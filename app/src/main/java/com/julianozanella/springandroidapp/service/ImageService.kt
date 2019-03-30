package com.julianozanella.springandroidapp.service

import android.content.ContentResolver
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageView
import com.julianozanella.springandroidapp.R
import com.julianozanella.springandroidapp.config.ApiConfig
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


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

    fun setClientImage(imgView: ImageView, id: String) {
        val url = "${ApiConfig.BUCKET_BASE_URL}/cp$id.jpg"
        Picasso.get().load(url).placeholder(R.drawable.avatar_blank).into(imgView)
    }

    private fun setImage(imgView: ImageView, url: String) {
        Picasso.get().load(url).placeholder(R.drawable.prod).into(imgView)
    }

    fun getBitmap(contentResolver: ContentResolver, uri: Uri): Bitmap {
        return MediaStore.Images.Media.getBitmap(contentResolver, uri)
    }

    fun getBitmap(extras: Bundle): Bitmap {
        return extras.get("data") as Bitmap
    }
}
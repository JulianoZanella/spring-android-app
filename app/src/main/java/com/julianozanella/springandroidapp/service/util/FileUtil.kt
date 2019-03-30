package com.julianozanella.springandroidapp.service.util

import android.content.Context
import android.graphics.Bitmap
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class FileUtil(private val context: Context) {

    fun createImageBody(bitmap: Bitmap): MultipartBody.Part {
        val file = createFileFromBitmap(bitmap, "profilePicture.jpg")
        val reqFile: RequestBody = RequestBody.create(MediaType.parse("mutlipart/form-data"), file)
        return MultipartBody.Part.createFormData("file", file.name, reqFile)
    }

    private fun createFileFromBitmap(bitmap: Bitmap, filename: String): File {
        //create a file to write bitmap data
        val f = File(context.cacheDir, filename)
        f.createNewFile()

        //Convert bitmap to byte array
        val bitmapdata = bitmapToJpg(bitmap)

        //write the bytes in file
        val fos = FileOutputStream(f)
        fos.write(bitmapdata)
        fos.flush()
        fos.close()
        return f
    }

    private fun bitmapToJpg(bitmap: Bitmap): ByteArray {
        val os = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 80, os)
        return os.toByteArray()
    }
}
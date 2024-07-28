package com.example.psysupapplication

import android.content.ContentResolver
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Base64
import java.io.ByteArrayOutputStream


data class Photo(
    val url: Uri
) {
    fun loadBase64(contentResolver: ContentResolver): String {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, url)
        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
        return Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
    }
}
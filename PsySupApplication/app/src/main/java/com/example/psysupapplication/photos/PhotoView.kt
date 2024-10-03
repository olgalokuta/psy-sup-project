package com.example.psysupapplication.photos

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale

// Декодирует и показывает фотографию из Base64 строки
@Composable
fun PhotoView(photo: String) {
    val bytes = Base64.decode(photo, Base64.DEFAULT)
    Image(
        modifier = Modifier.fillMaxHeight(),
        bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap(),
        contentScale = ContentScale.FillHeight,
        contentDescription = "ASD"
    )
}
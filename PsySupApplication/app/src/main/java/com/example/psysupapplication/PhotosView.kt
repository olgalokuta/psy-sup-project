package com.example.psysupapplication

import android.graphics.BitmapFactory
import android.util.Base64
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

@Composable
fun PhotosView(photos: List<String>) {
    if (photos.isNotEmpty()) {
        Row(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            for (photo in photos) {
                val bytes = Base64.decode(photo, Base64.DEFAULT)
                Image(
                    modifier = Modifier.fillMaxHeight(),
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.size).asImageBitmap(),
                    contentScale = ContentScale.FillHeight,
                    contentDescription = "ASD"
                )
            }
        }
    }
}
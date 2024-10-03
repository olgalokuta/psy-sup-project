package com.example.psysupapplication.photos

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


// Листаемый список фотографий поста
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
                PhotoView(photo)
            }
        }
    }
}
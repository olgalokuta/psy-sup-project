package com.example.psysupapplication

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.sp

@Composable
fun BottomPanel(state : MutableState<String>) : Unit {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(
            text = AnnotatedString("Посты"),
            fontSize = 20.sp,
            modifier = Modifier.clickable(
                onClick = { state.value = "PostsPage" }
            )
        )
        Text(text = "Создать пост",
            fontSize = 20.sp,
            modifier = Modifier.clickable(
                onClick = { state.value = "CreatePostPage" }
            )
        )
        Text(text = "Профиль",
            fontSize = 20.sp,
            modifier = Modifier.clickable(
                onClick = { state.value = "ProfilePage" }
            )
        )
    }
}
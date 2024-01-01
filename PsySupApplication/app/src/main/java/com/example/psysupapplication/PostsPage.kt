package com.example.psysupapplication

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PostsPage() : Unit {
    val u = User(
        1,
        "username",
        "email@gamil.com",
        "898788888"
    )
    val text = "Чувство юмора — самое важное качество в человеке. " +
            "Смех избавляет от массы проблем. Чувствуешь, что накрывает, улыбнись, " +
            "расслабься — все пройдет!"
    val post = Post (1, 1,"12.12.23",text)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
    ){
        LazyColumn {
            item {
                Box(
                    modifier = Modifier.padding(top = 30.dp)
                ){
                    Text(
                        text = "Посты других пользователей",
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left,
                        maxLines = 10,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 30.dp)
                            .fillMaxWidth()
                    )
                }
            }
            item { Spacer(modifier = Modifier.height(10.dp)) }
            items(10){
                ViewPost(u, post)
            }
        }
    }
}

@Composable
fun ViewPost(user : User, p : Post) : Unit {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.Center
    ){
        Column {
            ViewUserInPost(u = user, data = p.date)
            Card(
                elevation = CardDefaults.cardElevation(defaultElevation = 30.dp),
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxWidth(fraction = 0.9f)
                    .defaultMinSize(minHeight = 80.dp)
                    .clip(shape = RoundedCornerShape(20.dp)),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    text = p.text,
                    modifier = Modifier.padding(15.dp),
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Composable
fun ViewUserInPost(u : User, data : String) : Unit {
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 10.dp),
        modifier = Modifier
            .fillMaxWidth(fraction = 0.9f)
            .padding(10.dp)
            .height(70.dp)
            .clip(shape = RoundedCornerShape(20.dp))
            .background(Color.White),
        shape = RoundedCornerShape(15.dp)
    ) {
        Row {
            Image(
                painter = painterResource(id = R.drawable.default_avatar),
                contentDescription = "Default avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(60.dp)
                    .padding(5.dp)
                    .clip(RoundedCornerShape(50))
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.8f)
                    .padding(horizontal = 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column (
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.8f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = u.username,
                        fontSize = 24.sp
                    )
                    Text(text = data, fontSize = 20.sp)
                }
            }
        }
    }
}
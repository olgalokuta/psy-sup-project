package com.example.psysupapplication

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import com.example.psysupapplication.Post
import com.example.psysupapplication.R
import com.example.psysupapplication.User
import com.example.psysupapplication.UserAPI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.time.format.TextStyle

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = remember {
                mutableStateOf("Identification")
            }
            //mainPage()
            Crossfade(targetState = state, label = "") { currentSt ->
                when (currentSt.value) {
                    "Identification" -> IdentityPage(state)
                    else -> MainPage()
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdentityPage(stage : MutableState<String>) : Unit {
    var password by rememberSaveable { mutableStateOf("") }
    var nickname by remember { mutableStateOf(TextFieldValue("")) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(fraction = 0.7f),
        contentAlignment = Alignment.Center
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth(fraction = 0.9f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(modifier = Modifier.padding(10.dp)) {
                Text(
                    text = "Добро пожаловать!",
                    fontSize = 44.sp,
                    textAlign = TextAlign.Left,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .fillMaxWidth()
                )
            }

            TextField(
                value = nickname,
                onValueChange = {
                    nickname = it
                },
                label = { Text(text = "Ваш никнейм") },
                placeholder = { Text(text = "Введите никнейм") }
            )

            TextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Ваш пароль") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = {
                        stage.value = "MainPage"
                    },
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f)
                ) {
                    Text(
                        text = "Войти",
                        fontSize = 20.sp
                    )
                }
            }
        }
    }
}

@Composable
fun MainPage() : Unit {
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
    Box(modifier = Modifier.fillMaxWidth()){
        LazyColumn {
            item { CreatePostBox() }
            item {
                Box(modifier = Modifier.padding(start = 10.dp)) {
                    Text(
                        text = "Посты других пользователей",
                        fontSize = 28.sp,
                        textAlign = TextAlign.Left,
                        maxLines = 10,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(start = 10.dp)
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePostBox() :Unit {
    var text by remember { mutableStateOf("") }
    Box(
        modifier = Modifier
            .padding(top = 25.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.TopCenter
    ){
        Column (
            verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = text, onValueChange = { text = it },
                label = { Text(text = "Твой пост", fontSize = 24.sp)},
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.9f)
                    .defaultMinSize(minHeight = 150.dp),
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center
            ){
                Button(
                    onClick = {},
                    modifier = Modifier.fillMaxWidth(fraction = 0.7f)
                ) {
                    Text(
                        text = "Создать пост",
                        fontSize = 20.sp
                    )
                }
            }
            Spacer(modifier = Modifier.height(50.dp))
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
/*
fun sendRequest(id : Int) {
    var user : User
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    //http://127.0.0.1:8080/api/users/
    val retrofit = Retrofit.Builder()
        .baseUrl("http://local:8080/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    /*
    val api = retrofit.create(UserAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        var user = api.getUserById(id)
    }*/
}*/

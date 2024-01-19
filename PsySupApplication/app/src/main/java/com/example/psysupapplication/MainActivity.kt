package com.example.psysupapplication

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val state = remember {
                mutableStateOf(false)
            }

            val u = remember{
                mutableStateOf(User(
                    1,
                    "username",
                    "email@gamil.com",
                    "898788888",
                    "1234",
                    1,
                    true,
                    "2003-01-01",
                    emptyList()
                ))
            }


            Crossfade(targetState = state, label = "") { currentSt ->
                when (currentSt.value) {
                    false -> Enter(state, u)
                    //false -> RegistrationPage(state)
                    else -> MainPage(u.value)
//                    false -> RegistrationPage(state, u)
//                    else -> IdentityPage(state)

                }
            }
        }
    }
}

@Composable
fun MainPage(u : User) : Unit {
    val state = remember { mutableStateOf("PostsPage") }
    val userPostsList = remember { mutableStateOf(listOf<Post>()) }
    val allPublicPostsList = remember { mutableStateOf(listOf<Post>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
    ){
        Crossfade(targetState = state, label = "") { currentSt ->
            when (currentSt.value) {
                "PostsPage" -> PostsPage(u, allPublicPostsList)
                "CreatePostPage" -> CreatePage(u)
                "ProfilePage" -> ProfilePage(u, userPostsList)
            }
        }
        BottomPanel(state)
    }
}


fun sendRequest(id : Int) {
    var user : List<User>
    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    //http://127.0.0.1:8080/api/users/
    val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(UserAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        var user = api.getAllUsers()
    }
}

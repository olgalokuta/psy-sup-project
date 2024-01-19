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
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.text.AnnotatedString
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
                    //false -> IdentityPage(state, u)
                    false -> RegistrationPage(state)
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

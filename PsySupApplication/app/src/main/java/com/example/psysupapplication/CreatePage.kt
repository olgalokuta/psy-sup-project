package com.example.psysupapplication

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import java.io.ByteArrayOutputStream
import android.text.style.URLSpan
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePage(u : User, co : Context) : Unit {
    var text by remember { mutableStateOf("") }
    var dialogOpen by remember { mutableStateOf(false) }
    val isPublic = remember { mutableStateOf(false) }
    val ourList1 = emptyList<String>()




    val dsf = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(5)) { uri ->

        uri.forEach { it ->
            println(" every URI = $it")
            val bitmap = MediaStore.Images.Media.getBitmap(co.contentResolver, it)
            val outputStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.PNG, 80, outputStream)
            var srt = Base64.encodeToString(outputStream.toByteArray(), Base64.DEFAULT)
            ourList1.plus(srt)
        }
    }


    Box (
        modifier = Modifier.fillMaxWidth().fillMaxHeight(0.92f),
        contentAlignment = Alignment.TopCenter,

        ){
        Column (
            verticalArrangement =Arrangement.SpaceAround,

            //verticalArrangement = Arrangement.SpaceAround,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Напишите пост",
                fontSize = 28.sp,
                textAlign = TextAlign.Left,
                maxLines = 10,
                fontWeight = FontWeight.Bold,
                modifier = Modifier

                    .padding(horizontal = 30.dp, vertical = 20.dp)
                    .fillMaxWidth()
            )
            TextField(
                value = text, onValueChange = { text = it },
                label = { Text(text = "Ваш пост", fontSize = 24.sp) },
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .fillMaxHeight(0.6f),
            )
            Column (
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButtons(isPublic = isPublic)


                Button(onClick = {
                    dsf.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.3f)
                        .fillMaxHeight(0.3f)
                ) {
                    Text(text = "Добавить фото",  fontSize = 18.sp)
                }


                Button(
                    onClick = {
                        if (text != "") {
                            createEntry(u.id, isPublic.value, text, ourList1)
                            dialogOpen = true
                            text = ""
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth(fraction = 0.7f)
                        .fillMaxHeight(0.4f)
                ) {
                    Text(
                        text = "Создать пост",
                        fontSize = 19.sp
                    )
                }
            }
        }
    }
    if (dialogOpen) {
        Dialog(onDismissRequest = {
            dialogOpen = false
        }) {
            Surface(
                modifier = Modifier.fillMaxWidth().wrapContentHeight(),
                shape = RoundedCornerShape(size = 10.dp)
            ) {
                Column(modifier = Modifier.padding(all = 20.dp)) {
                    Text(text = "Пост создан!")
                }

            }
        }
    }
}


fun  convertBack( base64Str : String) : Bitmap
{
    val decodedBytes = Base64.decode(
        base64Str.substring(base64Str.indexOf(",")  + 1),
        Base64.DEFAULT
    );

    return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.size)
}


@Composable
fun RadioButtons(isPublic : MutableState<Boolean>) {
    Box (contentAlignment = Alignment.CenterStart){
        Row (Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically)
        {
            RadioButton(
                selected = !isPublic.value,
                onClick = { isPublic.value = false },
                modifier = Modifier.padding(8.dp)
            )
            Text("Приватный", fontSize = 20.sp)

            Spacer(modifier = Modifier.width(10.dp))
            RadioButton(
                selected = isPublic.value,
                onClick = { isPublic.value = true },
                modifier = Modifier.padding(8.dp)
            )
            Text("Публичный", fontSize = 20.sp)
        }
    }
}


fun createEntry (userId : Int, isPublic: Boolean, text : String, photos : List<String>) : Unit {
    var a = "public";
    if (!isPublic) a = "private";
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
    val currentDate = sdf.format(Date())
    val entryNoId = EntryWithoutId(
        iduser = userId,
        posted = currentDate,
        content = text,
        moderated = false,
        visibility = a,
        topics = emptyList(),
        photo =  photos
    )

    val interceptor = HttpLoggingInterceptor()
    interceptor.level = HttpLoggingInterceptor.Level.BODY

    val client = OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .build()

    val retrofit = Retrofit.Builder()
        //.baseUrl("http://62.3.58.13:8080/api/")
        //.baseUrl("http://localhost:8080/api/")
        //.baseUrl("http://127.0.0.1:8080/api/")
        .baseUrl("http://10.0.2.2:8080/api/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api = retrofit.create(EntryAPI::class.java)
    CoroutineScope(Dispatchers.IO).launch {
        val entry = api.createEntry(entryNoId)
    }
}

fun createDatabase(){



}

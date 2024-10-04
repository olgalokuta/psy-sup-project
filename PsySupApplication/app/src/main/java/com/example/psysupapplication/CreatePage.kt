package com.example.psysupapplication

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TextField
import androidx.compose.runtime.LaunchedEffect

import androidx.compose.ui.Alignment

import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage
import com.example.psysupapplication.api.apiProvider
import com.example.psysupapplication.api.provideApi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date

const val MAX_PHOTOS_COUNT = 5

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePage(u : User, co : Context) {
    var text by remember { mutableStateOf("") }
    var topicIds by remember { mutableStateOf(listOf<Int>()) }
    var finishedDialogOpen by remember { mutableStateOf(false) }
    var noTextDialogOpen by remember { mutableStateOf(false) }
    var isPublic by remember { mutableStateOf(false) }
    var photos by remember { mutableStateOf(listOf<Photo>()) }

    val maxPhotosSize: Int = MAX_PHOTOS_COUNT - photos.size
    val canLoadPhotos: Boolean = maxPhotosSize > 0

    val dsf = rememberLauncherForActivityResult(ActivityResultContracts.PickMultipleVisualMedia(maxOf(maxPhotosSize, 2))) { uris ->
        photos = photos.toMutableList().apply {
            addAll(uris.map(::Photo))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxHeight(0.92f)
            .fillMaxHeight()
    ) {
        Column (
            modifier = Modifier
                .verticalScroll(rememberScrollState()),
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
                value = text,
                onValueChange = { text = it },
                label = { Text(text = "Ваш пост", fontSize = 24.sp) },
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(0.9f),
            )
            Column (
                modifier = Modifier.fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                RadioButtons(isPublic) { isPublic = it }
                // Выбор тем
                RadioTopicsList(topicIds) { enable, topicId ->
                    topicIds = if (enable) {
                        topicIds.toMutableList().apply { add(topicId) }
                    } else {
                        topicIds.toMutableList().apply { remove(topicId) }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    for (i in photos.indices) {
                        Card {
                            AsyncImage(
                                model = photos[i].url,
                                contentDescription = null
                            )
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.End
                            ) {
                                IconButton(onClick = {
                                    photos = photos.toMutableList().apply {
                                        removeAt(i)
                                    }
                                }) {
                                    Icon(Icons.Rounded.Delete, null)
                                }
                            }
                        }
                    }
                }
            }
            Button(
                enabled = canLoadPhotos,
                onClick = {
                    dsf.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
                },
                modifier = Modifier
                    .fillMaxWidth(fraction = 0.5f)
            ) {
                Text(text = "Добавить фото",  fontSize = 18.sp)
            }
            Button(
                onClick = {
                    if (text != "") {
                        createEntry(co.contentResolver, u.id, isPublic, text, photos, topicIds) {
                            finishedDialogOpen = true
                            topicIds = emptyList()
                            photos = listOf()
                            text = ""
                        }
                    } else {
                        noTextDialogOpen = true
                    }
                },
                modifier = Modifier
            ) {
                Text(
                    text = "Создать пост",
                    fontSize = 19.sp
                )
            }
        }
    }
    if (finishedDialogOpen) InfoDialog(text = "Пост создан") { finishedDialogOpen = false }
    if (noTextDialogOpen) InfoDialog(text = "Введите текст") { noTextDialogOpen = false }
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
fun InfoDialog(text: String, onDismiss: () -> Unit) {
    Dialog(onDismissRequest = onDismiss) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(size = 10.dp)
        ) {
            Column(modifier = Modifier.padding(all = 20.dp)) {
                Text(text = text)
            }
        }
    }
}

@Composable
fun RadioTopicsList(topicIds: List<Int>, onChange: (enable: Boolean, topicId: Int) -> Unit) { // Меню выбора тем поста
    val topics = remember { mutableStateOf<List<Topic>?>(null) }
    val apiTopics = apiProvider.provideApi<TopicsAPI>()

    LaunchedEffect(Unit) { // Загружаем темы
        topics.value = apiTopics.getAllTopics()
    }

    LazyRow(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .scrollable(rememberScrollState(), Orientation.Horizontal),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val topicsValue = topics.value;
        if (topicsValue != null) {
            items(topicsValue.size) { id ->
                val enabled = topicIds.contains(id)
                Button(onClick = { onChange(!enabled, id) }) {
                    if (enabled) {
                        Icon(Icons.Default.Delete, contentDescription = null)
                    } else {
                        Icon(Icons.Default.Add, contentDescription = null)
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(text = topicsValue[id].topic)
                }
            }
        }
    }
}

@Composable
fun RadioButtons(isPublic: Boolean, onChange: (Boolean) -> Unit) {
    Box (contentAlignment = Alignment.CenterStart){
        Row (Modifier.selectableGroup(), verticalAlignment = Alignment.CenterVertically)
        {
            RadioButton(
                selected = !isPublic,
                onClick = { onChange(false) },
                modifier = Modifier.padding(8.dp)
            )
            Text("Приватный", fontSize = 20.sp)

            Spacer(modifier = Modifier.width(10.dp))
            RadioButton(
                selected = isPublic,
                onClick = { onChange(true) },
                modifier = Modifier.padding(8.dp)
            )
            Text("Публичный", fontSize = 20.sp)
        }
    }
}

val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")

fun createEntry(contentResolver: ContentResolver, userId: Int, isPublic: Boolean, text: String, photos: List<Photo>, topicIds: List<Int>, onFinish: () -> Unit) : Unit {
    val currentDate = sdf.format(Date())
    val entryNoId = EntryWithoutId(
        iduser = userId,
        posted = currentDate,
        content = text,
        moderated = false,
        visibility = if (isPublic) { "public" } else { "private" },
        topics = topicIds,
        photos =  photos.map { it.loadBase64(contentResolver) },
    )

    val api = apiProvider.provideApi<EntryAPI>()
    CoroutineScope(Dispatchers.IO).launch {
        val entry = api.createEntry(entryNoId)
        onFinish()
    }
}
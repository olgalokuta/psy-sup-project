package com.example.psysupapplication

import android.content.Context
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.psysupapplication.api.apiProvider
import com.example.psysupapplication.api.provideApi

data class EntryAndAuthorLists(
    val entries: List<Entry>,
    val authors: List<User>
)

@Composable
fun EntriesPage(co: Context, entrysAndAuthors : MutableState<EntryAndAuthorLists>, currentUser: User) : Unit {
    val isCommenting = remember { mutableStateOf(false) }
    val currentEntry = remember { mutableStateOf<Entry?>(null) }
    Crossfade(targetState = isCommenting, label = "") { currentSt ->
        when (currentSt.value) {
            true -> currentEntry.value?.let { CommentPage(co, it, currentUser) }
            false -> EntriesInScroll(entrysAndAuthors, isCommenting, currentEntry)
        }
    }
}

@Composable
fun EntriesInScroll(entrysAndAuthors : MutableState<EntryAndAuthorLists>, isCommenting: MutableState<Boolean>,
                    currentEntry: MutableState<Entry?>) {
    val selectedTopicId = remember { mutableStateOf(0) }
    PublicTopicEntries(entrysAndAuthors, selectedTopicId.value)
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
    ){
        LazyColumn {
            item {
                Text(
                    text = "Посты пользователей",
                    fontSize = 28.sp,
                    textAlign = TextAlign.Left,
                    maxLines = 10,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(horizontal = 30.dp, vertical = 20.dp)
                        .fillMaxWidth()
                )
                TopicsList(selectedTopicId.value) { topicId ->
                    selectedTopicId.value = topicId
                }
            }

            items(entrysAndAuthors.value.authors.zip(entrysAndAuthors.value.entries)){ authorAndEntry->
                Box(
                    modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                    contentAlignment = Alignment.Center
                ) {
                    val isEditing = remember { mutableStateOf(false) }
                    EntryView(authorAndEntry.component1(), authorAndEntry.component2(), isEditing, isCommenting, currentEntry, false)
                }
            }
        }
    }
}

@Composable
fun TopicsList(selectedTopicId: Int, onLoad: (id: Int) -> Unit) { // Меню выбора темы
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
                Button(onClick = { onLoad(id) }, enabled = selectedTopicId != id) {
                    Text(text = topicsValue[id].topic)
                }
            }
        }
    }
}

@Composable
fun PublicTopicEntries(entriesAndAuthors : MutableState<EntryAndAuthorLists>, topicId: Int) {
    val apiEntry = apiProvider.provideApi<EntryAPI>()
    val apiUser = apiProvider.provideApi<UserAPI>()

    LaunchedEffect(topicId) { // Перезагружать при изменении темы
        var listEntries = apiEntry.getPublicEntriesByTopic(topicId).reversed()
        var listUsers = mutableListOf<User>()
        for (entry in listEntries) {
            listUsers.add(apiUser.getUserById(entry.iduser))
        }
        entriesAndAuthors.value = EntryAndAuthorLists(listEntries, listUsers)
    }

    /*CoroutineScope(Dispatchers.IO).launch {
        var listEntries = apiEntry.getPublicEntries().reversed()
        var listUsers = mutableListOf<User>()
        for (entry in listEntries) {
            listUsers.add(apiUser.getUserById(entry.iduser))
        }
        entriesAndAuthors.value = EntryAndAuthorLists(listEntries, listUsers)
    }*/
}
package com.example.psysupapplication

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.psysupapplication.api.apiProvider
import com.example.psysupapplication.api.provideApi
import com.example.psysupapplication.ui.theme.Purple80
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date

data class CommentAndAuthorLists(
    val comments: List<Comment>,
    val authors: List<User>
)

@Composable
fun CommentPage (entry : Entry, currentUser: User): Unit {
    val CommentsPlusAuthors = remember { mutableStateOf<CommentAndAuthorLists?>(null) }
    val user = remember { mutableStateOf<User?>(null) }
    getUserById(entry.iduser, user)
    getEntryComments(entry.id, CommentsPlusAuthors)
    CommentsPageIter(entry, currentUser, CommentsPlusAuthors, user)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsPageIter(entry: Entry, currentUser: User, CommentsPlusAuthors : MutableState<CommentAndAuthorLists?>, user: MutableState<User?>) : Unit {
    var commentText by rememberSaveable { mutableStateOf("") }
    var dialogOpen by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(0.92f)
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {
            LazyColumn (modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.9f)) {
                item {
                    Box(
                        modifier = Modifier.padding(vertical = 30.dp, horizontal = 20.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column {
                            Text(
                                text = "Пост",
                                fontSize = 28.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                            user.value?.let {StaticEntry(it, entry)}
                        }
                    }
                }
                item {
                    Box (contentAlignment = Alignment.CenterStart) {
                        Column {
                            Text(
                                text = "Комментарии",
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(horizontal = 20.dp)
                            )
                        }
                    }
                }
                CommentsPlusAuthors.value?.let{
                    items(it.authors.zip(it.comments)){commentPlusAuthor->
                        Box(
                            modifier = Modifier.padding(horizontal = 15.dp, vertical = 10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            CommentView(commentPlusAuthor.component1(), commentPlusAuthor.component2())
                        }
                    }
                }
            }

            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ){
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    TextField(
                        value = commentText,
                        onValueChange = { commentText = it },
                        placeholder = { Text(text = "Введите комментарий") }
                    )
                    IconButton(onClick = {
                        postComment(commentText, currentUser.id, entry.id)
                        commentText = ""
                        dialogOpen = true
                    }) {
                        Icon(MyIcons.send, contentDescription = "Новый комментарий")
                    }
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
                    Text(text = "Ваш комментарий отправлен на модерацию")
                }
            }
        }
    }
}

@Composable
fun CommentView(user : User, comment : Comment) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = Purple80
        )
    ) {
        Column(modifier = Modifier.padding(15.dp)) {
            Row {
                Image(
                    painter = painterResource(id = R.drawable.default_avatar),
                    contentDescription = "Default avatar",
                    modifier = Modifier
                        .size(42.dp)
                        .clip(CircleShape)
                )

                Spacer(modifier = Modifier.width(15.dp))

                Column(
                    modifier = Modifier
                        .fillMaxHeight(fraction = 0.8f),
                    verticalArrangement = Arrangement.SpaceAround
                ) {
                    Text(
                        text = user.username,
                        color = Color.Black,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = comment.posted,
                        color = Color.Black.copy(alpha = 0.7f),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = comment.content,
                color = Color.Black.copy(alpha = 0.7f),
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium
            )
        }
    }
}

@Composable
fun getUserById (userID : Int, u: MutableState<User?>) {
    val api = apiProvider.provideApi<UserAPI>()
    LaunchedEffect(Unit) {
        u.value = api.getUserById(userID)
    }
//    CoroutineScope(Dispatchers.IO).launch {
//        entryComments.value = api.getEntryComments(entryId).reversed()
//    }
}

@Composable
fun getEntryComments (entryId : Int, commentsAndAuthors : MutableState<CommentAndAuthorLists?>) {
    val apiComment = apiProvider.provideApi<CommentAPI>()
    val apiUser = apiProvider.provideApi<UserAPI>()

    LaunchedEffect(Unit) {
        var listComments = apiComment.getEntryComments(entryId).reversed()
        var listUsers = mutableListOf<User>()
        for (comment in listComments) {
            listUsers.add(apiUser.getUserById(comment.iduser))
        }
        commentsAndAuthors.value = CommentAndAuthorLists(listComments, listUsers)
    }
}

fun postComment (content: String, idUser: Int, entryId: Int) {
    val sdf = SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss")
    val currentDate = sdf.format(Date())
    val comment = CommentWithoutId(idUser, currentDate, content, false, entryId, 0)

    val api = apiProvider.provideApi<CommentAPI>()
    CoroutineScope(Dispatchers.IO).launch {
        val c = api.createComment(comment)
    }
}
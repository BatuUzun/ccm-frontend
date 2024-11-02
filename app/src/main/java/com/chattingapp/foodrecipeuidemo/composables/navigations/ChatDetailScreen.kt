package com.chattingapp.foodrecipeuidemo.composables.navigations

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chattingapp.foodrecipeuidemo.R
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.viewmodel.MessageViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(navController: NavHostController, chatId: String?, username: String?, profilePhoto: String?) {
    val messageViewModel: MessageViewModel = viewModel()
    var messageText by remember { mutableStateOf("") } // State for message input
    val chatIdLong = chatId?.toLongOrNull()
    val lazyListState = rememberLazyListState() // Create LazyListState

    LaunchedEffect(chatIdLong) {
        chatIdLong?.let {
            messageViewModel.fetchChatLog(it)
        }
    }

    val chatLogState by messageViewModel.chatLog.collectAsState()

    // Automatically scroll to the last item when the chat log updates
    LaunchedEffect(chatLogState) {
        if (chatLogState.isNotEmpty()) {
            lazyListState.animateScrollToItem(chatLogState.size - 1)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages") },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = ImageVector.vectorResource(id = R.drawable.ic_back),
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // User Info Row
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(10.dp)
            ) {
                AsyncImage(
                    model = "${Constant.AWS_S3_LINK}${profilePhoto}",
                    contentDescription = null,
                    modifier = Modifier
                        .height(100.dp)
                        .padding(end = 8.dp)
                        .clip(RoundedCornerShape(8.dp))
                )

                Text(
                    text = username ?: "",
                    fontSize = 20.sp,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
            }

            // Messages List
            LazyColumn(
                state = lazyListState, // Set LazyListState to LazyColumn
                contentPadding = PaddingValues(vertical = 8.dp),
                modifier = Modifier.weight(1f) // Allow it to take available space
            ) {
                items(chatLogState) { message ->
                    MessageBubble(
                        message = message.message,
                        isSender = message.senderId == Constant.userProfile.id
                    )
                }
            }

            // Add a TextField and Send button at the bottom
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp)
                    .imePadding(), // This will add padding to prevent overlap with keyboard
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = messageText,
                    onValueChange = { messageText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Type a message...") }
                )
                IconButton(onClick = {
                    if (messageText.isNotBlank()) {
                        messageViewModel.sendMessage(chatIdLong ?: 0, messageText, Constant.userProfile.id)
                        messageText = "" // Clear input after sending
                    }
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_filter),
                        contentDescription = "Send Message"
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(message: String, isSender: Boolean) {
    val backgroundColor = if (isSender) Color(0xFFE1FFC7) else Color(0xFFE0E0E0)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp)
            .padding(start = 10.dp, end = 10.dp)
            .wrapContentWidth(if (isSender) Alignment.End else Alignment.Start)
    ) {
        Text(
            text = message,
            modifier = Modifier
                .background(
                    color = backgroundColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(12.dp),
            fontSize = 16.sp
        )
    }
}

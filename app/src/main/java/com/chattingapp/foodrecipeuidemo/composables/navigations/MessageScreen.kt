package com.chattingapp.foodrecipeuidemo.composables.navigations

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.chattingapp.foodrecipeuidemo.R
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.viewmodel.ChatViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageScreen(navController: NavHostController) {
    // Main Box to position items
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
                    containerColor = Color.Transparent // Make the background transparent
                )
            )
        }
    ) { innerPadding ->

        // Using innerPadding for content
        Box(modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding) // Apply inner padding here
        ) {
            // Your main content goes here
            val chatViewModel: ChatViewModel = viewModel()
            val createdChat = chatViewModel.createdChatLiveData.observeAsState()
            val errorMessage = chatViewModel.errorLiveData.observeAsState()

            var displayMessages by remember { mutableStateOf(false) }

            // Create chat as soon as the screen is displayed
            LaunchedEffect(Unit) {
                chatViewModel.createChat(user1Id = Constant.userProfile.id, user2Id = Constant.createChatUser2Id) // Pass actual IDs
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(16.dp) // Adding some padding to the column
            ) {
                // Check if chat is created successfully
                createdChat.value?.let { chat ->
                    //Text("Chat created with ID: ${chat.id}") // Adjust based on your Chat data class
                    displayMessages = true
                }

                // Check if there was an error
                errorMessage.value?.let { error ->
                    //Text("Error: $error")
                    displayMessages = true

                }


                if(displayMessages){
                    val chats = chatViewModel.chats.collectAsState()
                    var displayChats by remember { mutableStateOf(false) }

                    LaunchedEffect(Unit) {
                        chatViewModel.getChatsByUserId(userId = Constant.userProfile.id)
                        displayChats = true
                    }

                    if(displayChats){
                        chats.value.forEach { chat ->
                            ChatItem(chat, navController)
                        }
                    }

                }
            }
        }
    }
}


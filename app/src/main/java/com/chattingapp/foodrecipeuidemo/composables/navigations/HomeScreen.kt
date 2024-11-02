package com.chattingapp.foodrecipeuidemo.composables.navigations

import androidx.compose.runtime.*
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

@Composable
fun HomeScreen() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = "feedItem") {
        composable("feedItem") {
            FeedItemScreen(navController) // Your screen with FeedItem
        }
        composable("messageScreen") {
            MessageScreen(navController) // The screen you navigate to
        }
        composable("chatDetail/{chatId}/{username}/{profilePhoto}") { backStackEntry ->
            val chatId = backStackEntry.arguments?.getString("chatId")
            val username = backStackEntry.arguments?.getString("username")
            val profilePhoto = backStackEntry.arguments?.getString("profilePhoto")

            // Load and display chat details using the chatId
            ChatDetailScreen(navController, chatId = chatId, username, profilePhoto)
        }
    }

}

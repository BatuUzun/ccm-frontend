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
    }

}

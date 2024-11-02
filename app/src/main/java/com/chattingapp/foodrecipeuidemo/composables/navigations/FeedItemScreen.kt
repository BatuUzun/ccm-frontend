package com.chattingapp.foodrecipeuidemo.composables.navigations

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.chattingapp.foodrecipeuidemo.composables.item.FeedItem
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.viewmodel.FeedViewModel

@Composable
fun FeedItemScreen(navController: NavHostController) {
    val viewModel: FeedViewModel = viewModel()

    // Collect the state from StateFlow
    val userSkillMatches by viewModel.userSkillMatches.collectAsState()

    // Using rememberSaveable to keep the fetch status across configuration changes
    var isFirstTimeFetch by rememberSaveable { mutableStateOf(true) }

    // Fetch mutual skills only if it has not been fetched before
    LaunchedEffect(isFirstTimeFetch) {
        if (isFirstTimeFetch) {
            Log.d("HomeScreen", "Fetching mutual skills for the first time")
            viewModel.fetchMutualSkills(Constant.userProfile.id)  // Replace with your user ID or pass dynamically

            // Set the flag to false after fetching
            isFirstTimeFetch = false
        }
    }

    // Display the list of matches using LazyColumn
    if (userSkillMatches.isNotEmpty()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            contentPadding = PaddingValues(vertical = 8.dp)
        ) {
            items(userSkillMatches) { currentMatch ->
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    FeedItem(currentMatch, navController)



                    Spacer(modifier = Modifier.height(40.dp)) // Add space between items
                }
            }
        }
    } else {
        // Display nothing when there are no matches available
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            // Optionally put a placeholder here if desired
            Text("No more matches available")
        }
    }
}
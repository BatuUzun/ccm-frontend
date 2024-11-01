package com.chattingapp.foodrecipeuidemo.composables.tabs

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.viewmodel.UserSkillsViewModel
import com.chattingapp.foodrecipeuidemo.viewmodel.WantedSkillsViewModel

@Composable
fun TabContentNeededSkills() {
    val viewModel: WantedSkillsViewModel = viewModel()
    val userSkills = viewModel.wantedSkills.collectAsState().value
    var lastClickTime by remember { mutableStateOf(0L) } // Track the last click time
    val doubleClickThreshold = 300L // Threshold for double click in milliseconds

    // Fetch user skills when the screen is launched
    LaunchedEffect(Constant.userProfile.id) {
        viewModel.getWantedSkills(Constant.userProfile.id)
    }

    // Display skills in a LazyColumn
    LazyColumn(modifier = Modifier.padding(top = 20.dp)) {
        userSkills?.let {
            items(it) { skill ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth() // Make the Box take up all available width
                        .size(200.dp, 100.dp) // Set a specific width and height for the Box
                        .padding(16.dp) // Optional: add padding around the text
                        .border(BorderStroke(2.dp, Color.Cyan), RoundedCornerShape(16.dp)) // Add a border with rounded corners
                        .background(Color.Black, RoundedCornerShape(16.dp)) // Set a custom background color with rounded corners
                        .clickable {
                            // Handle double click
                            val currentTime = System.currentTimeMillis()
                            if (currentTime - lastClickTime < doubleClickThreshold) {
                                viewModel.deleteSkill(skill.id) // Call delete function on double click
                            }
                            lastClickTime = currentTime // Update last click time
                        },
                    contentAlignment = Alignment.Center // Center the text within the Box
                ) {
                    Text(
                        text = skill.skillName,
                        style = androidx.compose.material3.MaterialTheme.typography.bodyLarge, // You can customize the text style as needed
                        color = Color.White // Set text color directly
                    )
                }
            }
        } ?: run {
            // Optionally, display a loading or empty state
            item {
                CircularProgressIndicator()

            }
        }
    }

    val textState = remember { mutableStateOf("") }
    if(Constant.isAddPage){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            // TextField for user input
            TextField(
                value = textState.value,
                onValueChange = { textState.value = it },
                label = { Text("Add skill") },
                modifier = Modifier.fillMaxWidth()
            )

            // Button to submit the input
            Button(
                onClick = {
                    // Check if the input is not blank before proceeding
                    if (textState.value.isNotBlank()) {
                        // Call the addSkill function from the ViewModel
                        viewModel.addSkill(textState.value, Constant.userProfile.id)
                        // Clear the text field after submission
                        textState.value = ""
                    }
                },
                modifier = Modifier.padding(top = 16.dp) // Optional padding
            ) {
                Text("Submit", fontSize = 16.sp) // Customize button text size
            }
        }
    }

}

package com.chattingapp.foodrecipeuidemo.composables.navigations

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.chattingapp.foodrecipeuidemo.composables.tabs.TabContentNeededSkills
import com.chattingapp.foodrecipeuidemo.composables.tabs.TabContentYourSkills

@Composable
fun AddScreen(navController: NavController) {
    val tabTitles = listOf("Your Skills", "Needed Skills")
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.padding(top = 20.dp)) {
        TabRow(
            selectedTabIndex = selectedTabIndex) {
            tabTitles.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    modifier = Modifier.padding(8.dp),
                    text = {
                        Text(
                            text = title,
                            color = if (selectedTabIndex == index) {
                                Color.Black // Color for selected tab (black)
                            } else {
                                Color.Gray // Color for unselected tab (gray)
                            },
                            fontWeight = if (selectedTabIndex == index) {
                                FontWeight.Bold // Bold text for the selected tab
                            } else {
                                FontWeight.Normal // Normal text for unselected tab
                            }
                        )
                    }
                )
            }
        }

        // Content based on selected tab
        when (selectedTabIndex) {
            0 -> TabContentYourSkills() // Content for Your Skills
            1 -> TabContentNeededSkills() // Content for Needed Skills
        }
    }
}
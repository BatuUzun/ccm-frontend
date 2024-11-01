package com.chattingapp.foodrecipeuidemo.composables.navigations

import android.os.SystemClock
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.chattingapp.foodrecipeuidemo.R

@Composable
fun AppNavigationBar(navController: NavController) {
    var lastClickTimeFeed by remember { mutableStateOf(0L) }
    var lastClickTimeProfile by remember { mutableStateOf(0L) }
    val clickInterval = 10000L // 10 seconds
    val ICON_SIZE = 28.dp
    val ICON_SIZE_FEED = 32.dp
    var currentRoot by remember { mutableStateOf("home") }

    BottomNavigation(
        backgroundColor = Color.Transparent,
        contentColor = Color.Black,
        elevation = 0.dp,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Column {
            Divider (
                color = Color.Black,
                modifier = Modifier
                    .height(1.dp)
                    .fillMaxWidth()
            )
            Row {
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = if (currentRoot == "home") R.drawable.homeselected else R.drawable.homenotselected),
                            contentDescription = "Home",
                            modifier = Modifier.size(ICON_SIZE)
                        )
                    },
                    selected = currentRoot == "home",
                    onClick = {
                        currentRoot = "home"

                        lastClickTimeFeed = 0
                        lastClickTimeProfile = 0
                        navController.navigate("home")
                    }
                )

                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = if (currentRoot == "profile") R.drawable.profileselected else R.drawable.profilenotselected),
                            contentDescription = "profile",
                            modifier = Modifier.size(ICON_SIZE)
                        )
                    },
                    selected = currentRoot == "profile",
                    onClick = {
                        currentRoot = "profile"

                        lastClickTimeProfile = 0
                        lastClickTimeFeed = 0
                        navController.navigate("profile")
                    }
                )
                BottomNavigationItem(
                    icon = {
                        Icon(
                            painterResource(id = if (currentRoot == "add") R.drawable.createrecipeselected else R.drawable.createrecipenotselected),
                            contentDescription = "add",
                            modifier = Modifier.size(ICON_SIZE)
                        )
                    },
                    selected = currentRoot == "add",
                    onClick = {
                        currentRoot = "add"

                        lastClickTimeProfile = 0
                        lastClickTimeFeed = 0
                        navController.navigate("add")
                    }
                )

            }
        }


    }


}
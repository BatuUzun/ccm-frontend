package com.chattingapp.foodrecipeuidemo.composables.navigations

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.entity.Chat

import androidx.compose.foundation.clickable
import androidx.navigation.NavHostController

@Composable
fun ChatItem(chat: Chat, navController: NavHostController) {
    // Add a clickable modifier to navigate when the Row is clicked
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clickable {
                // Navigate to a different screen when clicked
                navController.navigate("chatDetail/${chat.id}/${chat.user2Username}/${chat.user2ProfilePicture}") // Replace "chatDetail" with your actual route
            }
    ) {
        AsyncImage(
            model = "${Constant.AWS_S3_LINK}${chat.user2ProfilePicture}",
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .padding(end = 8.dp) // Padding to the right of the image
                .clip(RoundedCornerShape(8.dp))
        )

        Text(
            text = chat.user2Username,
            fontSize = 20.sp, // Set the desired font size
            modifier = Modifier.align(Alignment.CenterVertically)
        )
    }

    Spacer(modifier = Modifier.height(40.dp)) // Add space between items
}

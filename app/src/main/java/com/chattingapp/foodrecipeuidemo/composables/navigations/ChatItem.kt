package com.chattingapp.foodrecipeuidemo.composables.navigations

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.entity.Chat
import com.chattingapp.foodrecipeuidemo.viewmodel.UserProfileViewModel

@Composable
fun ChatItem(chat: Chat) {

    // Fetch the profile picture when the screen is displayed


    // Display the profile picture or a placeholder if null
        AsyncImage(
            model = "${Constant.AWS_S3_LINK}${chat.user2ProfilePicture}",
            contentDescription = null,
            modifier = Modifier
                .height(100.dp)
                .padding(end = 8.dp) // Padding to the right of the image
                .clip(RoundedCornerShape(8.dp))
        )

    Spacer(modifier = Modifier.height(40.dp)) // Add space between items

}

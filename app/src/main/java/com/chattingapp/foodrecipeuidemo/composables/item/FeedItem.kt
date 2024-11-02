package com.chattingapp.foodrecipeuidemo.composables.item

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.entity.UserSkillMatchDTO

@Composable
fun FeedItem(currentMatch: UserSkillMatchDTO, navController: NavHostController) {
    // Card container for a more styled appearance
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp) // Spacing around the card
            .clip(RoundedCornerShape(16.dp)), // Rounded corners for a modern look
        elevation = CardDefaults.cardElevation(8.dp) // Shadow effect
    ) {
        Column(
            modifier = Modifier
                .background(Color(0xFFF5F5F5)) // Light background color
                .padding(16.dp) // Inner padding
        ) {
            // Row displaying user 1's information
            UserRow(
                imageModel = "${Constant.AWS_S3_LINK}${Constant.userProfile.profilePicture}",
                username = "You",
                skill = currentMatch.user1Skill,
                wantedSkill = currentMatch.user1WantedSkill
            )

            Spacer(modifier = Modifier.height(16.dp)) // Space between rows

            // Row displaying user 2's information
            UserRow(
                imageModel = "${Constant.AWS_S3_LINK}${currentMatch.user2ProfilePicture}",
                username = currentMatch.user2Username,
                skill = currentMatch.user2Skill,
                wantedSkill = currentMatch.user2WantedSkill
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Row for action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        Log.d("ChatNavigation", "From ${currentMatch.user1Username} to ${currentMatch.user2Username}")
                        Constant.createChatUser2Id = currentMatch.user2Id
                        navController.navigate("messageScreen")
                    },
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp)) // Rounded corners for button
                        .padding(8.dp) // Padding around the button
                ) {
                    Text("Send Message", fontSize = 16.sp)
                }
            }
        }
    }
}

@Composable
fun UserRow(
    imageModel: String,
    username: String,
    skill: String,
    wantedSkill: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp) // Padding for top and bottom of each row
    ) {
        // Profile picture with rounded corners and border
        AsyncImage(
            model = imageModel,
            contentDescription = "Profile Picture",
            modifier = Modifier
                .size(80.dp) // Set image size
                .clip(RoundedCornerShape(12.dp)) // Rounded corners for the image
                .border(2.dp, Color.Gray, RoundedCornerShape(12.dp)) // Border for emphasis
        )

        Spacer(modifier = Modifier.width(12.dp)) // Space between image and text

        // Column for user details
        Column(
            verticalArrangement = Arrangement.spacedBy(4.dp), // Spacing between text
            modifier = Modifier.weight(1f) // Take up remaining space
        ) {
            // Username and skill text with bold styling
            Text(
                text = buildAnnotatedString {
                    append("$username knows ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF1A73E8))) {
                        append(skill)
                    }
                },
                fontSize = 18.sp,
                color = Color.Black
            )
            Text(
                text = buildAnnotatedString {
                    append("$username wants to learn ")
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFFE53935))) {
                        append(wantedSkill)
                    }
                },
                fontSize = 18.sp,
                color = Color.DarkGray
            )
        }
    }
}


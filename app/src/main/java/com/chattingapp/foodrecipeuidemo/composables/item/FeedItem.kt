package com.chattingapp.foodrecipeuidemo.composables.item

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxSize()
    ) {
        // Row to hold the image and the first two texts
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = "${Constant.AWS_S3_LINK}${Constant.userProfile.profilePicture}",
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .padding(end = 8.dp) // Padding to the right of the image
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f) // Take up the remaining space
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("You know ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(currentMatch.user1Skill)
                        }
                    },
                    fontSize = 20.sp // Increased font size
                )
                Text(
                    text = buildAnnotatedString {
                        append("You want to learn ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(currentMatch.user1WantedSkill)
                        }
                    },
                    fontSize = 20.sp // Increased font size
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AsyncImage(
                model = "${Constant.AWS_S3_LINK}${currentMatch.user2ProfilePicture}",
                contentDescription = null,
                modifier = Modifier
                    .height(100.dp)
                    .padding(end = 8.dp) // Padding to the right of the image
                    .clip(RoundedCornerShape(8.dp))
            )

            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier.weight(1f) // Take up the remaining space
            ) {
                // Other details in the next Text views
                Text(
                    text = buildAnnotatedString {
                        append("${currentMatch.user2Username} knows ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(currentMatch.user2Skill)
                        }
                    },
                    fontSize = 20.sp // Increased font size
                )
                Text(
                    text = buildAnnotatedString {
                        append("${currentMatch.user2Username} wants to learn ")
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                            append(currentMatch.user2WantedSkill)
                        }
                    },
                    fontSize = 20.sp // Increased font size
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Button(onClick = {
                Log.d("asd", currentMatch.user1Username + currentMatch.user2Username)
                Constant.createChatUser2Id = currentMatch.user2Id
                navController.navigate("messageScreen")

            }) {
                Text("Send message")
            }

        }
    }
}

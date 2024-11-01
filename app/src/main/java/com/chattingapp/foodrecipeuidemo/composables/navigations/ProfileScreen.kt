package com.chattingapp.foodrecipeuidemo.composables.navigations

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.chattingapp.foodrecipeuidemo.MainActivity
import com.chattingapp.foodrecipeuidemo.R
import com.chattingapp.foodrecipeuidemo.composables.tabs.TabContentNeededSkills
import com.chattingapp.foodrecipeuidemo.composables.tabs.TabContentYourSkills
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.viewmodel.ProfileImageViewModel
import com.chattingapp.foodrecipeuidemo.viewmodel.TokenViewModel
import com.chattingapp.foodrecipeuidemo.viewmodel.UserProfileViewModel
import com.chattingapp.foodrecipeuidemo.viewmodel.UserSkillsViewModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.io.FileOutputStream

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavController) {

    val profileImageViewModel: ProfileImageViewModel = viewModel()
    val userProfileViewModel: UserProfileViewModel = viewModel()

    val userProfile = remember {
        Constant.userProfile
    }

    var displayProfileImage by remember { mutableStateOf(false) }

    Column {

        Row(
            modifier = Modifier
                .fillMaxWidth() // Fill the entire width of the screen
                .padding(top = 16.dp)
        ) {
            Text(
                userProfile.username,
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .padding(start = 16.dp, top = 16.dp)
            )
            if(userProfile.id == Constant.userProfile.id){
                val context = LocalContext.current
                val tokenViewModel: TokenViewModel = viewModel()
                Spacer(modifier = Modifier.weight(1f))
                androidx.compose.material.Button(
                    onClick = {
                        val token = retrieveToken(context)
                        if (token != null) {
                            deleteToken(context)
                            tokenViewModel.deleteToken(Constant.user.id, token)
                        }
                        navigateToMainActivity(context)


                    },modifier = Modifier
                        .padding(start = 16.dp, top = 16.dp, end = 16.dp) // Add padding around the button
                ) {
                    Text(text = "Logout", color = Color.White)
                }
            }

        }

        Row(verticalAlignment = Alignment.CenterVertically) {
            painterResource(id = R.drawable.ic_launcher_background)
            if (userProfile.bm == null) {
                LaunchedEffect(userProfile.profilePicture) {
                    profileImageViewModel.fetchProfileImage(userProfile.profilePicture)
                }

                val profileImage by profileImageViewModel.profileImage.observeAsState()
                profileImage?.let {
                    displayProfileImage = true
                } ?: run {
                    //ProfilePhotoPlaceholder()
                }
            } else {
                displayProfileImage = true
            }
            if (displayProfileImage) {
                userProfile.bm?.let {
                    if(userProfile.id == Constant.userProfile.id){

                        var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
                        val context = LocalContext.current
                        val launcher = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
                            selectedImageUri = uri
                            uri?.let {
                                val contentResolver = context.contentResolver
                                val inputStream = contentResolver.openInputStream(uri)

                                val originalBitmap = BitmapFactory.decodeStream(inputStream)
                                val resizedBitmap = Bitmap.createScaledBitmap(originalBitmap, 100, 100, true)

                                val file = File.createTempFile("profile_picture", ".jpg")
                                val outputStream = FileOutputStream(file)
                                resizedBitmap.compress(Bitmap.CompressFormat.JPEG, 80, outputStream)
                                outputStream.flush()
                                outputStream.close()

                                val requestFile = RequestBody.create(MultipartBody.FORM, file)
                                val body = MultipartBody.Part.createFormData("file", file.name, requestFile)

                                userProfileViewModel.changeProfilePicture(body, userProfile.id)
                            }
                        }


                        // Display the image
                        val bitmap = selectedImageUri?.let { uri ->
                            loadBitmapFromUri(uri, context.contentResolver)
                        }

                        val displayBitmap = bitmap ?: userProfile.bm

                        if (displayBitmap != null) {
                            Constant.userProfile.bm = displayBitmap
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth() // Makes the Box take up available space
                                    .padding(top = 30.dp, end = 20.dp), // Add outer padding if needed
                                contentAlignment = Alignment.Center // Center the content inside the Box
                            ) {
                                Image(
                                    bitmap = displayBitmap.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .size(100.dp)
                                        .clickable {
                                            launcher.launch("image/*")
                                        }
                                )
                            }
                        }

                    }
                    else{
                        Image(
                            bitmap = it.asImageBitmap(),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }

                }


            }
        }


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


    }

private fun navigateToMainActivity(context: Context) {
    val intent = Intent(context, MainActivity::class.java)
    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    context.startActivity(intent)
}
fun retrieveToken(context: Context): String? {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    return sharedPreferences.getString("auth_token", null)
}
fun deleteToken(context: Context) {
    val sharedPreferences: SharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    val editor = sharedPreferences.edit()
    editor.remove("auth_token") // Remove the token
    editor.apply() // Commit changes
}

fun loadBitmapFromUri(uri: Uri, contentResolver: ContentResolver): Bitmap? {
    return try {
        contentResolver.openInputStream(uri)?.use { inputStream ->
            BitmapFactory.decodeStream(inputStream)
        }
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}
package com.chattingapp.foodrecipeuidemo.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chattingapp.foodrecipeuidemo.activity.ui.theme.MyAppTheme
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.viewmodel.UserProfileViewModel

class HomePageActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    val userProfileViewModel:UserProfileViewModel = viewModel()
                    var isFirstTime by remember { mutableStateOf(true) }
                    val userProfile by userProfileViewModel.userProfile.collectAsState()
                    val isLoading by userProfileViewModel.isLoading.collectAsState()

                    if(isFirstTime) {
                        LaunchedEffect(Unit) {
                            userProfileViewModel.fetchUserProfile()
                            isFirstTime = false
                        }
                    }



                    if(!isFirstTime && !isLoading && userProfile != null) {
                        Text(text = Constant.userProfile.username+ Constant.userProfile.id)
                    }
                    else{
                        CircularProgressIndicator()
                    }

                }
            }




        }
    }
}

package com.chattingapp.foodrecipeuidemo.activity

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.Coil
import com.chattingapp.foodrecipeuidemo.activity.ui.theme.MyAppTheme
import com.chattingapp.foodrecipeuidemo.coil.CoilSetup
import com.chattingapp.foodrecipeuidemo.composables.navigations.AddScreen
import com.chattingapp.foodrecipeuidemo.composables.navigations.AppNavigationBar
import com.chattingapp.foodrecipeuidemo.composables.navigations.HomeScreen
import com.chattingapp.foodrecipeuidemo.composables.navigations.ProfileScreen

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
                    val imageLoaderProvider = CoilSetup(this)
                    Coil.setImageLoader(imageLoaderProvider.imageLoader)

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


                    val navController = rememberNavController()

                    if(!isFirstTime && !isLoading && userProfile != null) {
                        Scaffold(
                            bottomBar = { AppNavigationBar(navController = navController) }
                        ) { innerPadding ->
                            NavHost(
                                navController = navController,
                                startDestination = "home",
                                modifier = Modifier.padding(innerPadding)
                            ) {
                                composable("home") {
                                    Constant.isAddPage = false

                                    HomeScreen()
                                }
                                composable("profile") {
                                    Constant.isAddPage = false

                                    ProfileScreen(navController)
                                }
                                composable("add") {
                                    Constant.isAddPage = true
                                    AddScreen(navController)
                                }



                            }


                        }
                    }
                    else{
                    }

                }
            }




        }
    }
}
package com.chattingapp.foodrecipeuidemo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.foodrecipeuidemo.constant.Constant
import com.chattingapp.foodrecipeuidemo.entity.UserProfile
import com.chattingapp.foodrecipeuidemo.entity.UserProfileDTO
import com.chattingapp.foodrecipeuidemo.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class UserProfileViewModel : ViewModel() {

    private val apiService = RetrofitHelper.apiService


    private val _userProfile = MutableStateFlow<UserProfile?>(null)
    val userProfile: StateFlow<UserProfile?> get() = _userProfile

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> get() = _isLoading

    fun fetchUserProfile() {
        viewModelScope.launch(Dispatchers.IO) {
            _isLoading.value = true
            try {
                val profile = apiService.getUserProfileByEmail(Constant.user.email)
                withContext(Dispatchers.Main) {
                    _userProfile.value = profile
                    Constant.userProfile = profile
                    // Assuming ProfileImageViewModel is also managed by a dependency injection or similar

                }
            } catch (e: Exception) {
                Log.e("API_CALL_FAILURE", "Failed to fetch user profile", e)
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun saveUser(userProfileDTO: UserProfileDTO, onSuccess: (String) -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            try {
                // Making the network call on IO dispatcher
                val response = withContext(Dispatchers.IO) {
                    RetrofitHelper.apiService.saveUser(userProfileDTO)
                }

                // Handling the response after it's received
                onSuccess(response)
            } catch (e: Exception) {
                // If an error occurs, invoke the onError callback
                onError("Failed to create user: ${e.message ?: "Unknown error"}")
            }
        }
    }



}

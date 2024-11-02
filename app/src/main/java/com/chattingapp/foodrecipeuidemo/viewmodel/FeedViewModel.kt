package com.chattingapp.foodrecipeuidemo.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import com.chattingapp.foodrecipeuidemo.entity.UserSkillMatchDTO
import com.chattingapp.foodrecipeuidemo.retrofit.RetrofitHelper

class FeedViewModel : ViewModel() {
    private val _userSkillMatches = MutableStateFlow<List<UserSkillMatchDTO>>(emptyList())
    val userSkillMatches: StateFlow<List<UserSkillMatchDTO>> = _userSkillMatches

    // Flag to track if mutual skills have already been fetched
    private var hasFetchedSkills = false

    // Log tag
    private val TAG = "FeedViewModel"

    fun fetchMutualSkills(userId: Long) {
        // Fetch mutual skills only once
        if (!hasFetchedSkills) {
            Log.d(TAG, "Fetching mutual skills for user ID: $userId")
            viewModelScope.launch {
                try {
                    val response = RetrofitHelper.apiService.getMutualSkills(userId)
                    _userSkillMatches.value = response
                    hasFetchedSkills = true // Set the flag to true after fetching
                    Log.d(TAG, "Successfully fetched mutual skills: ${response.size} skills found.")
                } catch (e: Exception) {
                    // Handle error, e.g., show a toast or log it
                    Log.e(TAG, "Error fetching mutual skills: ${e.message}")
                    e.printStackTrace()
                }
            }
        } else {
            Log.d(TAG, "Mutual skills have already been fetched.")
        }
    }

}

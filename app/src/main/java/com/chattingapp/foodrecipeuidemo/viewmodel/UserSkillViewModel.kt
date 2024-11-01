package com.chattingapp.foodrecipeuidemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.foodrecipeuidemo.entity.UserSkill
import com.chattingapp.foodrecipeuidemo.retrofit.RetrofitHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UserSkillsViewModel : ViewModel() {

    private val _userSkills = MutableStateFlow<List<UserSkill>?>(null)
    val userSkills: StateFlow<List<UserSkill>?> get() = _userSkills

    fun getUserSkills(userId: Long) {
        viewModelScope.launch {
            try {
                val skills = RetrofitHelper.apiService.getUserSkillsByUserId(userId)
                _userSkills.value = skills
            } catch (e: Exception) {
                // Handle the error (log it or show a message)
                _userSkills.value = null
            }
        }
    }

    fun deleteSkill(id: Long) {
        viewModelScope.launch {
            try {
                RetrofitHelper.apiService.deleteUserSkill(id)
                // Update the _userSkills state to reflect the deletion
                _userSkills.value = _userSkills.value?.filter { it.id != id }
            } catch (e: Exception) {
                // Handle any errors here (e.g., show a Toast or update the UI state)
            }
        }
    }

    /*fun addSkill(skillName: String, userId: Long) {
        viewModelScope.launch {
            try {
                val newSkill = UserSkill(0, skillName, userId)
                val addedSkill = RetrofitHelper.apiService.addUserSkill(newSkill)
                // Update the state flow with the new skill
                _userSkills.value = _userSkills.value?.plus(addedSkill) ?: listOf(addedSkill)
            } catch (e: Exception) {
                // Handle any errors here (e.g., show a Toast or update the UI state)
            }
        }
    }*/

    fun addSkill(skillName: String, userId: Long) {
        // Check if the skill already exists
        val existingSkills = _userSkills.value
        if (existingSkills?.any { it.skillName.equals(skillName, ignoreCase = true) } == true) {
            // Skill already exists, return or show a message to the user
            return
        }

        // Proceed to add the skill
        viewModelScope.launch {
            try {
                val newSkill = UserSkill(0, skillName = skillName, userId = userId)
                val addedSkill = RetrofitHelper.apiService.addUserSkill(newSkill)
                // Optionally, update the state to include the new skill
                _userSkills.value = _userSkills.value?.plus(addedSkill) // Add the new skill to the existing list
            } catch (e: Exception) {
                // Handle any errors here (e.g., show a Toast or update the UI state)
            }
        }
    }
}

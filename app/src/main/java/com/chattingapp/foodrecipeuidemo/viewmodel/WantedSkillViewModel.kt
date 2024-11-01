package com.chattingapp.foodrecipeuidemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.foodrecipeuidemo.entity.UserSkill
import com.chattingapp.foodrecipeuidemo.retrofit.RetrofitHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class WantedSkillsViewModel : ViewModel() {

    private val _wantedSkills = MutableStateFlow<List<UserSkill>?>(null)
    val wantedSkills: StateFlow<List<UserSkill>?> get() = _wantedSkills

    fun getWantedSkills(userId: Long) {
        viewModelScope.launch {
            try {
                val skills = RetrofitHelper.apiService.getWantedSkillsByUserId(userId)
                _wantedSkills.value = skills
            } catch (e: Exception) {
                // Handle the error (log it or show a message)
                _wantedSkills.value = null
            }
        }
    }

    fun deleteSkill(id: Long) {
        viewModelScope.launch {
            try {
                RetrofitHelper.apiService.deleteWantedSkill(id)
                // Update the _userSkills state to reflect the deletion
                _wantedSkills.value = _wantedSkills.value?.filter { it.id != id }
            } catch (e: Exception) {
                // Handle any errors here (e.g., show a Toast or update the UI state)
            }
        }
    }

    fun addSkill(skillName: String, userId: Long) {
        // Check if the skill already exists
        val existingSkills = _wantedSkills.value
        if (existingSkills?.any { it.skillName.equals(skillName, ignoreCase = true) } == true) {
            // Skill already exists, return or show a message to the user
            return
        }

        // Proceed to add the skill
        viewModelScope.launch {
            try {
                val newSkill = UserSkill(0, skillName = skillName, userId = userId)
                val addedSkill = RetrofitHelper.apiService.addWantedSkill(newSkill)
                // Optionally, update the state to include the new skill
                _wantedSkills.value = _wantedSkills.value?.plus(addedSkill) // Add the new skill to the existing list
            } catch (e: Exception) {
                // Handle any errors here (e.g., show a Toast or update the UI state)
            }
        }
    }
}

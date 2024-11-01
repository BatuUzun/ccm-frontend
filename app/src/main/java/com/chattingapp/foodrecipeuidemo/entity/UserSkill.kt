package com.chattingapp.foodrecipeuidemo.entity

data class UserSkill(
    val id: Long,          // Unique identifier for the user skill
    val skillName: String, // Name of the skill
    val userId: Long       // ID of the user associated with the skill
)

package com.chattingapp.foodrecipeuidemo.entity

data class UserSkillMatchDTO(
    val user1Id: Long,
    val user1Username: String,
    val user1Skill: String,
    val user2Id: Long,
    val user2Username: String,
    val user2WantedSkill: String,
    val user2Skill: String,
    val user1WantedSkill: String,
    val user2ProfilePicture: String
)

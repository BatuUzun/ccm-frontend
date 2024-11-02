package com.chattingapp.foodrecipeuidemo.entity

data class MessageRequest(
    val chatId: Long,
    val message: String,
    val senderId: Long // Adjust this based on your user structure
)

package com.chattingapp.foodrecipeuidemo.entity

data class Message(
    val id: Long,
    val chatId: Long,
    val senderId: Long,
    val message: String,
    val time: String // Ensure this matches the format your server returns
)

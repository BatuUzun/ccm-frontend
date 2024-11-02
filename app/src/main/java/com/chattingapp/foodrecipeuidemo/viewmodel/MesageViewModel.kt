package com.chattingapp.foodrecipeuidemo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.foodrecipeuidemo.entity.Message
import com.chattingapp.foodrecipeuidemo.entity.MessageRequest // Make sure this import is included
import com.chattingapp.foodrecipeuidemo.retrofit.RetrofitHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MessageViewModel : ViewModel() {
    private val _chatLog = MutableStateFlow<List<Message>>(emptyList())
    val chatLog: StateFlow<List<Message>> get() = _chatLog

    fun fetchChatLog(chatId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val messages = RetrofitHelper.apiService.getChatLog(chatId)
                _chatLog.value = messages
            } catch (e: Exception) {
                // Handle errors here, such as logging or updating a UI state for error display
                e.printStackTrace()
            }
        }
    }

    // Function to send a message
    fun sendMessage(chatId: Long, messageText: String, senderId: Long) {
        val request = MessageRequest(
            chatId = chatId,
            message = messageText,
            senderId = senderId // Use the current user's ID
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {
                // Make the API call to send the message
                val sentMessage = RetrofitHelper.apiService.sendMessage(request)

                // Update the chat log with the new message
                _chatLog.value = _chatLog.value + sentMessage // Add the new message to the existing list
            } catch (e: Exception) {
                // Handle errors here
                e.printStackTrace()
            }
        }
    }
}

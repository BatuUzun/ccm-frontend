package com.chattingapp.foodrecipeuidemo.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chattingapp.foodrecipeuidemo.entity.Chat
import com.chattingapp.foodrecipeuidemo.retrofit.RetrofitHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ChatViewModel : ViewModel() {



    val createdChatLiveData = MutableLiveData<Chat?>()
    val errorLiveData = MutableLiveData<String?>()

    fun createChat(user1Id: Long, user2Id: Long) {
        viewModelScope.launch {
            try {
                val chat = Chat(0, user1Id = user1Id, user2Id = user2Id, "", "")
                val createdChat = RetrofitHelper.apiService.createChat(chat)
                createdChatLiveData.value = createdChat
            } catch (e: Exception) {
                errorLiveData.value = e.message
            }
        }
    }

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> get() = _error
    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> get() = _chats

    fun getChatsByUserId(userId: Long) {
        viewModelScope.launch {
            try {
                val chatsList = RetrofitHelper.apiService.getChatsByUserId(userId)
                _chats.value = chatsList // Set the fetched chats to the state flow
                _error.value = null // Clear any previous error
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }
}

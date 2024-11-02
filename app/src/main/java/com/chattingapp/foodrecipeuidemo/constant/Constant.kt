package com.chattingapp.foodrecipeuidemo.constant

import com.chattingapp.foodrecipeuidemo.entity.User
import com.chattingapp.foodrecipeuidemo.entity.UserProfile

object Constant {
    lateinit var user:User
    lateinit var userProfile:UserProfile
    const val MINIMUM_PASSWORD_SIZE = 8
    const val AWS_S3_LINK = "https://ccn-photo.s3.us-east-1.amazonaws.com/"

    var isAddPage = false
    var createChatUser2Id = -1L
}
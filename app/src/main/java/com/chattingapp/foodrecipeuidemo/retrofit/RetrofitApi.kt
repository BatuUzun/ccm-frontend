package com.chattingapp.foodrecipeuidemo.retrofit

import com.chattingapp.foodrecipeuidemo.entity.AuthenticationDTO
import com.chattingapp.foodrecipeuidemo.entity.ChangePasswordRequest
import com.chattingapp.foodrecipeuidemo.entity.User
import com.chattingapp.foodrecipeuidemo.entity.UserProfile
import com.chattingapp.foodrecipeuidemo.entity.UserProfileDTO
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface RetrofitAPICredentials {

    // CREDENTIALS API
    @POST("credentials/create-user/")
    suspend fun saveUser(@Body userProfileDTO: UserProfileDTO?): String

    @POST("credentials/check-login-credentials/")
    suspend fun checkLoginCredentials(@Body authenticationDTO: AuthenticationDTO) : User

    @GET("credentials/get-user-token/")
    suspend fun getUserByToken(@Query("token") token:String) : User

    @PUT("credentials/verify-email/")
    suspend fun verifyUser(@Query("email") email:String) : Boolean

    // EMAIL API
    @GET("email-sender/send-verification-code/")
    suspend fun sendVerificationEmail(@Query("email") email:String) : Int

    @DELETE("credentials/delete-token")
    suspend fun deleteToken(@Query("userId") userId: Long, @Query("token") token: String): Response<Unit>

    @GET("credentials/exists-by-email/{email}")
    suspend fun userExistsByEmail(@Path("email") email: String): Boolean

    @GET("email-sender/send-change-password-code/")
    suspend fun sendChangePasswordEmail(@Query("email") email:String) : Int

    @POST("credentials/change-password")
    suspend fun changePassword(@Body request: ChangePasswordRequest): Boolean

    // PROFILE API
    @GET("credentials/get-user-profile-by-email/")
    suspend fun getUserProfileByEmail(@Query("email") email:String): UserProfile

}
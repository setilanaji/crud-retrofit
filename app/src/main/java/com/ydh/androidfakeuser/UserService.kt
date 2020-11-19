package com.ydh.androidfakeuser

import com.ydh.androidfakeuser.model.UserModel
import retrofit2.Call
import retrofit2.http.*

interface UserService {
    @GET("users")
    fun getAllUser(@Query("page") page: Int): Call<UserResponse>

    @GET("users/{id}")
    fun getUser(@Path("id") id: Int): Call<UserResponse>

    @POST("users")
    fun createUser(@Body createUserBody: CreateUserBody ): Call<CreateUserResponse>

    @PUT("users/{id}")
    fun updateUser(@Body updateUserBody: UpdateUserBody, @Path("id") id: Int): Call<UpdateUserResponse>

    @DELETE("users/{id}")
    fun deleteUser(@Path("id") id: Int): Call<String>
}
package com.ydh.androidfakeuser

import com.google.gson.annotations.SerializedName
import com.ydh.androidfakeuser.model.UserModel

data class UserResponse(

    @SerializedName("data")
    val listUser: ArrayList<UserModel>
)
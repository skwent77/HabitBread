package com.habitbread.main.data

import com.google.gson.annotations.SerializedName
import java.util.*

class UserInfoResponse (
    @SerializedName("userId")
    val userId : Int,
    @SerializedName("name")
    val userName: String,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("createdAt")
    val createdAt: Date,
    @SerializedName("updatedAt")
    val updatedAt: Date
)
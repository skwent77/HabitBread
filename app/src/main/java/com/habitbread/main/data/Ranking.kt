package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

data class Ranking(
    @SerializedName("userId")
    val userId : Int,
    @SerializedName("userName")
    val userName : String,
    @SerializedName("exp")
    val exp: Int,
    @SerializedName("achievement")
    val achievement : Int,
    @SerializedName("rank")
    val rank : String
)
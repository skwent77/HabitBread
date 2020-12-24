package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

data class GoogleOAuthRequest(
    @SerializedName("idToken")
    val idToken: String
)
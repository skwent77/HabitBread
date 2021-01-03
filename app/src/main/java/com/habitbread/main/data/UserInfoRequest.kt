package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

class UserInfoRequest {
    data class NicknameRequest(
        @SerializedName("name")
        val name: String
    )

    data class FcmTokenRequest(
        @SerializedName("fcmToken")
        val fcmToken: String
    )
}
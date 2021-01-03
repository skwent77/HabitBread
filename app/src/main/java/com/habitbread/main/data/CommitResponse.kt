package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

data class CommitResponse(
    @SerializedName("itemId")
    val itemId: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("level")
    val level: Int?
)




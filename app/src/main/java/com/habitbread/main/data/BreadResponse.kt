package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

data class BreadResponse (
    @SerializedName("userItemId")
    val userItemId: Int,
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("item")
    val item: ItemResponse
)

data class ItemResponse (
    @SerializedName("itemId")
    val itemId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
    @SerializedName("level")
    val level: Int
)
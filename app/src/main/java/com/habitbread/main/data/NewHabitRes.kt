package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

data class NewHabitRes (
    @SerializedName("habitId")
    val habitId: Int,
    @SerializedName("title")
    val habitName: String
)

data class NewChangedHabitRes (
    val title: String,
    val category: String,
    val description: String?,
    val alarmTime: String?
)

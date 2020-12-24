package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

data class HabitListResponse(
    @SerializedName("comment")
    val comment: String,
    @SerializedName("habits")
    val habits: List<Habits>
)

data class Habits(
    @SerializedName("habitId")
    val habitId: Int,
    @SerializedName("title")
    val habitName: String,
    @SerializedName("description")
    val description: String?,
    @SerializedName("dayOfWeek")
    val dayOfWeek: String,
    @SerializedName("commitHistory")
    val commitHistory: List<HabitCommitHistory>
)




package com.habitbread.main.data

import com.google.gson.annotations.SerializedName

data class DetailResponse(
    @SerializedName("habit")
    val habit: HabitDetailInfo,
    @SerializedName("commitFullCount")
    val commitFullCount: Int,
    @SerializedName("comparedToLastMonth")
    val comparedToLastMonth: Int
)

data class HabitDetailInfo(
    @SerializedName("habitId")
    val habitId: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("dayOfWeek")
    val dayOfWeek: String,
    @SerializedName("alarmTime")
    val alarmTime: String,
    @SerializedName("continuousCount")
    val continuousCount: Int,
    @SerializedName("commitHistory")
    val commitHistory: List<HabitCommitHistory>
)

data class HabitCommitHistory(
    @SerializedName("createdAt")
    val createdAt: String
)

data class DeleteHabit(
    @SerializedName("message")
    val message: String
)



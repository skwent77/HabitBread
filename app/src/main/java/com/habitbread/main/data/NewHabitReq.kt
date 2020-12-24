package com.habitbread.main.data

data class NewHabitReq (
    val title: String,
    val category: String,
    val description: String?,
    val dayOfWeek: String,
    val alarmTime: String?
)

data class NewChangedHabitReq (
    val title: String,
    val category: String,
    val description: String?,
    val alarmTime: String?
)

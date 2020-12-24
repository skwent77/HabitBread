package com.habitbread.main.util

import com.habitbread.main.data.Habits
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateCalculation {
    val today = getTodayOfWeek()
    val tomorrow = if(today == 6) 0 else today + 1
    fun getTodayOfWeek(): Int {
        val calendar: Calendar = Calendar.getInstance()
        val dayOfWeek: Int = calendar.get(java.util.Calendar.DAY_OF_WEEK)-1 // Sun = 0, Mon = 1, Tue = 2, ... , Sat = 6
        return dayOfWeek
    }

    fun getTodayOfWeekWithDate(inputDate: String): Int {
        val dataFormat = SimpleDateFormat("yyyy-MM-dd", Locale.KOREA)
        val date: Date? = dataFormat.parse(inputDate)
        val calendar: Calendar = Calendar.getInstance()
        calendar.time = date!!
        return calendar.get(Calendar.DAY_OF_WEEK)-1
    }

    fun convertUTCtoSeoulTime(inputTime: String): String {
        val utcZone: ZoneId = ZoneId.of("UTC")
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val dateTime: LocalDateTime = LocalDateTime.parse(inputTime, formatter)
        val utcDateTime: ZonedDateTime = dateTime.atZone(utcZone)
        val zdt: ZonedDateTime = utcDateTime.withZoneSameInstant(ZoneId.of("Asia/Seoul"))
        val date: String = zdt.format(DateTimeFormatter.ISO_LOCAL_DATE)
        return date
    }

    fun habitListSorting(habitList: List<Habits>): List<Habits> {
        var sortedList = habitList
        sortedList = habitList.sortedWith(Comparator<Habits> { o1, o2 ->
            val todayIndex: Int = DateCalculation().getTodayOfWeek()
            var tomorrowIndex: Int = todayIndex + 1
            if(todayIndex == 6) {
                tomorrowIndex = 0
            }
            val todayCompareFirst: Int = o1.dayOfWeek[todayIndex].toInt()
            val todayCompareSecond: Int = o2.dayOfWeek[todayIndex].toInt()
            val tomorrowCompareFirst: Int = o1.dayOfWeek[tomorrowIndex].toInt()
            val tomorrowCompareSecond: Int = o2.dayOfWeek[tomorrowIndex].toInt()

            when{
                // 1, 0, -1 우선순위로 정렬
                todayCompareFirst < todayCompareSecond -> 1
                tomorrowCompareFirst < tomorrowCompareSecond -> 0
                else -> -1
            }
        })
        return sortedList
    }
}
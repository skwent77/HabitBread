package com.habitbread.main.repository

import com.habitbread.main.api.ServerImpl
import com.habitbread.main.data.HabitListResponse
import com.habitbread.main.data.RankResponse
import com.habitbread.main.data.NewHabitReq
import kotlinx.coroutines.runBlocking
import retrofit2.await

class HabitRepository {

    val TAG :String? = "HabitBread"
    private val habitBreadAPI = ServerImpl.APIService
    var allRanks : RankResponse? = null
    private lateinit var allHabitListData : HabitListResponse


    fun getHabitList(): HabitListResponse {
        runBlocking {
            val request = habitBreadAPI.getAllHabitLists()
            val response = request.await()
            allHabitListData = response
       }
        return allHabitListData
    }

    fun postNewHabit(body : NewHabitReq): HabitListResponse{
        runBlocking {
            val postRequest = habitBreadAPI.postNewHabit(body)
            val postResponse = postRequest.await()
            val getRequest = habitBreadAPI.getAllHabitLists()
            val getResponse = getRequest.await()
            allHabitListData = getResponse
        }
        return allHabitListData
    }
}
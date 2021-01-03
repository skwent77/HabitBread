package com.habitbread.main.repository

import com.habitbread.main.api.ServerImpl
import com.habitbread.main.data.*
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import retrofit2.await
import retrofit2.awaitResponse

class DetailRepository {

    private val habitBreadAPI = ServerImpl.APIService
    private lateinit var detailData : DetailResponse
    private lateinit var commitResponse: Response<CommitResponse>
    private lateinit var deleteHabit: DeleteHabit
    private lateinit var changedHabitData: NewChangedHabitRes

    fun getDetailData(habitId: Int, year: Int, month: Int): DetailResponse {
        runBlocking {
            val request = habitBreadAPI.getHabitDetail(habitId, year, month)
            val response = request.await()
            detailData = response
        }
        return detailData
    }

    fun postCommit(habitId: Int): Response<CommitResponse> {
        runBlocking {
            val request = habitBreadAPI.postCommit(habitId)
            val response: Response<CommitResponse> = request.awaitResponse()
            commitResponse = response
        }
        return commitResponse
   }

    fun deleteHabit(habitId: Int): DeleteHabit {
        runBlocking {
            val request = habitBreadAPI.deleteHabit(habitId)
            val response: DeleteHabit = request.await()
            deleteHabit = response
        }
        return deleteHabit
    }

    fun putChangedHabitData(habitId: Int, body: NewChangedHabitReq): NewChangedHabitRes {
        runBlocking {
            val request = habitBreadAPI.putChangedHabitData(habitId, body)
            val response: NewChangedHabitRes = request.await()
            changedHabitData = response
        }
        return changedHabitData
    }
}
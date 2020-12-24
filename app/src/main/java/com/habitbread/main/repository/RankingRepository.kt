package com.habitbread.main.repository

import android.util.Log
import com.habitbread.main.api.ServerImpl
import com.habitbread.main.data.RankResponse
import kotlinx.coroutines.runBlocking
import retrofit2.await

class RankingRepository {
    private val habitBreadAPI = ServerImpl.APIService
    private var allRanks : RankResponse? = null;

    fun getAllRanks() : RankResponse{
        runBlocking {
            val request = habitBreadAPI.getAllRankings()
            val response = request.await()
            Log.d("HabitBread", response.toString());
            allRanks = response
        }
        return allRanks!!;
    }
}
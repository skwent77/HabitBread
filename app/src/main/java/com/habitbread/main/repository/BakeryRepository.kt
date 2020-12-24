package com.habitbread.main.repository

import android.util.Log
import com.habitbread.main.api.ServerImpl
import com.habitbread.main.data.BreadResponse
import kotlinx.coroutines.runBlocking
import retrofit2.await

class BakeryRepository {

    private val habitBreadAPI = ServerImpl.APIService
    private var breadsData: List<BreadResponse>? = listOf()

    fun getBreads(): List<BreadResponse>? {
        runBlocking {
            val request = habitBreadAPI.getBreads()
            val response = request.await()
            breadsData = response
            Log.d("chohee_RP", breadsData.toString())
        }
        return breadsData
    }
}
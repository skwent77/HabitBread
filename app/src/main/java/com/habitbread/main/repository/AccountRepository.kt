package com.habitbread.main.repository

import android.util.Log
import com.habitbread.main.api.ServerImpl
import com.habitbread.main.data.AccountResponse
import com.habitbread.main.data.BaseResponse
import com.habitbread.main.data.UserInfoRequest
import com.habitbread.main.data.UserInfoResponse
import kotlinx.coroutines.runBlocking
import retrofit2.await

class AccountRepository {
    private val habitBreadAPI = ServerImpl.APIService
    private var account : AccountResponse? = null;
    private var baseResponse : BaseResponse = BaseResponse("");
    private var userInfo : UserInfoResponse? = null;

    fun getUserInfo() : AccountResponse {
        runBlocking {
            val request = habitBreadAPI.getUserInfo()
            val response = request.await();
            Log.d("HabitBread", response.toString())
            account = response
        }
        return account!!;
    }

    fun deleteAccount() : BaseResponse{
        runBlocking {
            val request = habitBreadAPI.deleteAccount()
            val response = request.await()
            baseResponse = response
        }
        return baseResponse
    }

    fun updateUserNickname(nickname: String) : UserInfoResponse {
        runBlocking {
            val request = habitBreadAPI.updateNickname(UserInfoRequest.NicknameRequest(nickname))
            val response = request.await()
            userInfo = response
        }
        return userInfo!!
    }

    fun updateFcmToken(fcmToken: String) : UserInfoResponse {
        runBlocking {
            val request = habitBreadAPI.patchFcmToken(UserInfoRequest.FcmTokenRequest(fcmToken))
            val response = request.await()
            userInfo = response
        }
        return userInfo!!
    }
}
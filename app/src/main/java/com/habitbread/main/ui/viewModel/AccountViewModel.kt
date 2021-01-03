package com.habitbread.main.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habitbread.main.api.FirebaseAPI
import com.habitbread.main.data.AccountResponse
import com.habitbread.main.data.UserInfoResponse
import com.habitbread.main.repository.AccountRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class AccountViewModel : ViewModel() {

    val accountData: MutableLiveData<AccountResponse> = MutableLiveData()
    val userInfoData: MutableLiveData<UserInfoResponse> = MutableLiveData()

    fun getUserInfo() {
        GlobalScope.launch {
            try {
                val accountList = AccountRepository().getUserInfo()
                accountData.postValue(accountList)
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    fun updateUserNickname(nickname: String) {
        GlobalScope.launch {
            try {
                val userInfoList = AccountRepository().updateUserNickname(nickname)
                userInfoData.postValue(userInfoList);
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }

    fun deleteAccount() {
       GlobalScope.launch {
           try {
               val response = AccountRepository().deleteAccount();
               Log.d("HabitBread", response.toString())
           } catch (e : Exception) {
               e.printStackTrace()
           }
       }
    }
}
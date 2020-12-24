package com.habitbread.main.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habitbread.main.data.*
import com.habitbread.main.repository.DetailRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Response
import java.lang.Error

class DetailViewModel : ViewModel(){

    var detailData: MutableLiveData<DetailResponse> = MutableLiveData()
    var commitData: MutableLiveData<Response<CommitResponse>> = MutableLiveData()
    var deleteData: MutableLiveData<DeleteHabit> = MutableLiveData()
    var changedHabitData: MutableLiveData<NewChangedHabitRes> = MutableLiveData()

    fun getDetailData(habitId: Int, year: Int, month: Int){
        GlobalScope.launch {
            try {
                val data = DetailRepository().getDetailData(habitId, year, month)
                detailData.postValue(data)
            }catch (err: Error){
                Log.e("HabitBread", err.printStackTrace().toString())
            }
        }
    }

    fun postCommit(habitId: Int) {
        GlobalScope.launch {
            try {
                val data = DetailRepository().postCommit(habitId)
                commitData.postValue(data)
            }catch (err: Error) {
                Log.e("HabitBread", err.printStackTrace().toString())
            }
        }
    }

    fun deleteHabit(habitId: Int) {
        GlobalScope.launch {
            try {
                val data = DetailRepository().deleteHabit(habitId)
                deleteData.postValue(data)
            }catch (err: Error) {
                Log.e("HabitBread", err.printStackTrace().toString())
            }
        }
    }

    fun putChangedHabitData(habitId: Int, body: NewChangedHabitReq) {
        GlobalScope.launch {
            try {
                val data = DetailRepository().putChangedHabitData(habitId, body)
                changedHabitData.postValue(data)
            }catch (err: Error) {
                Log.e("HabitBread", err.printStackTrace().toString())
            }
        }
    }
}
package com.habitbread.main.ui.viewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habitbread.main.data.BreadResponse
import com.habitbread.main.repository.BakeryRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BakeryViewModel: ViewModel() {

    var breadsData: MutableLiveData<List<BreadResponse>?> = MutableLiveData()

    fun getBreads(){
        GlobalScope.launch {
            try {
                val data = BakeryRepository().getBreads()
                breadsData.postValue(data)
            }catch (err: Error){
                Log.e("HabitBread", err.printStackTrace().toString())
            }
        }
    }
}
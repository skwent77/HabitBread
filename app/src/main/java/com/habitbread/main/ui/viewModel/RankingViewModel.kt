package com.habitbread.main.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.habitbread.main.data.RankResponse
import com.habitbread.main.repository.RankingRepository
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.lang.Exception

class RankingViewModel : ViewModel() {
    var rankingData: MutableLiveData<RankResponse> = MutableLiveData()

    fun getAllRanks() {
        GlobalScope.launch {
            try {
                val rankList = RankingRepository().getAllRanks();
                rankingData.postValue(rankList);
            } catch (e : Exception) {
                e.printStackTrace()
            }
        }
    }
}
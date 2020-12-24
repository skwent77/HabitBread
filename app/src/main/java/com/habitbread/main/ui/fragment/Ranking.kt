package com.habitbread.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habitbread.main.R
import com.habitbread.main.adapter.RankListAdapter
import com.habitbread.main.ui.viewModel.RankingViewModel
import kotlinx.android.synthetic.main.fragment_ranking.*
import kotlin.math.floor

class Ranking : Fragment() {
    private lateinit var recyclerview_rankList : RecyclerView
    private lateinit var recyclerview_adapter: RankListAdapter
    val rankingViewModel : RankingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_ranking, container, false)
        recyclerview_rankList = view.findViewById(R.id.recyclerview_rankingList)
        return view;
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rankingViewModel.rankingData.observe(viewLifecycleOwner, Observer {
            recyclerview_adapter.setAdapterData(it)
            if (it.user != null) {
                val percentage = floor(it.user.rank.toDouble()/it.userTotalCount*100).toInt()
                textview_my_rank_percent_above.visibility = View.VISIBLE
                textview_my_rank_percent.visibility = View.VISIBLE
                textview_my_rank_percent.text = "$percentage%"
                textview_my_rank_with_total.text = getString(R.string.totalRanking, it.userTotalCount, it.user.rank)
            } else {
                textview_my_rank_percent_above.visibility = View.INVISIBLE
                textview_my_rank_percent.visibility = View.INVISIBLE
                textview_my_rank_with_total.text = "아직 점수가 산정되지 않았습니다. 잠시만 기다려주세요!"
            }
        })
        initRecyclerView();
    }

    override fun onResume() {
        super.onResume()
        rankingViewModel.getAllRanks()
    }

    private fun initRecyclerView() {
            recyclerview_adapter = RankListAdapter(context)
            recyclerview_rankList.adapter = recyclerview_adapter
            recyclerview_rankList.layoutManager = LinearLayoutManager(context)
    }
}
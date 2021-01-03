package com.habitbread.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habitbread.main.R
import com.habitbread.main.adapter.BakeryAdapter
import com.habitbread.main.data.BreadsData
import com.habitbread.main.ui.viewModel.BakeryViewModel
import com.habitbread.main.util.GridViewDecoration
import kotlinx.android.synthetic.main.fragment_bakery.view.*

class Bakery : Fragment() {

    private lateinit var rvBakeryLevel1: RecyclerView
    private lateinit var rvBakeryLevel2: RecyclerView
    private lateinit var rvBakeryLevel3: RecyclerView
    private lateinit var rvBakeryLevel4: RecyclerView
    private lateinit var rvBakeryAdapterLevel1: BakeryAdapter
    private lateinit var rvBakeryAdapterLevel2: BakeryAdapter
    private lateinit var rvBakeryAdapterLevel3: BakeryAdapter
    private lateinit var rvBakeryAdapterLevel4: BakeryAdapter
    private val bakeryViewModel: BakeryViewModel by viewModels()
    private val breadsData: BreadsData = BreadsData()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_bakery, container, false)
        initRecyclerView(view)
        view.imageVew_back_button.setOnClickListener {
            findNavController().navigateUp()
        }
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setBreads()
    }

    private fun initRecyclerView(view: View) {
        rvBakeryLevel1 = view.findViewById(R.id.recyclerView_level1)
        rvBakeryAdapterLevel1 = BakeryAdapter(requireContext())
        rvBakeryLevel1.adapter = rvBakeryAdapterLevel1
        rvBakeryLevel1.layoutManager = GridLayoutManager(requireContext(), 3)
        rvBakeryLevel1.addItemDecoration(GridViewDecoration(3, 20, true))

        //Level2
        rvBakeryLevel2 = view.findViewById(R.id.recyclerView_level2)
        rvBakeryAdapterLevel2 = BakeryAdapter(requireContext())
        rvBakeryLevel2.adapter = rvBakeryAdapterLevel2
        rvBakeryLevel2.layoutManager = GridLayoutManager(requireContext(), 3)
        rvBakeryLevel2.addItemDecoration(GridViewDecoration(3, 20, true))

        //Level3
        rvBakeryLevel3 = view.findViewById(R.id.recyclerView_level3)
        rvBakeryAdapterLevel3 = BakeryAdapter(requireContext())
        rvBakeryLevel3.adapter = rvBakeryAdapterLevel3
        rvBakeryLevel3.layoutManager = GridLayoutManager(requireContext(), 3)
        rvBakeryLevel3.addItemDecoration(GridViewDecoration(3, 20, true))

        //Level4
        rvBakeryLevel4 = view.findViewById(R.id.recyclerView_level4)
        rvBakeryAdapterLevel4 = BakeryAdapter(requireContext())
        rvBakeryLevel4.adapter = rvBakeryAdapterLevel4
        rvBakeryLevel4.layoutManager = GridLayoutManager(requireContext(), 3)
        rvBakeryLevel4.addItemDecoration(GridViewDecoration(3, 20, true))
    }

    private fun setBreads() {
        bakeryViewModel.getBreads()
        bakeryViewModel.breadsData.observe(requireActivity(), Observer {
            val level1ItemIds: MutableList<Int> = mutableListOf()
            val level2ItemIds: MutableList<Int> = mutableListOf()
            val level3ItemIds: MutableList<Int> = mutableListOf()
            val level4ItemIds: MutableList<Int> = mutableListOf()
            if(it == null) {
                rvBakeryAdapterLevel1.setAdapterData(breadsData.level1, level1ItemIds)
                rvBakeryAdapterLevel2.setAdapterData(breadsData.level2, level2ItemIds)
                rvBakeryAdapterLevel3.setAdapterData(breadsData.level3, level3ItemIds)
                rvBakeryAdapterLevel4.setAdapterData(breadsData.level4, level4ItemIds)
            }else {
                for(i in it.indices) {
                    if(breadsData.level1Ids.contains(it[i].item.itemId)) {
                        level1ItemIds.add(it[i].item.itemId)
                    }
                    if(breadsData.level2Ids.contains(it[i].item.itemId)) {
                        level2ItemIds.add(it[i].item.itemId)
                    }
                    if(breadsData.level3Ids.contains(it[i].item.itemId)) {
                        level3ItemIds.add(it[i].item.itemId)
                    }
                    if(breadsData.level4Ids.contains(it[i].item.itemId)) {
                        level4ItemIds.add(it[i].item.itemId)
                    }
                }
                rvBakeryAdapterLevel1.setAdapterData(breadsData.level1, level1ItemIds)
                rvBakeryAdapterLevel2.setAdapterData(breadsData.level2, level2ItemIds)
                rvBakeryAdapterLevel3.setAdapterData(breadsData.level3, level3ItemIds)
                rvBakeryAdapterLevel4.setAdapterData(breadsData.level4, level4ItemIds)
            }
            rvBakeryAdapterLevel1.notifyDataSetChanged()
            rvBakeryAdapterLevel2.notifyDataSetChanged()
            rvBakeryAdapterLevel3.notifyDataSetChanged()
            rvBakeryAdapterLevel4.notifyDataSetChanged()
        })
    }


}
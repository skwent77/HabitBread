package com.habitbread.main.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.habitbread.main.R
import com.habitbread.main.adapter.HabitListAdapter
import com.habitbread.main.data.NewHabitReq
import com.habitbread.main.ui.viewModel.HabitViewModel
import com.habitbread.main.util.DateCalculation
import kotlinx.android.synthetic.main.fragment_my_habits.*
import kotlinx.android.synthetic.main.layout_add_button.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MyHabits : Fragment() {

    private lateinit var recyclerviewHabitList: RecyclerView
    private lateinit var recyclerviewAdapter: HabitListAdapter
    private val habitViewModel: HabitViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view= inflater.inflate(R.layout.fragment_my_habits, container, false)
        recyclerviewHabitList = view.findViewById(R.id.recyclerView_habitlist)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        habitViewModel.getAllList()
        initRecyclerView()
        habitViewModel.rvData.observe(viewLifecycleOwner, Observer {
            textView_announcement.text = it.comment
            val sortedList = DateCalculation().habitListSorting(it.habits)
            if (it.habits.size >= 10) {
                layout_create_habit.visibility = View.GONE
            }
            recyclerviewAdapter.setAdapterData(sortedList)
        })
        onClickNewHabit()
        onClickBakery()
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onResume() {
        super.onResume()
        habitViewModel.getAllList()
    }

    override fun onStop() {
        EventBus.getDefault().unregister(this)
        super.onStop()
    }

    private fun initRecyclerView() {
        recyclerviewAdapter = HabitListAdapter(context)
        recyclerviewHabitList.adapter = recyclerviewAdapter
        recyclerviewHabitList.layoutManager = LinearLayoutManager(context)
    }

    private fun onClickNewHabit(){
        button_add.setOnClickListener {
            findNavController().navigate(R.id.action_viewPager_to_registrationBottomSheet)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBottomSheetDoneEvent(modalPost: ModalPost){
        val body = NewHabitReq(title = modalPost.title, category = modalPost.category, description = modalPost.description, dayOfWeek = modalPost.dayOfWeek, alarmTime = modalPost.alarmTime)
        habitViewModel.postHabit(body)
    }

    private fun onClickBakery() {
        imageView_bakery_circle.setOnClickListener {
            findNavController().navigate(R.id.action_viewPager_to_bakery)
        }
    }
}
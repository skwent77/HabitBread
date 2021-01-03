package com.habitbread.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.habitbread.main.R
import com.habitbread.main.data.Habits
import com.habitbread.main.util.DateCalculation
import kotlinx.android.synthetic.main.item_habit.view.*

class HabitListAdapter(private val context: Context?) : RecyclerView.Adapter<HabitListAdapter.HabitListViewHolder>() {

    private var habits : List<Habits>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HabitListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_habit, parent, false)
        return HabitListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return if (habits == null) 0 else habits!!.size
    }

    override fun onBindViewHolder(holder: HabitListViewHolder, position: Int) {
        holder.bind(habits!![position])
    }

    inner class HabitListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        private val tvHabitName: TextView = itemView.textView_habitName
        private val tvDescription: TextView = itemView.textView_description

        fun bind(data: Habits){
            tvHabitName.text = data.habitName
            tvDescription.text = data.description
            var shouldCommitToday = false
            when {
                data.dayOfWeek[DateCalculation().today] == '1' -> {
                    itemView.textView_isToday.text = "오늘"
                    shouldCommitToday = true
                }
                data.dayOfWeek[DateCalculation().tomorrow] == '1' -> {
                    itemView.textView_isToday.text = "내일"
                    itemView.imageView_dot.visibility = View.INVISIBLE
                    itemView.textView_isToday.setBackgroundColor(Color.parseColor("#CCFFFFFF"))
                    itemView.button_habit.setBackgroundColor(Color.parseColor("#CCFFFFFF"))
                }
                else -> {
                    itemView.textView_isToday.visibility = View.INVISIBLE
                    itemView.imageView_dot.visibility = View.INVISIBLE
                    itemView.textView_isToday.setBackgroundColor(Color.parseColor("#CCFFFFFF"))
                    itemView.button_habit.setBackgroundColor(Color.parseColor("#CCFFFFFF"))
                }
            }

            // 습관 등록 요일만 표시하기
           for(i in 0..6) {
                if(data.dayOfWeek[i] == '1') {
                    itemView.findViewWithTag<TextView>(i.toString()).visibility = View.VISIBLE
                }else {
                    itemView.findViewWithTag<TextView>(i.toString()).visibility = View.GONE
                }
            }

            // 커밋한 날짜 초록색 표시하기
            val isCommitList: MutableList<Int> = mutableListOf()
            for(element in data.commitHistory) {
                val inputDate: String = DateCalculation().convertUTCtoSeoulTime(element.createdAt).substring(0, 10)
                val index = DateCalculation().getTodayOfWeekWithDate(inputDate)
                itemView.findViewWithTag<TextView>(index.toString()).setBackgroundResource(R.drawable.background_dayofweek)
                itemView.findViewWithTag<TextView>(index.toString()).setTextColor(Color.parseColor("#FFFFFF"))
                isCommitList.add(index)
            }

            // 빨간 점 표시하기
            if(shouldCommitToday) {
                if(isCommitList.contains(DateCalculation().getTodayOfWeek())) {
                    itemView.imageView_dot.visibility = View.INVISIBLE
                }else {
                    itemView.imageView_dot.visibility = View.VISIBLE
                }
            }

            itemView.button_habit.setOnClickListener {
                val bundle = bundleOf("habitId" to data.habitId, "habitName" to data.habitName, "habitDescription" to data.description)
                it.findNavController().navigate(R.id.action_viewPager_to_detail, bundle)
            }
        }
    }

    fun setAdapterData(habitData: List<Habits>?){
        habits = habitData
        notifyDataSetChanged()
    }
}
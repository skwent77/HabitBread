package com.habitbread.main.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.habitbread.main.R
import com.habitbread.main.data.Bread
import kotlinx.android.synthetic.main.item_bread.view.*

class BakeryAdapter(val context: Context): RecyclerView.Adapter<BakeryAdapter.BakeryViewHolder>() {

    var breads = listOf<Bread>()
    var itemIds = listOf<Int>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BakeryViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_bread, parent, false)
        return BakeryViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return breads.size
    }

    override fun onBindViewHolder(holder: BakeryViewHolder, position: Int) {
        holder.bind(breads[position])
    }

    inner class BakeryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val tvLevel: TextView = itemView.textView_level
        val ivBread: ImageView = itemView.imageView_bread
        val tvBreadName: TextView = itemView.textView_bread_name

        fun bind(data : Bread) {
            tvLevel.text = "LV" + data.level.toString()
            tvBreadName.text = data.name
            if(itemIds.contains(data.itemId)) {
                if(data.level == 1) {
                    tvLevel.setBackgroundResource(R.drawable.border_bread_level1)
                }else if(data.level == 2) {
                    tvLevel.setBackgroundResource(R.drawable.border_bread_level2)
                }else if(data.level == 3) {
                    tvLevel.setBackgroundResource(R.drawable.border_bread_level3)
                }else {
                    tvLevel.setBackgroundResource(R.drawable.border_bread_level4)
                }
                tvBreadName.setTextColor(Color.parseColor("#726655"))
                ivBread.setImageResource(data.breadImg)
            }else{
                ivBread.setImageResource(data.defaultImg)
            }
        }
    }

    fun setAdapterData(data: List<Bread>, itemIds: List<Int>){
        this.breads = data
        this.itemIds = itemIds
        notifyDataSetChanged()
    }
}
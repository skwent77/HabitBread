package com.habitbread.main.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.habitbread.main.R
import com.habitbread.main.data.RankResponse
import com.habitbread.main.data.Ranking
import kotlinx.android.synthetic.main.item_rank.view.*

class RankListAdapter(val context: Context?) : RecyclerView.Adapter<RankListAdapter.RankingListViewHolder>() {

    var rankingData : List<Ranking> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingListViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.item_rank, parent, false)
        return RankingListViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return rankingData.size
    }

    override fun onBindViewHolder(holder: RankingListViewHolder, position: Int) {
        holder.bind(rankingData[position])
    }

    inner class RankingListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val tv_rank : TextView = itemView.textview_rank_num
        val tv_nickname : TextView = itemView.textview_rank_nickname
        val tv_exp : TextView = itemView.textview_rank_exp
        val tv_achievement : TextView = itemView.textview_rank_achievement
        val imageView_crown : ImageView = itemView.imageview_crown

        fun bind(data: Ranking){
            if ((rankingData[0].rank != data.rank) == (rankingData[1].rank != data.rank)) {
                imageView_crown.visibility = View.GONE
            } else {
                if (rankingData[0].rank == data.rank) {
                    imageView_crown.setBackgroundResource(R.drawable.ic_goldcrown);
                } else {
                    imageView_crown.setBackgroundResource(R.drawable.ic_silvercrown)
                }
                imageView_crown.visibility = View.VISIBLE
            }
            tv_rank.text = data.rank
            tv_nickname.text = data.userName
            tv_exp.text = data.exp.toString()
            tv_achievement.text = data.achievement.toString()
        }
    }

    fun setAdapterData(rankData: RankResponse?){
        rankingData = rankData!!.rankings
        notifyDataSetChanged()
    }
}
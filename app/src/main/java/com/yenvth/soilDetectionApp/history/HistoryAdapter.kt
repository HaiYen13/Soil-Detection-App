package com.yenvth.soilDetectionApp.history

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.models.HistoryModel
import com.yenvth.soilDetectionApp.utils.DateUtils

class HistoryAdapter(
    private val mContext: Context,
    private val listener: OnHistoryItemClickListener
) : RecyclerView.Adapter<HistoryAdapter.MyHolder>() {
    private var dataList = emptyList<HistoryModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<HistoryModel>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    interface OnHistoryItemClickListener {
        fun onHistoryClick(historyModel: HistoryModel)
        fun onItemDelete(historiesId: Int)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_history, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        if (getItemViewType(pos) == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#80cccccc"))
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"))
        }
        val historyModel = dataList[pos]
        holder.itemView.setOnClickListener { listener.onHistoryClick(historyModel) }
        holder.btnDelete.setOnClickListener { listener.onItemDelete(historyModel.historyId) }
        holder.tvName.text = historyModel.soilName
        holder.tvDate.text = DateUtils.convertTimes(historyModel.timestamp!!, "dd/MM/yyyy HH:mm")
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val btnDelete: ImageView
        val tvDate: TextView
        val cardView: MaterialCardView

        init {
            cardView = itemView.findViewById(R.id.cardView)
            tvName = itemView.findViewById(R.id.tvName)
            btnDelete = itemView.findViewById(R.id.btnDelete)
            tvDate = itemView.findViewById(R.id.tvDate)
        }
    }
}
package com.yenvth.soilDetectionApp.map

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.models.SoilModel
import com.yenvth.soilDetectionApp.utils.Constant
import java.util.*

class MapAdapter(
    private val mContext: Context,
    private val listener: OnSoilMapItemClickListener
) : RecyclerView.Adapter<MapAdapter.MyHolder>() {
    private var dataList = emptyList<SoilModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<SoilModel>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    interface OnSoilMapItemClickListener {
        fun onSoilMapClickListener(soilModel: SoilModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_soil_map, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        val soilModel = dataList[pos]
        holder.itemView.setOnClickListener {
            listener.onSoilMapClickListener(soilModel)
        }
        holder.lnImage.setBackgroundColor(
            mContext.resources.getColor(
                Constant.getColorArea(
                    soilModel.soilCode?.toLowerCase(Locale.getDefault())
                )
            )
        )
        holder.tvName.text = soilModel.nameVi
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val lnImage: LinearLayout

        init {
            tvName = itemView.findViewById(R.id.tvName)
            lnImage = itemView.findViewById(R.id.lnImage)
        }
    }
}
package com.yenvth.soilDetectionApp.map

import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.models.SoilModel
import java.util.ArrayList
import androidx.recyclerview.widget.RecyclerView
import android.widget.TextView
import android.widget.LinearLayout
import java.util.Locale
import android.content.Context
import android.view.*
import com.yenvth.soilDetectionApp.utils.Constant

class MapAdapter(
    private val mContext: Context,
    private val dataList: ArrayList<SoilModel>?,
    private val listener: OnSoilMapItemClickListener?
) : RecyclerView.Adapter<MapAdapter.MyHolder>() {
    interface OnSoilMapItemClickListener {
        fun onSoilMapClickListener(soilModel: SoilModel)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_soil_map, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        val soilModel = dataList!![pos]
        holder.itemView.setOnClickListener { view: View? ->
            listener?.onSoilMapClickListener(
                soilModel
            )
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
        return dataList!!.size
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
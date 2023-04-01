package com.yenvth.soilDetectionApp.diction

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.models.SoilModel
import com.yenvth.soilDetectionApp.utils.ImageUtils

class SoilAdapter(
    private val mContext: Context,
    private val onClick: (model: SoilModel) -> Unit,
) : RecyclerView.Adapter<SoilAdapter.MyHolder>() {
    private var dataList = emptyList<SoilModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<SoilModel>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_search_soil, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        val soilModel = dataList[pos]
        holder.itemView.setOnClickListener { onClick(soilModel) }
        holder.tvName.text = soilModel.nameVi
        holder.tvDes.text = soilModel.description
        holder.tv_pH.text = "pH: " + 10
        holder.tv_humidity.text = "Độ ẩm: " + 20
        holder.tv_n2o.text = "N2O: " + 30
        ImageUtils.setImage(mContext, soilModel.url, holder.image, holder.progressBar)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvDes: TextView
        val tv_pH: TextView
        val tv_humidity: TextView
        val tv_n2o: TextView
        val image: RoundedImageView
        val progressBar: ProgressBar

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvDes = itemView.findViewById(R.id.tvDes)
            tv_pH = itemView.findViewById(R.id.tv_pH)
            tv_humidity = itemView.findViewById(R.id.tv_humidity)
            tv_n2o = itemView.findViewById(R.id.tv_n2o)
            image = itemView.findViewById(R.id.image)
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }
}
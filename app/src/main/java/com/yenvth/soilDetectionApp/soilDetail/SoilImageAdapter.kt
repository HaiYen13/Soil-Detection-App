package com.yenvth.soilDetectionApp.soilDetail

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import com.makeramen.roundedimageview.RoundedImageView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.models.LabelModel
import com.yenvth.soilDetectionApp.utils.ImageUtils

class SoilImageAdapter(
    private val mContext: Context,
    private val listener: OnSoilImageClickListener?
) : RecyclerView.Adapter<SoilImageAdapter.MyHolder>() {
    private var dataList = emptyList<LabelModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<LabelModel>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    interface OnSoilImageClickListener {
        fun onSoilImageClick(url: String?)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_image_show, parent, false)
        return MyHolder(v)
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        ImageUtils.setImage(mContext, dataList[pos].url, holder.image, holder.progressBar)
        holder.image.setOnClickListener { listener?.onSoilImageClick(dataList[pos].url) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val image: RoundedImageView
        val progressBar: ProgressBar

        init {
            image = itemView.findViewById(R.id.image)
            progressBar = itemView.findViewById(R.id.progressBar)
        }
    }
}
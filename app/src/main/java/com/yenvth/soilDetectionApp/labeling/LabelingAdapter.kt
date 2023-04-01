package com.yenvth.soilDetectionApp.labeling

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.card.MaterialCardView
import com.makeramen.roundedimageview.RoundedImageView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.extension.toDate
import com.yenvth.soilDetectionApp.models.LabelModel

class LabelingAdapter(
    private val mContext: Context,
    private val listener: OnLabelItemClickListener
) : RecyclerView.Adapter<LabelingAdapter.MyHolder>() {
    private var dataList = emptyList<LabelModel>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(list: List<LabelModel>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    interface OnLabelItemClickListener {
        fun onLabelClickListener(labelingModel: LabelModel)
        fun onDeleteLabel(labelId: Int)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val v = LayoutInflater.from(mContext).inflate(R.layout.item_label, parent, false)
        return MyHolder(v)
    }

    override fun getItemViewType(position: Int): Int {
        return position % 2
    }

    override fun onBindViewHolder(holder: MyHolder, pos: Int) {
        if (getItemViewType(pos) == 1) {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#80cccccc"))
        } else {
            holder.cardView.setCardBackgroundColor(Color.parseColor("#ffffff"))
        }
        val labelingModel = dataList[pos]
        holder.itemView.setOnClickListener {
            listener.onLabelClickListener(labelingModel)
        }
        holder.tvName.text = labelingModel.labelName
        holder.tvDate.text = labelingModel.timestamp.toDate()
        if (!TextUtils.isEmpty(labelingModel.url)) {
            Glide.with(mContext).load(labelingModel.url)
                .listener(object : RequestListener<Drawable?> {
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any,
                        target: Target<Drawable?>,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.progressBar.visibility = View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any,
                        target: Target<Drawable?>,
                        dataSource: DataSource,
                        isFirstResource: Boolean
                    ): Boolean {
                        holder.progressBar.visibility = View.GONE
                        return false
                    }
                }).into(holder.image)
        }
        holder.btnDelete.setOnClickListener { listener.onDeleteLabel(labelingModel.labelId) }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class MyHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView
        val tvDate: TextView
        val image: RoundedImageView
        val progressBar: ProgressBar
        val cardView: MaterialCardView
        val btnDelete: ImageView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvDate = itemView.findViewById(R.id.tvDate)
            image = itemView.findViewById(R.id.image)
            progressBar = itemView.findViewById(R.id.progressBar)
            cardView = itemView.findViewById(R.id.cardView)
            btnDelete = itemView.findViewById(R.id.btnDelete)
        }
    }
}
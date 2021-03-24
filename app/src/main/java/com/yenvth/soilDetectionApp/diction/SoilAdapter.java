package com.yenvth.soilDetectionApp.diction;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.SoilModel;

import java.util.ArrayList;

public class SoilAdapter extends RecyclerView.Adapter<SoilAdapter.MyHolder> {
    private Context mContext;
    private ArrayList<SoilModel> dataList;
    private OnSoilItemClickListener listener;

    public SoilAdapter(Context mContext, ArrayList<SoilModel> dataList, OnSoilItemClickListener listener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.listener = listener;
    }

    public interface OnSoilItemClickListener {
        void onSoilClickListener(SoilModel soilModel);
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.item_search_soil, parent, false);
        return new MyHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int pos) {
        SoilModel soilModel = dataList.get(pos);
        holder.itemView.setOnClickListener(view -> {
            if (listener != null) {
                listener.onSoilClickListener(soilModel);
            }
        });
        holder.tvName.setText(soilModel.getSoil_name());
        holder.tvDes.setText(soilModel.getDescription());
        holder.tv_pH.setText("pH: " + soilModel.getpH());
        holder.tv_humidity.setText("Độ ẩm: " + soilModel.getHumidity());
        holder.tv_n2o.setText("N2O: " + soilModel.getN2o());
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class MyHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private TextView tvDes;
        private TextView tv_pH;
        private TextView tv_humidity;
        private TextView tv_n2o;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvName);
            tvDes = itemView.findViewById(R.id.tvDes);
            tv_pH = itemView.findViewById(R.id.tv_pH);
            tv_humidity = itemView.findViewById(R.id.tv_humidity);
            tv_n2o = itemView.findViewById(R.id.tv_n2o);
        }
    }
}

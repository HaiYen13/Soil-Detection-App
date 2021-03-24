package com.yenvth.soilDetectionApp.soilDetail;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.SoilModel;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoilDetailActivity extends AppCompatActivity implements View.OnClickListener, SoilDetailView {

    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    private SoilDetailPresenterImpl<SoilDetailView> presenter;
    private int soil_id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_soil_detail);
        ButterKnife.bind(this);
        presenter = new SoilDetailPresenterImpl<>(this, this);
        init();
        action();
    }

    private void init() {
//        tvToolbar.setText("Chi tiết mẫu đất");
        if (getIntent().getExtras() != null) {
            soil_id = getIntent().getIntExtra("soil_id", 0);
            if (soil_id > 0) {
                presenter.getSoilDetail(soil_id);
            }
        }
    }

    private void action() {
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onGetSoilDetails(SoilModel soilModel) {
        tvToolbar.setText(soilModel.getSoil_name());
    }
}

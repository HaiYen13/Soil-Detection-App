package com.yenvth.soilDetectionApp.detection;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.yenvth.soilDetectionApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetectionActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        action();
    }

    private void init() {
        tvToolbar.setText("Nhận dạng mẫu đất");
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
}

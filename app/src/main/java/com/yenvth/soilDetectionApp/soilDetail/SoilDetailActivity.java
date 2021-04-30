package com.yenvth.soilDetectionApp.soilDetail;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.speech.tts.TextToSpeech;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.detection.DetectionActivity;
import com.yenvth.soilDetectionApp.map.MapActivity;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.utils.DateUtils;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SoilDetailActivity extends AppCompatActivity implements View.OnClickListener, SoilDetailView, SoilImageAdapter.OnSoilImageClickListener {

    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.tvSoilName)
    protected TextView tvSoilName;
    @BindView(R.id.tvNameEn)
    protected TextView tvNameEn;
    @BindView(R.id.tvUpdate)
    protected TextView tvUpdate;
    @BindView(R.id.btnSound)
    protected FloatingActionButton btnSound;
    @BindView(R.id.btnDetect)
    protected FloatingActionButton btnDetect;
    @BindView(R.id.btnMap)
    protected LinearLayout btnMap;
    @BindView(R.id.recycler_view_soil_images)
    protected RecyclerView recycler_view_soil_images;
    @BindView(R.id.tvDescription)
    protected TextView tvDescription;
    @BindView(R.id.lnImages)
    protected LinearLayout lnImages;

    private SoilDetailPresenterImpl<SoilDetailView> presenter;
    private int soil_id;
    private TextToSpeech tts;
    private SoilModel soilModel;
    private SoilImageAdapter mAdapter;

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

        tts = new TextToSpeech(SoilDetailActivity.this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.US);
                }
            }
        });
    }

    private void action() {
        btnBack.setOnClickListener(this);
        btnSound.setOnClickListener(this);
        btnDetect.setOnClickListener(this);
        btnMap.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnDetect:
                intent = new Intent(SoilDetailActivity.this, DetectionActivity.class);
                startActivity(intent);
                break;
            case R.id.btnSound:
                btnSound.setColorFilter(SoilDetailActivity.this.getResources().getColor(R.color.start_color));
                tts.speak(soilModel.getName_en(), TextToSpeech.QUEUE_FLUSH, null);
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        btnSound.setColorFilter(Color.parseColor("#000000"));
                    }
                }, 1000);
                break;
            case R.id.btnMap:
                intent = new Intent(SoilDetailActivity.this, MapActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void onPause() {
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onPause();
    }

    @Override
    public void onGetSoilDetails(SoilModel soilModel) {
        this.soilModel = soilModel;
        tvToolbar.setText(soilModel.getName_vi());
        tvSoilName.setText(soilModel.getName_vi());
        tvNameEn.setText(soilModel.getName_en());
        tvUpdate.setText("Ngày cập nhật: " + DateUtils.convertTimes(soilModel.getTimestamp(), "dd/MM/yyyy"));
        tvDescription.setText(soilModel.getDescription());

        ArrayList<String> list_image_soil = new ArrayList<>();
        if (!TextUtils.isEmpty(soilModel.getUrl())) {
            list_image_soil.add(soilModel.getUrl());
        }

        mAdapter = new SoilImageAdapter(this, list_image_soil, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        recycler_view_soil_images.setLayoutManager(layoutManager);
        recycler_view_soil_images.setItemAnimator(new DefaultItemAnimator());
        recycler_view_soil_images.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        if (list_image_soil.size() == 0) {
            lnImages.setVisibility(View.GONE);
        } else {
            lnImages.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onSoilImageClick(String url) {
        final Dialog dialog = new Dialog(SoilDetailActivity.this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.dialog_image_show);

        ImageView btnBack = dialog.findViewById(R.id.btnBack);
        ImageView imageView = dialog.findViewById(R.id.image);
        Glide.with(SoilDetailActivity.this).load(url).listener(new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                return false;
            }
        }).into(imageView);

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}

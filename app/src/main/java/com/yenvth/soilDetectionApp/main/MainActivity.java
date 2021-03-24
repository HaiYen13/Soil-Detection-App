package com.yenvth.soilDetectionApp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.card.MaterialCardView;
import com.google.android.material.navigation.NavigationView;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.base.BaseActivity;
import com.yenvth.soilDetectionApp.detection.DetectionActivity;
import com.yenvth.soilDetectionApp.diction.DictionActivity;
import com.yenvth.soilDetectionApp.history.HistoryActivity;
import com.yenvth.soilDetectionApp.labeling.LabelingActivity;
import com.yenvth.soilDetectionApp.login.LoginActivity;
import com.yenvth.soilDetectionApp.map.MapActivity;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @BindView(R.id.btnMenu)
    protected ImageView btnMenu;
    @BindView(R.id.btnDiction)
    protected MaterialCardView btnDiction;
    @BindView(R.id.btnDetect)
    protected MaterialCardView btnDetect;
    @BindView(R.id.btnMap)
    protected MaterialCardView btnMap;
    @BindView(R.id.btnHis)
    protected MaterialCardView btnHis;
    @BindView(R.id.btnLabeling)
    protected MaterialCardView btnLabeling;
    @BindView(R.id.btnSearch)
    protected LinearLayout btnSearch;
    @BindView(R.id.navigationView)
    protected NavigationView navigationView;
    @BindView(R.id.drawer)
    protected DrawerLayout drawer;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(MainActivity.this);
        init();
        action();
    }

    private void init() {
        sp = getSharedPreferences(Constant.PREFERENCE_NAME, MODE_PRIVATE);
    }

    private void action() {
        btnMenu.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnDiction.setOnClickListener(this);
        btnDetect.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnHis.setOnClickListener(this);
        btnLabeling.setOnClickListener(this);

        navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            Intent intent;
            switch (id) {
                case R.id.diction:
                    intent = new Intent(MainActivity.this, DictionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.map:
                    intent = new Intent(MainActivity.this, MapActivity.class);
                    startActivity(intent);
                    break;
                case R.id.history:
                    if (sp.getString("uid", "").equals("")) {
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(intent, Constant.LOGIN_HISTORY_REQUEST);
                        break;
                    }
                    intent = new Intent(MainActivity.this, HistoryActivity.class);
                    startActivity(intent);
                    break;
                case R.id.detect:
                    intent = new Intent(MainActivity.this, DetectionActivity.class);
                    startActivity(intent);
                    break;
                case R.id.labeling:
                    if (sp.getString("uid", "").equals("")) {
                        intent = new Intent(MainActivity.this, LoginActivity.class);
                        startActivityForResult(intent, Constant.LOGIN_LABEL_REQUEST);
                        break;
                    }
                    intent = new Intent(MainActivity.this, LabelingActivity.class);
                    startActivity(intent);
                    break;
            }
            return false;
        });
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.btnMenu:
                drawer.openDrawer(Gravity.LEFT);
                break;
            case R.id.btnDiction:
            case R.id.btnSearch:
                intent = new Intent(MainActivity.this, DictionActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDetect:
                intent = new Intent(MainActivity.this, DetectionActivity.class);
                startActivity(intent);
                break;
            case R.id.btnMap:
                intent = new Intent(MainActivity.this, MapActivity.class);
                startActivity(intent);
                break;
            case R.id.btnHis:
                if (sp.getString("uid", "").equals("")) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Constant.LOGIN_HISTORY_REQUEST);
                    break;
                }
                intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                break;
            case R.id.btnLabeling:
                if (sp.getString("uid", "").equals("")) {
                    intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivityForResult(intent, Constant.LOGIN_LABEL_REQUEST);
                    break;
                }
                intent = new Intent(MainActivity.this, LabelingActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.LOGIN_HISTORY_REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        } else if (requestCode == Constant.LOGIN_LABEL_REQUEST) {
            if (resultCode == RESULT_OK) {
                Intent intent = new Intent(MainActivity.this, LabelingActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        }
    }
}
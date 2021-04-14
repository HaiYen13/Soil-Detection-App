package com.yenvth.soilDetectionApp.main;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
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
import com.yenvth.soilDetectionApp.models.UserModel;
import com.yenvth.soilDetectionApp.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity implements View.OnClickListener, MainView {

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
    @BindView(R.id.btnLogin)
    protected TextView btnLogin;
    @BindView(R.id.imgUser)
    protected CircleImageView imgUser;
    @BindView(R.id.tvName)
    protected TextView tvName;

    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private MainPresenterImpl presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        ButterKnife.bind(MainActivity.this);
        presenter = new MainPresenterImpl(this, this);
        init();
        action();
    }



    private void init() {
        sp = getSharedPreferences(Constant.PREFERENCE_NAME, MODE_PRIVATE);
        editor = sp.edit();
    }

    private void action() {
        btnMenu.setOnClickListener(this);
        btnSearch.setOnClickListener(this);
        btnDiction.setOnClickListener(this);
        btnDetect.setOnClickListener(this);
        btnMap.setOnClickListener(this);
        btnHis.setOnClickListener(this);
        btnLabeling.setOnClickListener(this);
        btnLogin.setOnClickListener(this);

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
                case R.id.signout:
                    drawer.closeDrawers();
                    final SweetAlertDialog dialog = new SweetAlertDialog(MainActivity.this, SweetAlertDialog.WARNING_TYPE);
                    dialog.setContentText("Bạn chắc chắn đăng xuất chứ?")
                            .setConfirmText("Đồng ý")
                            .setCancelText("Hủy")
                            .showCancelButton(true)
                            .setConfirmClickListener(sweetAlertDialog -> {
                                editor.putString("uid", "");
                                editor.putString("name", "");
                                editor.apply();
                                dialog.cancel();
                                Intent intent1 = new Intent(MainActivity.this, LoginActivity.class);
                                intent1.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent1);
                            })
                            .setCancelClickListener(sweetAlertDialog -> dialog.cancel())
                            .show();
                    break;

            }
            return false;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (TextUtils.isEmpty(sp.getString("uid", ""))) {
            btnLogin.setVisibility(View.VISIBLE);
            imgUser.setVisibility(View.GONE);
            MenuItem menuItem = navigationView.getMenu().findItem(R.id.signout);
            menuItem.setVisible(false);

        } else {
            btnLogin.setVisibility(View.GONE);
            imgUser.setVisibility(View.VISIBLE);
            presenter.getInformationAfterLogin();
            MenuItem menuItem = navigationView.getMenu().findItem(R.id.signout);
            menuItem.setVisible(true);
        }
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
            case R.id.btnLogin:
                intent = new Intent(MainActivity.this, LoginActivity.class);
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

    @Override
    public void onGetInformationSuccess(UserModel userModel) {
        tvName.setText(userModel.getName());
    }
}
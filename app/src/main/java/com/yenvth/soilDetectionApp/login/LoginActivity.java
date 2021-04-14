package com.yenvth.soilDetectionApp.login;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yenvth.soilDetectionApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActivity";

    @BindView(R.id.btnLogin)
    protected TextView btnLogin;
    @BindView(R.id.btnBack)
    protected ImageView btnBack;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        //-------------------Phone Number Login---------------//
        btnLogin.setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this, PhoneLoginActivity.class);
            startActivity(intent);
        });

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });
    }
}

package com.yenvth.soilDetectionApp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.base.BaseActivity;
import com.yenvth.soilDetectionApp.main.MainActivity;
import com.yenvth.soilDetectionApp.utils.CommonUtils;

import java.util.Random;

public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginView {
    private EditText edPhone, edPassword;
    private TextView btnLogin;
    private ImageView btnShowPass;
    private boolean SHOW_PASS = false;
    private DatabaseReference mDatabase;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;
    private LoginPresenterImpl mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        init();
        action();
    }

    private void init() {
        sp = getSharedPreferences("auth", MODE_PRIVATE);
        editor = sp.edit();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mPresenter = new LoginPresenterImpl(this, this);

        if (sp.getString("uid", null) != null) {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        }

        edPhone = findViewById(R.id.edPhone);
        edPassword = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnShowPass = findViewById(R.id.btnShowPass);
    }

    private void action() {
        btnLogin.setOnClickListener(this);
        btnShowPass.setOnClickListener(this);

        edPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) {
                    btnShowPass.setVisibility(View.VISIBLE);
                } else {
                    btnShowPass.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void login() {

        if (edPhone.getText().toString().trim().isEmpty() || edPassword.getText().toString().trim().isEmpty()) {
            CommonUtils.showError(LoginActivity.this, "Số điện thoại hoặc mật khẩu không đúng.");
            return;
        }

        final String phone = edPhone.getText().toString().trim();
        final String password = edPassword.getText().toString().trim();

//        mPresenter.doLogin(phone, password);

        showLoading(this);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
            hideLoading();
            setResult(RESULT_OK);
            finish();
        }, new Random().nextInt(2000) + 1500);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnShowPass:
                SHOW_PASS = !SHOW_PASS;
                if (!SHOW_PASS) {
                    edPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    edPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
                edPassword.setSelection(edPassword.getText().toString().length());
                break;
            case R.id.btnLogin:
                login();
                break;
        }
    }

    @Override
    public void onLoginSuccess(String uid) {
        editor.putString("uid", uid);
        editor.commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}

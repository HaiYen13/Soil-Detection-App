package com.yenvth.soilDetectionApp.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.yenvth.soilDetectionApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PhoneLoginActivity extends AppCompatActivity {

    private static final String TAG = "PhoneLoginActivity";
    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.edPhoneNumber)
    protected EditText edPhoneNumber;
    @BindView(R.id.btnContinue)
    protected TextView btnContinue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_login);
        getSupportActionBar().hide();
        ButterKnife.bind(this);

        btnBack.setOnClickListener(view -> onBackPressed());
        btnContinue.setOnClickListener(view -> {
            if (!validatePhoneNumber()) {
                return;
            }
            String phoneNumber = edPhoneNumber.getText().toString().trim();
            if (phoneNumber.charAt(0) == '0') {
                phoneNumber = phoneNumber.substring(1);
            }
            Intent intent = new Intent(PhoneLoginActivity.this, PhoneVerifyActivity.class);
            intent.putExtra("phone_number", "+84" + phoneNumber);
            startActivity(intent);
        });

    }

    private boolean validatePhoneNumber() {
        String phoneNumber = edPhoneNumber.getText().toString();
        if (TextUtils.isEmpty(phoneNumber)) {
            edPhoneNumber.setError("Số điện thoại không hợp lệ.");
            return false;
        }
        return true;
    }


}

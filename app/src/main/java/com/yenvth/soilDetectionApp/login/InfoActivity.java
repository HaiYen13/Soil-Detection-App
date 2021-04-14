package com.yenvth.soilDetectionApp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.main.MainActivity;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InfoActivity extends AppCompatActivity {
    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.edName)
    protected EditText edName;
    @BindView(R.id.btnContinue)
    protected TextView btnContinue;

    private DatabaseReference mDatabase;
    private static String uID;
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        init();
        action();
    }

    private void init() {
        uID = getIntent().getStringExtra("uid");
        sp = getSharedPreferences(Constant.PREFERENCE_NAME, MODE_PRIVATE);
        editor = sp.edit();

        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    private void action() {
        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edName.getText().toString().trim();

                if (TextUtils.isEmpty(name)) {
                    CommonUtils.showError(InfoActivity.this, "Vui lòng nhập tên");
                    return;
                }

                mDatabase.child("users").child(uID).child("name").setValue(name)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                editor.putString("uid", uID);
                                editor.putString("name", name);
                                editor.apply();
                                Intent intent = new Intent(InfoActivity.this, MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                CommonUtils.showError(InfoActivity.this, "Không thành công");
                            }
                        });
            }
        });
    }
}

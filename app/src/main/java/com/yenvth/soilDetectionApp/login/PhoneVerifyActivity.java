package com.yenvth.soilDetectionApp.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alimuzaffar.lib.pin.PinEntryEditText;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.base.BaseActivity;
import com.yenvth.soilDetectionApp.main.MainActivity;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.widget.Toast.LENGTH_SHORT;

public class PhoneVerifyActivity extends BaseActivity {

    private static final String TAG = "PhoneVerifyActivity";
    private static String uID;
    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.tvPhoneNumber)
    protected TextView tvPhoneNumber;
    @BindView(R.id.tvResend)
    protected TextView tvResend;
    @BindView(R.id.edPinEntry)
    protected PinEntryEditText edPinEntry;
    @BindView(R.id.btnContinue)
    protected TextView btnContinue;

    private SweetAlertDialog pDialog;
    private SweetAlertDialog dialog;
    private DatabaseReference mDatabase;
    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String PHONE_NUMBER = "";
    private SharedPreferences sp;
    private SharedPreferences.Editor editor;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verify);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        PHONE_NUMBER = getIntent().getExtras().getString("phone_number");

        sp = getSharedPreferences(Constant.PREFERENCE_NAME, MODE_PRIVATE);
        editor = sp.edit();

        dialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.SUCCESS_TYPE);
        dialog.setTitleText("Đã gửi mã")
                .hideConfirmButton()
                .show();
        tvPhoneNumber.setText(PHONE_NUMBER);
        btnBack.setOnClickListener(v -> onBackPressed());

        tvResend.setOnClickListener(v -> {
            resendVerificationCode(PHONE_NUMBER, mResendToken);
            dialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.SUCCESS_TYPE);
            dialog.setTitleText("Gửi lại mã thành công")
                    .hideConfirmButton()
                    .show();
        });

        btnContinue.setOnClickListener(v -> verifyPhoneNumberWithCode(mVerificationId, edPinEntry.getText().toString()));

        // [START initialize_auth]
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        // [END initialize_auth]
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                mVerificationInProgress = false;
                // [END_EXCLUDE]
                pDialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                pDialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
                pDialog.setTitleText("Đang tải");
                pDialog.setCancelable(false);
                pDialog.show();
                signInWithPhoneAuthCredential(credential);
            }

            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);
//                Toast.makeText(getApplication(), "Verify Failed!!", Toast.LENGTH_SHORT).show();
                // [START_EXCLUDE silent]
//                mVerificationInProgress = false;
                // [END_EXCLUDE]

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    // [START_EXCLUDE]
                    edPinEntry.setError("Số điện thoại không hợp lệ.");
                    // [END_EXCLUDE]
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Hết lượng yêu cầu.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);

//                Toast.makeText(getApplication(), "Code sent!!", Toast.LENGTH_SHORT).show();
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;

            }
        };
        // [END phone_auth_callbacks]

        startPhoneNumberVerification(PHONE_NUMBER);
    }

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks);        // OnVerificationStateChangedCallbacks
        // [END start_phone_auth]

        mVerificationInProgress = true;
    }

    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            // [END verify_with_code]
            signInWithPhoneAuthCredential(credential);
        } catch (IllegalArgumentException e) {
            Toast.makeText(this, "Mã xác nhận không đúng.", LENGTH_SHORT).show();
        }

    }

    // [START resend_verification]
    private void resendVerificationCode(String phoneNumber, PhoneAuthProvider.ForceResendingToken token) {
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                phoneNumber,        // Phone number to verify
                60,                 // Timeout duration
                TimeUnit.SECONDS,   // Unit of timeout
                this,               // Activity (for callback binding)
                mCallbacks,         // OnVerificationStateChangedCallbacks
                token);             // ForceResendingToken from callbacks
    }
    // [END resend_verification]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            dialog = new SweetAlertDialog(PhoneVerifyActivity.this, SweetAlertDialog.SUCCESS_TYPE);
                            dialog.setTitleText("Đăng nhập thành công")
                                    .hideConfirmButton()
                                    .show();
                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    dialog.dismiss();
                                }
                            }, 500);
                            final FirebaseUser user = task.getResult().getUser();

                            ValueEventListener eventListener = new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                    uID = user.getUid();
                                    boolean contain = false;
                                    String name = null;
                                    for (DataSnapshot snapshot : dataSnapshot.child("users").getChildren()) {
                                        if (uID.equals(snapshot.getKey())) {
                                            contain = true;
                                            name = (String) snapshot.child("name").getValue();
                                            break;
                                        }
                                    }
                                    if (contain) {
                                        editor.putString("uid", uID);
                                        editor.putString("name", name);
                                        editor.apply();
                                        Intent intent = new Intent(PhoneVerifyActivity.this, MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    } else {
                                        Intent intent = new Intent(PhoneVerifyActivity.this, InfoActivity.class);
                                        intent.putExtra("uid", uID);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {
                                    CommonUtils.showError(PhoneVerifyActivity.this, "Đăng nhập thất bại");
                                }
                            };
                            mDatabase.addListenerForSingleValueEvent(eventListener);

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
//                            Toast.makeText(getApplication(), "Signin Failed!!", Toast.LENGTH_SHORT).show();

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                CommonUtils.showError(PhoneVerifyActivity.this, "Mã xác nhận không đúng.");
                            }
                        }
                    }
                });
    }
    // [END sign_in_with_phone]


    @Override
    protected void onStop() {
        super.onStop();
        if (pDialog != null)
            pDialog.dismiss();
        if (dialog != null)
            dialog.dismiss();
    }
}

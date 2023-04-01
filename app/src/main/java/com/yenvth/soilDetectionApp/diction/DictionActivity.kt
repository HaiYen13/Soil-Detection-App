package com.yenvth.soilDetectionApp.diction;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.scwang.wave.MultiWaveHeader;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.HistoryModel;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.soilDetail.SoilDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionActivity extends AppCompatActivity implements DictionView,
        View.OnClickListener,
        SoilAdapter.OnSoilItemClickListener {

    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.recycler_view_soil)
    protected RecyclerView recycler_view_soil;
    @BindView(R.id.edSearch)
    protected AutoCompleteTextView edSearch;
    @BindView(R.id.btnSearch)
    protected ImageView btnSearch;
    @BindView(R.id.btnVoice)
    protected ImageView btnVoice;

    private SoilAdapter mAdapter;
    private ArrayList<SoilModel> dataList;
    private DictionPresenterImpl<DictionView> mPresenter;
    private boolean isDeletable;
    private MultiWaveHeader wave1, wave2;
    private SpeechRecognizer mSpeechRecongizer;
    private Intent mSpeechRecognizerIntent;
    private String LANG = "vi";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diction);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        mPresenter = new DictionPresenterImpl<>(DictionActivity.this, this);
        init();
        action();
    }

    private void init() {
        mSpeechRecongizer = SpeechRecognizer.createSpeechRecognizer(this);
        mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        mPresenter.getListSoil("");
    }

    private void action() {
        btnBack.setOnClickListener(this);
        btnSearch.setOnClickListener(this);

        edSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    edSearch.setBackgroundResource(R.drawable.corner_search_edittext_select);
                } else {
                    edSearch.setBackgroundResource(R.drawable.corner_search_edittext_unselect);
                }
            }
        });

        edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                int length = edSearch.getText().toString().trim().length();

                if (length == 0) {
                    isDeletable = false;
                    btnVoice.setImageResource(R.drawable.micro);
                } else {
                    isDeletable = true;
                    btnVoice.setImageResource(R.drawable.delete);
                }
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                       mPresenter.getListSoil(s.toString());
                    }
                }, 100);
            }
        });

        btnVoice.setOnClickListener(v -> {
            if (isDeletable) {
                edSearch.setText("");
            } else {
//                speechToText();
            }
        });
    }

    private void checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO)
                    == PackageManager.PERMISSION_GRANTED) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:" + getPackageName()));
                startActivity(intent);
                finish();
            }
        }
    }

    private void speechToText() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(R.layout.dialog_speech);
        final ImageView btnVoice = dialog.findViewById(R.id.btnVoice);
        final TextView tvDetect = dialog.findViewById(R.id.tvDetect);

        wave1 = dialog.findViewById(R.id.wave1);
        wave2 = dialog.findViewById(R.id.wave2);

        wave1.setStartColor(Color.parseColor("#ffffff"));
        wave1.setCloseColor(Color.parseColor("#ffffff"));
        wave1.setColorAlpha(.4f);
        wave1.setVelocity(2);
        wave1.setProgress(0.8f);
        wave1.stop();
        wave1.setGradientAngle(45);

        wave2.setStartColor(Color.parseColor("#ffffff"));
        wave2.setCloseColor(Color.parseColor("#ffffff"));
        wave2.setColorAlpha(.4f);
        wave2.setVelocity(2);
        wave2.setProgress(0.8f);
        wave2.stop();
        wave2.setGradientAngle(45);
        checkPermission();
        btnVoice.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    wave1.setVisibility(View.GONE);
                    wave2.setVisibility(View.GONE);
                    wave1.stop();
                    wave2.stop();
                    mSpeechRecongizer.stopListening();
                    tvDetect.setHint("You will see the input here");
                    break;
                case MotionEvent.ACTION_DOWN:
                    tvDetect.setText("");
                    tvDetect.setHint("Listening ... ");
                    wave1.setVisibility(View.VISIBLE);
                    wave2.setVisibility(View.VISIBLE);
                    wave1.start();
                    wave2.start();
                    mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, LANG);
                    mSpeechRecongizer.startListening(mSpeechRecognizerIntent);
                    break;
            }
            return false;
        });
        dialog.show();

        mSpeechRecongizer.setRecognitionListener(new RecognitionListener() {
            @Override
            public void onReadyForSpeech(Bundle params) {

            }

            @Override
            public void onBeginningOfSpeech() {

            }

            @Override
            public void onRmsChanged(float rmsdB) {

            }

            @Override
            public void onBufferReceived(byte[] buffer) {

            }

            @Override
            public void onEndOfSpeech() {

            }

            @Override
            public void onError(int error) {

            }

            @Override
            public void onResults(Bundle results) {
                final ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
                if (matches != null) {
                    wave1.setVisibility(View.GONE);
                    wave2.setVisibility(View.GONE);

//                    System.out.println(matches.get(0));
                    tvDetect.setText(matches.get(0));
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dialog.dismiss();
                            edSearch.setText(matches.get(0));
                        }
                    }, 1000);

                }
            }

            @Override
            public void onPartialResults(Bundle partialResults) {

            }

            @Override
            public void onEvent(int eventType, Bundle params) {
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnSearch:
                String query = edSearch.getText().toString().trim();
                if (!TextUtils.isEmpty(query)) {
                    mPresenter.getListSoil(query);
                }
                break;
        }
    }

    @Override
    public void onGetListSoilSuccess(ArrayList<SoilModel> soilModels) {
        mAdapter = new SoilAdapter(this, soilModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view_soil.setLayoutManager(layoutManager);
        recycler_view_soil.setItemAnimator(new DefaultItemAnimator());
        recycler_view_soil.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSaveHistorySuccess() {

    }

    @Override
    public void onSoilClickListener(SoilModel soilModel) {
        mPresenter.saveSearchHistory(new HistoryModel(soilModel.getSoilId(), soilModel.getNameVi(),System.currentTimeMillis()));
        Intent intent = new Intent(this, SoilDetailActivity.class);
        intent.putExtra("soil_id", soilModel.getSoilId());
        startActivity(intent);
    }
}

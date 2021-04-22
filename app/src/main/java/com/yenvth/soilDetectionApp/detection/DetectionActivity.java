package com.yenvth.soilDetectionApp.detection;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.Surface;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.cnnModel.classification.ClassifierActivity;
import com.yenvth.soilDetectionApp.cnnModel.tflite.Classifier;
import com.yenvth.soilDetectionApp.models.DetectionModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.io.IOException;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetectionActivity extends AppCompatActivity implements View.OnClickListener, DetectionView {

    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.btnCam)
    protected LinearLayout btnCam;
    @BindView(R.id.btnGallery)
    protected LinearLayout btnGallery;
    @BindView(R.id.image)
    protected ImageView image;
    @BindView(R.id.btnDetect)
    protected TextView btnDetect;
    @BindView(R.id.tvResult)
    protected TextView tvResult;
    @BindView(R.id.lnResult)
    protected LinearLayout lnResult;
    @BindView(R.id.expandLayout)
    protected ExpandableLayout expandLayout;
    @BindView(R.id.tvMessage)
    protected TextView tvMessage;
    @BindView(R.id.btnNo)
    protected TextView btnNo;
    @BindView(R.id.btnYes)
    protected TextView btnYes;

    private Bitmap bitmap;
    private Uri uri;
    private DetectionPresenterImpl<DetectionView> presenter;
    private Classifier classifier;
    private Integer sensorOrientation;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detection);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        presenter = new DetectionPresenterImpl<>(this, this);
        init();
        action();
    }

    private void init() {
        tvToolbar.setText("Nhận dạng mẫu đất");
    }

    private void action() {
        btnBack.setOnClickListener(this);
        btnCam.setOnClickListener(this);
        btnGallery.setOnClickListener(this);
        btnDetect.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnCam:
                checkCameraPermission();
                break;
            case R.id.btnGallery:
                CropImage.startPickImageActivity(DetectionActivity.this);
                break;
            case R.id.btnDetect:
                if (uri == null) {
                    CommonUtils.showError(this, "Vui lòng chọn hình ảnh.");
                    break;
                }
                presenter.saveImageToFirebaseStorage(uri);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
                Uri uriImage = CropImage.getPickImageResultUri(this, data);
                if (CropImage.isReadExternalStoragePermissionsRequired(this, uriImage)) {
                    uri = uriImage;
                } else {
                    startCrop(uriImage);
                }
            }
            if (requestCode == 2) {
                startCrop(uri);
            }

            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                uri = result.getUri();
                image.setImageURI(result.getUri());
                image.setBackground(null);
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                } catch (Exception e) {
                    //handle exception
                }
                btnDetect.setVisibility(View.VISIBLE);
                lnResult.setVisibility(View.GONE);
            }
        }
    }

    //Todo: Get URI from Gallery
    private void startCrop(Uri uri) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON).setMultiTouchEnabled(true).start(this);
    }

    @Override
    public void onSaveImageSuccess(String url) {
        try {
            if (bitmap != null) {
                if (classifier == null) {
                    classifier = Classifier.create(this, Classifier.Model.QUANTIZED_EFFICIENTNET, Classifier.Device.CPU, 1);
                }
                sensorOrientation = 90 - getScreenOrientation();
                List<Classifier.Recognition> results = classifier.recognizeImage(bitmap, sensorOrientation);
                if (results != null && results.size() >= 3) {
                    Classifier.Recognition recognition = results.get(0);
                    if (recognition != null) {
                        if (recognition.getTitle() != null) {
                            lnResult.setVisibility(View.VISIBLE);
                            tvResult.setText(recognition.getTitle().toUpperCase());
                            final Handler handler = new Handler();
                            handler.postDelayed(() -> {
                                expandLayout.setExpanded(true);
                                tvMessage.setText("Đây có phải là " + recognition.getTitle().toUpperCase() + " không?");
                            }, new Random().nextInt(2000));
                            btnDetect.setVisibility(View.GONE);
                        }
                    }
                } else {
                    expandLayout.setExpanded(false);
                    lnResult.setVisibility(View.VISIBLE);
                    tvResult.setText("Không thể nhận dạng");
                    btnDetect.setVisibility(View.VISIBLE);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
//        if (!TextUtils.isEmpty(url)) {
////            presenter.detectSoil("https://images.theconversation.com/files/275002/original/file-20190516-69195-1yg53ff.jpg?ixlib=rb-1.1.0&q=45&auto=format&w=1200&h=1200.0&fit=crop");
//        }
        btnYes.setOnClickListener(view -> {
            expandLayout.setExpanded(false);
            presenter.saveLabelDetection(new DetectionModel(url, tvResult.getText().toString().trim(), System.currentTimeMillis()));
            CommonUtils.showMessage(DetectionActivity.this, "Cảm ơn vì đóng góp của bạn");
        });

        btnNo.setOnClickListener(view -> {
            expandLayout.setExpanded(false);
            presenter.saveLabelDetection(new DetectionModel(url, "Chưa xác nhận", System.currentTimeMillis()));
            CommonUtils.showMessage(DetectionActivity.this, "Cảm ơn vì đóng góp của bạn");
        });
    }

    private void checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
//            ContentValues values = new ContentValues();
//            values.put(MediaStore.Images.Media.TITLE, "NewPic");
//            values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Detect");
//            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            startActivityForResult(cameraIntent, 2);
            Intent intent = new Intent(DetectionActivity.this, ClassifierActivity.class);
            startActivity(intent);
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 44);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 44) {
            if (grantResults.length > 0){
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.TITLE, "NewPic");
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Detect");
                uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(cameraIntent, 2);
            }
        }
    }

    protected int getScreenOrientation() {
        switch (getWindowManager().getDefaultDisplay().getRotation()) {
            case Surface.ROTATION_270:
                return 270;
            case Surface.ROTATION_180:
                return 180;
            case Surface.ROTATION_90:
                return 90;
            default:
                return 0;
        }
    }
}

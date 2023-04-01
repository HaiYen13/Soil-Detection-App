package com.yenvth.soilDetectionApp.detection

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.view.Surface
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.cnnModel.classification.ClassifierActivity
import com.yenvth.soilDetectionApp.cnnModel.tflite.Classifier
import com.yenvth.soilDetectionApp.databinding.ActivityDetectionBinding
import com.yenvth.soilDetectionApp.utils.CommonUtils
import java.io.IOException
import java.util.*

class DetectionActivity : AppCompatActivity(), View.OnClickListener, DetectionView {

    private lateinit var binding: ActivityDetectionBinding
    private var bitmap: Bitmap? = null
    private var uri: Uri? = null
    private val screenOrientation: Int
        get() = when (windowManager.defaultDisplay.rotation) {
            Surface.ROTATION_270 -> 270
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_90 -> 90
            else -> 0
        }

    private val presenter: DetectionPresenterImpl<DetectionView> by lazy {
        DetectionPresenterImpl(this)
    }
    private val classifier: Classifier by lazy {
        Classifier.create(
            this,
            Classifier.Model.QUANTIZED_EFFICIENTNET,
            Classifier.Device.CPU,
            1
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        action()
    }

    private fun init() {
        binding.header.tvToolbar.text = getString(R.string.soil_detection)
    }

    private fun action() {
        binding.header.btnBack.setOnClickListener(this)
        binding.btnCam.setOnClickListener(this)
        binding.btnGallery.setOnClickListener(this)
        binding.btnDetect.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> onBackPressed()
            R.id.btnCam -> checkCameraPermission()
            R.id.btnGallery -> CropImage.startPickImageActivity(this@DetectionActivity)
            R.id.btnDetect -> {
                if (uri == null) {
                    CommonUtils.showSnackBar(this, getString(R.string.please_choose_image), false)
                    return
                }
                detectSoil()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK) {
            if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE) {
                val uriImage = CropImage.getPickImageResultUri(this, data)
                if (CropImage.isReadExternalStoragePermissionsRequired(this, uriImage)) {
                    uri = uriImage
                } else {
                    startCrop(uriImage)
                }
            }
            if (requestCode == 2) {
                startCrop(uri)
            }
            if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
                val result = CropImage.getActivityResult(data)
                uri = result.uri
                binding.image.setImageURI(result.uri)
                binding.image.background = null
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
                } catch (e: Exception) {
                    //handle exception
                }
                binding.btnDetect.visibility = View.VISIBLE
                binding.lnResult.visibility = View.GONE
            }
        }
    }

    //Todo: Get URI from Gallery
    private fun startCrop(uri: Uri?) {
        CropImage.activity(uri).setGuidelines(CropImageView.Guidelines.ON)
            .setMultiTouchEnabled(true).start(this)
    }

    @SuppressLint("SetTextI18n")
    private fun detectSoil() {
        try {
            val sensorOrientation = 90 - screenOrientation
            val results = classifier.recognizeImage(bitmap, sensorOrientation)
            if (results != null && results.size >= 3) {
                val recognition = results[0]
                if (recognition != null) {
                    if (recognition.title != null) {
                        binding.lnResult.visibility = View.VISIBLE
                        binding.tvResult.text = recognition.title.toUpperCase(Locale.getDefault())
                        Handler().postDelayed({
                            binding.expandLayout.expand()
                            binding.tvMessage.text =
                                "${getString(R.string.detect_asking_1)}${
                                    recognition.title.toUpperCase(
                                        Locale.getDefault()
                                    )
                                }${
                                    getString(
                                        R.string.detect_asking_2
                                    )
                                }"
                        }, Random().nextInt(2000).toLong())
                        binding.btnDetect.visibility = View.GONE
                    }
                }
            } else {
                binding.expandLayout.collapse()
                binding.lnResult.visibility = View.VISIBLE
                binding.tvResult.text = getString(R.string.can_not_detect)
                binding.btnDetect.visibility = View.VISIBLE
            }

            binding.btnYes.setOnClickListener {
                binding.expandLayout.collapse()
                uri?.let { selectUri ->
                    presenter.saveImageToFirebaseStorage(
                        selectUri,
                        binding.tvResult.text.toString().trim(),
                        true
                    )
                }
                CommonUtils.showSnackBar(
                    this@DetectionActivity,
                    getString(R.string.thanks_for_contribution)
                )
            }
            binding.btnNo.setOnClickListener {
                binding.expandLayout.collapse()
                uri?.let { selectUri ->
                    presenter.saveImageToFirebaseStorage(
                        selectUri,
                        binding.tvResult.text.toString().trim(),
                        false
                    )
                }
                CommonUtils.showSnackBar(
                    this@DetectionActivity,
                    getString(R.string.thanks_for_contribution)
                )
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

    }

    private fun checkCameraPermission() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED &&
            ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED
        ) {
//            ContentValues values = new ContentValues();
//            values.put(MediaStore.Images.Media.TITLE, "NewPic");
//            values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Detect");
//            uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//            Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//            startActivityForResult(cameraIntent, 2);
            val intent = Intent(this@DetectionActivity, ClassifierActivity::class.java)
            startActivity(intent)
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                44
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 44) {
            if (grantResults.isNotEmpty()) {
                val values = ContentValues()
                values.put(MediaStore.Images.Media.TITLE, "NewPic")
                values.put(MediaStore.Images.Media.DESCRIPTION, "Image To Detect")
                uri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
                startActivityForResult(cameraIntent, 2)
            }
        }
    }
}
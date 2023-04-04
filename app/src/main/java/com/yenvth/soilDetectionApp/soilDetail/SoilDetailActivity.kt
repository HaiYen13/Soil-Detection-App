package com.yenvth.soilDetectionApp.soilDetail

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.databinding.ActivitySoilDetailBinding
import com.yenvth.soilDetectionApp.detection.DetectionActivity
import com.yenvth.soilDetectionApp.extension.toDate
import com.yenvth.soilDetectionApp.map.MapActivity
import com.yenvth.soilDetectionApp.soilDetail.SoilImageAdapter.OnSoilImageClickListener
import com.yenvth.soilDetectionApp.utils.ImageUtils

class SoilDetailActivity : AppCompatActivity(), View.OnClickListener,
    OnSoilImageClickListener {
    private lateinit var binding: ActivitySoilDetailBinding
    private val viewModel by viewModels<SoilDetailViewModel>()

    private val mAdapter: SoilImageAdapter by lazy {
        SoilImageAdapter(this, this)
    }
    private var soilId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySoilDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        action()
        setupObserve()
    }

    private fun init() {
//        tvToolbar.setText("Chi tiết mẫu đất");
        soilId = intent.getIntExtra("soilId", 0)
        if (soilId != 0) {
            viewModel.getSoilDetail(soilId)
        }

        val layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)
        binding.recyclerViewSoilImages.layoutManager = layoutManager
        binding.recyclerViewSoilImages.adapter = mAdapter
    }

    private fun action() {
        binding.header.btnBack.setOnClickListener(this)
        binding.btnSound.setOnClickListener(this)
        binding.btnDetect.setOnClickListener(this)
        binding.btnMap.setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    private fun setupObserve() {
        viewModel.soil.observe(this) { soilModel ->
            binding.header.tvToolbar.text = soilModel.nameVi
            binding.tvSoilName.text = soilModel.nameVi
            binding.tvNameEn.text = soilModel.nameEn
            binding.tvUpdate.text =
                "${getString(R.string.update_date)} ${soilModel.timestamp.toDate()}"
            binding.tvDescription.text = soilModel.description
            val listImageSoil = arrayListOf<String>()
            if (!soilModel.url.isNullOrEmpty()) {
                listImageSoil.add(soilModel.url!!)
            }
            if (listImageSoil.size == 0) {
                binding.lnImages.visibility = View.GONE
            } else {
                mAdapter.setData(listImageSoil)
                binding.lnImages.visibility = View.VISIBLE
            }
        }
    }

    override fun onClick(view: View) {
        val intent: Intent
        when (view.id) {
            R.id.btnBack -> onBackPressed()
            R.id.btnDetect -> {
                intent = Intent(this@SoilDetailActivity, DetectionActivity::class.java)
                startActivity(intent)
            }
            R.id.btnMap -> {
                intent = Intent(this@SoilDetailActivity, MapActivity::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onSoilImageClick(url: String?) {
        val dialog =
            Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen)
        dialog.setContentView(R.layout.dialog_image_show)
        val btnBack = dialog.findViewById<ImageView>(R.id.btnBack)
        val imageView = dialog.findViewById<ImageView>(R.id.image)
        ImageUtils.setImage(this, url, imageView)
        btnBack.setOnClickListener { dialog.dismiss() }
        dialog.show()
    }
}
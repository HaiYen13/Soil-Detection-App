package com.yenvth.soilDetectionApp.labeling

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.makeramen.roundedimageview.RoundedImageView
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.databinding.ActivityLabelingBinding
import com.yenvth.soilDetectionApp.labeling.LabelingAdapter.OnLabelItemClickListener
import com.yenvth.soilDetectionApp.models.LabelModel
import com.yenvth.soilDetectionApp.utils.CommonUtils

class LabelingActivity : AppCompatActivity(), View.OnClickListener,
    OnLabelItemClickListener {
    private val viewModel by viewModels<LabelingViewModel>()
    private lateinit var binding: ActivityLabelingBinding

    private var imageLayout: ConstraintLayout? = null
    private var btnAddImage: LinearLayout? = null
    private var imageView: RoundedImageView? = null
    private var uri: Uri? = null
    private var btnDel: ImageView? = null
    private val mAdapter: LabelingAdapter by lazy {
        LabelingAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLabelingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        action()
        setupObserve()
    }

    private fun init() {
        binding.header.tvToolbar.text = getString(R.string.my_labels)

        val layoutManager: LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerViewLabel.layoutManager = layoutManager
        binding.recyclerViewLabel.adapter = mAdapter

        viewModel.getLabels()
    }

    private fun action() {
        binding.header.btnBack.setOnClickListener(this)
        binding.btnAdd.setOnClickListener(this)
    }

    private fun setupObserve() {
        viewModel.labels.observe(this) { list ->
            mAdapter.setData(list)
        }

        viewModel.addStatus.observe(this) {
            CommonUtils.showSnackBar(this@LabelingActivity, getString(R.string.upload_successful))
            viewModel.getLabels()
        }

        viewModel.deleteStatus.observe(this) {
            CommonUtils.showSnackBar(this@LabelingActivity, getString(R.string.delete_successful))
            viewModel.getLabels()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> onBackPressed()
            R.id.btnAdd -> {
                val dialog = BottomSheetDialog(this)
                dialog.setContentView(R.layout.dialog_add_label)
                btnAddImage = dialog.findViewById(R.id.btnAddImage)
                imageLayout = dialog.findViewById(R.id.imageLayout)
                imageView = dialog.findViewById(R.id.image)
                btnDel = dialog.findViewById(R.id.btnDel)
                val edName = dialog.findViewById<EditText>(R.id.edName)
                val btnContinue = dialog.findViewById<TextView>(R.id.btnContinue)
                btnAddImage?.setOnClickListener {
                    val intent = Intent()
                    intent.type = "image/*"
                    intent.action = Intent.ACTION_GET_CONTENT
                    startActivityForResult(
                        Intent.createChooser(
                            intent,
                            getString(R.string.choose_an_image)
                        ), PICK_IMAGE
                    )
                }
                btnDel?.setOnClickListener {
                    uri = null
                    imageView?.setImageURI(null)
                    btnAddImage?.visibility = View.VISIBLE
                    imageLayout?.visibility = View.GONE
                }
                btnContinue?.setOnClickListener {
                    val label = edName?.text.toString().trim()
                    if (label.isBlank()) {
                        CommonUtils.showSnackBar(
                            dialog.currentFocus,
                            "Vui lòng nhập tên nhãn",
                            false
                        )

                        return@setOnClickListener
                    }
                    if (uri == null) {
                        CommonUtils.showSnackBar(
                            dialog.currentFocus,
                            "Vui lòng chọn ảnh đất",
                            false
                        )

                        return@setOnClickListener
                    }
                    viewModel.addLabel(this, label, uri!!)
                    dialog.cancel()
                }
                dialog.show()
            }
        }
    }

    override fun onLabelClickListener(labelingModel: LabelModel) {}

    override fun onDeleteLabel(labelId: Int) {
        val dialog = SweetAlertDialog(this@LabelingActivity, SweetAlertDialog.WARNING_TYPE)
        dialog.setContentText(getString(R.string.are_you_sure_to_delete_this_label))
            .setConfirmText(getString(R.string.ok))
            .setCancelText(getString(R.string.cancel))
            .showCancelButton(true)
            .setConfirmClickListener {
                dialog.cancel()
                viewModel.deleteLabel(labelId)
            }
            .setCancelClickListener { dialog.cancel() }
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            uri = data?.data
            imageView?.setImageURI(uri)
            btnAddImage?.visibility = View.GONE
            imageLayout?.visibility = View.VISIBLE
        }
    }

    companion object {
        private const val PICK_IMAGE = 1
    }
}
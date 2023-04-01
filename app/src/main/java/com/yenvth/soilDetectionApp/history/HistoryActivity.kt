package com.yenvth.soilDetectionApp.history

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import cn.pedant.SweetAlert.SweetAlertDialog
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.databinding.ActivityHistoryBinding
import com.yenvth.soilDetectionApp.history.HistoryAdapter.OnHistoryItemClickListener
import com.yenvth.soilDetectionApp.models.HistoryModel
import com.yenvth.soilDetectionApp.soilDetail.SoilDetailActivity
import com.yenvth.soilDetectionApp.utils.CommonUtils

class HistoryActivity : AppCompatActivity(),
    View.OnClickListener,
    HistoryView,
    OnHistoryItemClickListener {
    private lateinit var binding: ActivityHistoryBinding

    private val presenter: HistoryPresenterImpl<HistoryView> by lazy {
        HistoryPresenterImpl(this, this)
    }

    private val mAdapter: HistoryAdapter by lazy {
        HistoryAdapter(this, this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        action()
    }

    private fun init() {
        binding.header.tvToolbar.text = getString(R.string.search_history)

        val layoutManager: LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerViewHistory.layoutManager = layoutManager
        binding.recyclerViewHistory.adapter = mAdapter

        presenter.getHistories()
    }

    private fun action() {
        binding.header.btnBack.setOnClickListener(this)
        binding.btnDeleteAll.setOnClickListener(this)
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> onBackPressed()
            R.id.btnDeleteAll -> {
                val dialog = SweetAlertDialog(this@HistoryActivity, SweetAlertDialog.WARNING_TYPE)
                dialog.setContentText(getString(R.string.are_you_sure_to_delete_history))
                    .setConfirmText(getString(R.string.ok))
                    .setCancelText(getString(R.string.cancel))
                    .showCancelButton(true)
                    .setConfirmClickListener {
                        dialog.cancel()
                        presenter.deleteAllHistories()
                    }
                    .setCancelClickListener { dialog.cancel() }
                    .show()
            }
        }
    }

    override fun onGetListHistorySuccess(historyModels: List<HistoryModel>) {
        mAdapter.setData(historyModels)
    }

    override fun onDeleteHistorySuccess() {
        CommonUtils.showSnackBar(this@HistoryActivity, getString(R.string.delete_successful))
        presenter.getHistories()
    }

    override fun onDeleteAllHistoriesSuccess() {
        CommonUtils.showSnackBar(this@HistoryActivity, getString(R.string.delete_all_success))
        presenter.getHistories()
    }

    override fun onHistoryClick(historyModel: HistoryModel) {
        val intent = Intent(this@HistoryActivity, SoilDetailActivity::class.java)
        intent.putExtra("soilId", historyModel.soilId)
        startActivity(intent)
    }

    override fun onItemDelete(historiesId: Int) {
        presenter.deleteHistory(historiesId)
    }
}
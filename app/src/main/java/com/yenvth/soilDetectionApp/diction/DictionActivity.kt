package com.yenvth.soilDetectionApp.diction

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.View.OnFocusChangeListener
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.databinding.ActivityDictionBinding
import com.yenvth.soilDetectionApp.room.entity.HistoryRecord
import com.yenvth.soilDetectionApp.soilDetail.SoilDetailActivity

class DictionActivity : AppCompatActivity(), View.OnClickListener {
    private val viewModel by viewModels<DictionViewModel>()
    private lateinit var binding: ActivityDictionBinding

    companion object {
        const val TAG = "DictionActivity"
    }

    private val mAdapter: SoilAdapter by lazy {
        SoilAdapter(this) { model ->
            viewModel.saveSearchHistory(
                HistoryRecord(
                    historyId = 0,
                    soilId = model.soilId,
                    System.currentTimeMillis()
                )
            )
            val intent = Intent(this, SoilDetailActivity::class.java)
            intent.putExtra("soilId", model.soilId)
            startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDictionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        action()
        setupObserve()
    }

    private fun init() {
        viewModel.getListSoil(queryString = "")

        val layoutManager: LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerViewSoil.layoutManager = layoutManager
        binding.recyclerViewSoil.adapter = mAdapter
    }

    private fun action() {
        binding.btnBack.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
        binding.edSearch.onFocusChangeListener =
            OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.edSearch.setBackgroundResource(R.drawable.corner_search_edittext_select)
                } else {
                    binding.edSearch.setBackgroundResource(R.drawable.corner_search_edittext_unselect)
                }
            }
        binding.edSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
                binding.btnDelete.visibility = if (s.isBlank()) View.GONE else View.VISIBLE
                Handler().postDelayed({ viewModel.getListSoil(s.toString().trim()) }, 100)
            }
        })
        binding.btnDelete.setOnClickListener {
            binding.edSearch.setText("")
        }
    }

    private fun setupObserve() {
        viewModel.soils.observe(this) { soilModels ->
            mAdapter.setData(soilModels)
        }

        viewModel.saveStatus.observe(this) {
            Log.d(TAG, "History saved")
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnBack -> onBackPressed()
            R.id.btnSearch -> {
                val query = binding.edSearch.text.toString().trim()
                if (!TextUtils.isEmpty(query)) {
                    viewModel.getListSoil(query)
                }
            }
        }
    }
}
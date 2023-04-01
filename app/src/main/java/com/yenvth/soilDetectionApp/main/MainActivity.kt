package com.yenvth.soilDetectionApp.main

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.cnnModel.classification.ClassifierActivity
import com.yenvth.soilDetectionApp.databinding.ActivityMainBinding
import com.yenvth.soilDetectionApp.detection.DetectionActivity
import com.yenvth.soilDetectionApp.diction.DictionActivity
import com.yenvth.soilDetectionApp.history.HistoryActivity
import com.yenvth.soilDetectionApp.labeling.LabelingActivity
import com.yenvth.soilDetectionApp.map.MapActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        action()
    }

    private fun action() {
        binding.btnMenu.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
        binding.btnDiction.setOnClickListener(this)
        binding.btnDetect.setOnClickListener(this)
        binding.btnMap.setOnClickListener(this)
        binding.btnHis.setOnClickListener(this)
        binding.btnLabeling.setOnClickListener(this)
        binding.btnLogin.setOnClickListener(this)
        binding.rlDetect.setOnClickListener(this)
        binding.lnHis.setOnClickListener(this)
        binding.lnLabel.setOnClickListener(this)
        binding.navigationView.setNavigationItemSelectedListener { menuItem: MenuItem ->
            val id: Int = menuItem.itemId
            binding.drawer.closeDrawers()
            when (id) {
                R.id.diction -> {
                    startActivity(Intent(this@MainActivity, DictionActivity::class.java))
                }
                R.id.map -> {
                    startActivity(Intent(this@MainActivity, MapActivity::class.java))
                }
                R.id.history -> {
                    startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
                }
                R.id.detect -> {
                    startActivity(Intent(this@MainActivity, DetectionActivity::class.java))
                }
                R.id.labeling -> {
                    startActivity(Intent(this@MainActivity, LabelingActivity::class.java))
                }
            }
            false
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnMenu -> binding.drawer.openDrawer(Gravity.LEFT)
            R.id.btnDiction, R.id.btnSearch -> {
                startActivity(Intent(this@MainActivity, DictionActivity::class.java))
            }
            R.id.btnDetect -> {
                startActivity(Intent(this@MainActivity, DetectionActivity::class.java))
            }
            R.id.btnMap -> {
                startActivity(Intent(this@MainActivity, MapActivity::class.java))
            }
            R.id.lnHis, R.id.btnHis -> {
                startActivity(Intent(this@MainActivity, HistoryActivity::class.java))
            }
            R.id.lnLabel, R.id.btnLabeling -> {
                startActivity(Intent(this@MainActivity, LabelingActivity::class.java))
            }
            R.id.rlDetect -> {
                startActivity(Intent(this@MainActivity, ClassifierActivity::class.java))
            }
        }
    }
}
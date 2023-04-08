package com.yenvth.soilDetectionApp.map

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMapClickListener
import com.google.android.gms.maps.GoogleMap.OnMarkerClickListener
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.data.Feature
import com.google.maps.android.data.geojson.GeoJsonLayer
import com.google.maps.android.data.geojson.GeoJsonLayer.GeoJsonOnFeatureClickListener
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle
import com.yenvth.soilDetectionApp.R
import com.yenvth.soilDetectionApp.databinding.ActivityMapBinding
import com.yenvth.soilDetectionApp.map.MapAdapter.OnSoilMapItemClickListener
import com.yenvth.soilDetectionApp.models.ProvinceModel
import com.yenvth.soilDetectionApp.models.SoilModel
import com.yenvth.soilDetectionApp.soilDetail.SoilDetailActivity
import com.yenvth.soilDetectionApp.utils.CommonUtils
import com.yenvth.soilDetectionApp.utils.Constant
import com.yenvth.soilDetectionApp.utils.Constant.MapEnum
import com.yenvth.soilDetectionApp.utils.FileUtils
import com.yenvth.soilDetectionApp.utils.ImageUtils
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class MapActivity : AppCompatActivity(), OnMapReadyCallback, View.OnClickListener,
    OnMapClickListener, OnMarkerClickListener, OnSoilMapItemClickListener {
    private lateinit var binding: ActivityMapBinding
    private val viewModel by viewModels<MapViewModel>()

    private val fusedLocationProviderClient: FusedLocationProviderClient? = null
    private var googleMap: GoogleMap? = null
    private var mUiSettings: UiSettings? = null
    private val mAdapter: MapAdapter by lazy {
        MapAdapter(this, this)
    }
    private var soilModels: ArrayList<SoilModel>? = null
    private var soilSelected: SoilModel? = null
    private var provinceModels: List<ProvinceModel> = arrayListOf()
    private var provinceSelected: ProvinceModel? = null
    private var provinceAdapter: ArrayAdapter<*>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        init()
        action()
        setupObserve()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() {
        viewModel.getProvinces()

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        val layoutManager: LayoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        binding.recyclerViewSoilMap.layoutManager = layoutManager
        binding.recyclerViewSoilMap.itemAnimator = DefaultItemAnimator()
        binding.recyclerViewSoilMap.adapter = mAdapter
        mAdapter.notifyDataSetChanged()
    }

    private fun action() {
        binding.btnBack.setOnClickListener(this)
        binding.tvDetail.setOnClickListener(this)
        //Todo: Setup spinner
        provinceAdapter =
            ArrayAdapter(this, R.layout.layout_custom_spinner_not_icon, provinceModels)
        provinceAdapter?.setDropDownViewResource(R.layout.item_spinner)
        binding.spProvince.adapter = provinceAdapter
        binding.spProvince.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View, i: Int, l: Long) {
                provinceSelected = provinceModels[i]
//                CommonUtils.showSnackBar(this@MapActivity, provinceSelected.provinceName(), false)
//                presenter.getListSoilByProvince(provinceSelected.provinceId())
            }

            override fun onNothingSelected(adapterView: AdapterView<*>?) {}
        }
    }

    private fun setupObserve() {
        viewModel.provinceSoil.observe(this) { soilModels ->
            mAdapter.setData(soilModels)
            binding.expandLayout.expand()
            binding.expandDetail.collapse()
        }

        viewModel.provinces.observe(this) { provinces ->
            this.provinceModels = provinces
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        moveCameraMap(MapEnum.HANOT_LAT, MapEnum.HANOT_LON)
        googleMap.setOnMapClickListener(this)
        mUiSettings = googleMap.uiSettings
        mUiSettings?.isZoomControlsEnabled = false
        mUiSettings?.isMyLocationButtonEnabled = true
        mUiSettings?.isZoomGesturesEnabled = false
        mUiSettings?.isTiltGesturesEnabled = false
        mUiSettings?.isRotateGesturesEnabled = false
        mUiSettings?.isMapToolbarEnabled = false
        mUiSettings?.isScrollGesturesEnabled = false
        try {
            addGeoJsonLayerToMap(
                GeoJsonLayer(
                    googleMap,
                    JSONObject(FileUtils.readFile(this@MapActivity, "vietnamsoilmap.geojson"))
                )
            )
            addVietnamGeoJsonLayerToMap(
                GeoJsonLayer(
                    googleMap,
                    JSONObject(FileUtils.readFile(this@MapActivity, "vietnam.geojson"))
                )
            )
        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btnBack -> onBackPressed()
            R.id.tvDetail -> {
                val intent = Intent(this, SoilDetailActivity::class.java)
                intent.putExtra("soilId", soilSelected?.soilId)
                startActivity(intent)
            }
        }
    }

    private fun moveCameraMap(lat: Double, lon: Double) {
        val latLng = LatLng(lat, lon)
        if (googleMap != null) {
            googleMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    latLng,
                    MapEnum.HANOI_ZOOM.toFloat()
                )
            )
        }
    }

    override fun onMapClick(latLng: LatLng) {}
    override fun onMarkerClick(marker: Marker): Boolean {
        return false
    }

    override fun onSoilMapClickListener(soilModel: SoilModel) {
        soilSelected = soilModel
        binding.tvName.text = soilModel.nameVi
        binding.tvProvince.text = provinceSelected?.provinceName
        binding.tvDes.text = soilModel.description
        ImageUtils.setImage(this, soilModel.url, binding.imgSoil, binding.progressBar)
        binding.expandDetail.collapse()
        binding.expandDetail.expand()
    }

    private fun addColorsToMarkers(layer: GeoJsonLayer) {
        for (feature in layer.features) {
            val geoJsonPolygonStyle = GeoJsonPolygonStyle()
            geoJsonPolygonStyle.strokeColor = Color.parseColor("#ff0000")
            geoJsonPolygonStyle.fillColor = resources.getColor(
                Constant.getColorArea(
                    feature.getProperty("domsoil").toLowerCase(Locale.getDefault())
                )
            )
            geoJsonPolygonStyle.strokeWidth = 0f
            feature.polygonStyle = geoJsonPolygonStyle
        }
    }

    private fun addGeoJsonLayerToMap(layer: GeoJsonLayer) {
        addColorsToMarkers(layer)
        layer.addLayerToMap()
        layer.setOnFeatureClickListener(GeoJsonOnFeatureClickListener { feature: Feature? -> })
    }

    //Layer tỉnh Vietnam
    private fun addStrokeArea(layer: GeoJsonLayer) {
        for (feature in layer.features) {
            val geoJsonPolygonStyle = GeoJsonPolygonStyle()
            geoJsonPolygonStyle.strokeColor = Color.parseColor("#ff0000")
            geoJsonPolygonStyle.strokeWidth = 3f
            geoJsonPolygonStyle.isClickable = true
            feature.polygonStyle = geoJsonPolygonStyle
        }
    }

    private fun addVietnamGeoJsonLayerToMap(layer: GeoJsonLayer) {
        addStrokeArea(layer)
        layer.addLayerToMap()
        layer.setOnFeatureClickListener(GeoJsonOnFeatureClickListener { feature: Feature ->
            CommonUtils.showSnackBar(
                this@MapActivity,
                feature.getProperty("name") + " " + getString(R.string.province)
            )
            val provinceId = feature.getProperty("id_1").toIntOrNull()
            provinceSelected = provinceModels.find { it.provinceId == provinceId }
            binding.spProvince.setSelection(
                getIndex(
                    binding.spProvince,
                    provinceSelected?.provinceName ?: ""
                )
            )
            provinceId?.let { viewModel.getListSoilByProvince(it) }
        })
    }

    //private method of your class
    private fun getIndex(spinner: Spinner, myString: String): Int {
        for (i in 0 until spinner.count) {
            if (spinner.getItemAtPosition(i).toString().equals(myString, ignoreCase = true)) {
                return i
            }
        }
        return -1
    }
}
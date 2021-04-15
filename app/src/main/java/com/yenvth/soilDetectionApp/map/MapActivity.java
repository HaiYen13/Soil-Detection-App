package com.yenvth.soilDetectionApp.map;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.maps.android.data.geojson.GeoJsonFeature;
import com.google.maps.android.data.geojson.GeoJsonLayer;
import com.google.maps.android.data.geojson.GeoJsonPolygonStyle;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.ProvinceModel;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.soilDetail.SoilDetailActivity;
import com.yenvth.soilDetectionApp.sqliteHelper.DBHelper;
import com.yenvth.soilDetectionApp.utils.CommonUtils;
import com.yenvth.soilDetectionApp.utils.Constant;
import com.yenvth.soilDetectionApp.utils.FileUtils;

import net.cachapa.expandablelayout.ExpandableLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MapActivity extends AppCompatActivity implements
        MapView,
        OnMapReadyCallback,
        View.OnClickListener,
        GoogleMap.OnMapClickListener,
        GoogleMap.OnMarkerClickListener,
        MapAdapter.OnSoilMapItemClickListener{

    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.recycler_view_soil_map)
    protected RecyclerView recycler_view_soil_map;
    @BindView(R.id.expandLayout)
    protected ExpandableLayout expandLayout;
    @BindView(R.id.expandDetail)
    protected ExpandableLayout expandDetail;
    @BindView(R.id.tvDetail)
    protected TextView tvDetail;
    @BindView(R.id.spProvince)
    protected Spinner spProvince;
    @BindView(R.id.imgSoil)
    protected RoundedImageView imgSoil;
    @BindView(R.id.tvName)
    protected TextView tvName;
    @BindView(R.id.tvProvince)
    protected TextView tvProvince;
    @BindView(R.id.tvDes)
    protected TextView tvDes;
    @BindView(R.id.progressBar)
    protected ProgressBar progressBar;

    private FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap googleMap;
    private UiSettings mUiSettings;
    private MapPresenterImpl<MapView> presenter;

    private MapAdapter mAdapter;
    private ArrayList<SoilModel> soilModels;
    private SoilModel soilSelected;

    private DBHelper dbHelper;
    private ArrayList<ProvinceModel> provinceModels;
    private ProvinceModel provinceSelected;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        presenter = new MapPresenterImpl<>(this, this);
        init();
        action();
    }

    private void init() {
        dbHelper = new DBHelper(this);
        provinceModels = dbHelper.getProvinces();

        SupportMapFragment mapFragment =
                (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void action() {
        btnBack.setOnClickListener(this);
        tvDetail.setOnClickListener(this);
        //Todo: Setup spinner
        ArrayAdapter provinceAdapter = new ArrayAdapter<>(this, R.layout.layout_custom_spinner_not_icon, provinceModels);
        provinceAdapter.setDropDownViewResource(R.layout.item_spinner);
        spProvince.setAdapter(provinceAdapter);

        spProvince.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                provinceSelected = provinceModels.get(i);
                CommonUtils.showError(MapActivity.this, provinceSelected.getProvince_name());
                presenter.getListSoilByProvince(provinceSelected.getProvince_id());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
        moveCameraMap(Constant.MapEnum.HANOT_LAT, Constant.MapEnum.HANOT_LON);
        googleMap.setOnMapClickListener(this);

        if (mUiSettings != null) {
            mUiSettings = googleMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            mUiSettings.setMyLocationButtonEnabled(true);
            mUiSettings.setZoomGesturesEnabled(false);
            mUiSettings.setTiltGesturesEnabled(false);
            mUiSettings.setRotateGesturesEnabled(false);
            mUiSettings.setMapToolbarEnabled(false);
            mUiSettings.setScrollGesturesEnabled(false);
        }
        try {
            addGeoJsonLayerToMap(new GeoJsonLayer(googleMap, new JSONObject(FileUtils.readFile(MapActivity.this, "vietnamsoilmap.geojson"))));
            addVietnamGeoJsonLayerToMap(new GeoJsonLayer(googleMap, new JSONObject(FileUtils.readFile(MapActivity.this, "vietnam.geojson"))));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.tvDetail:
                Intent intent = new Intent(this, SoilDetailActivity.class);
                intent.putExtra("soil_id", soilSelected.getSoilId());
                startActivity(intent);
                break;
        }
    }

    private void moveCameraMap(double lat, double lon) {
        LatLng latLng = new LatLng(lat, lon);
        if (googleMap != null) {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, Constant.MapEnum.HANOI_ZOOM));
        }
    }

    @Override
    public void onMapClick(LatLng latLng) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    @Override
    public void onGetListSoilSuccess(ArrayList<SoilModel> soilModels) {
        this.soilModels = soilModels;
        expandLayout.expand();
        expandDetail.collapse();
        mAdapter = new MapAdapter(this, soilModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view_soil_map.setLayoutManager(layoutManager);
        recycler_view_soil_map.setItemAnimator(new DefaultItemAnimator());
        recycler_view_soil_map.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onSoilMapClickListener(SoilModel soilModel) {
        soilSelected = soilModel;
        tvName.setText(soilModel.getNameVi());
        tvProvince.setText(provinceSelected.getProvince_name());
        tvDes.setText(soilModel.getDescription());
        if (!TextUtils.isEmpty(soilModel.getUrl())) {
            Glide.with(MapActivity.this).load(soilModel.getUrl()).listener(new RequestListener<Drawable>() {
                @Override
                public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

                @Override
                public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                    progressBar.setVisibility(View.GONE);
                    return false;
                }

            }).into(imgSoil);

        }
        expandDetail.expand();
    }

    private void addColorsToMarkers(GeoJsonLayer layer) {
        for (GeoJsonFeature feature : layer.getFeatures()) {
            GeoJsonPolygonStyle geoJsonPolygonStyle = new GeoJsonPolygonStyle();
            geoJsonPolygonStyle.setStrokeColor(Color.parseColor("#ff0000"));
            geoJsonPolygonStyle.setFillColor(getResources().getColor(Constant.getColorArea(feature.getProperty("domsoil").toLowerCase())));
            geoJsonPolygonStyle.setStrokeWidth(0f);
            feature.setPolygonStyle(geoJsonPolygonStyle);
        }
    }

    private void addGeoJsonLayerToMap(GeoJsonLayer layer) {
        addColorsToMarkers(layer);
        layer.addLayerToMap();
        layer.setOnFeatureClickListener((GeoJsonLayer.GeoJsonOnFeatureClickListener) feature -> {

        });
    }

    //Layer tỉnh Vietnam
    private void addStrokeArea(GeoJsonLayer layer) {
        for (GeoJsonFeature feature : layer.getFeatures()) {
            GeoJsonPolygonStyle geoJsonPolygonStyle = new GeoJsonPolygonStyle();
            geoJsonPolygonStyle.setStrokeColor(Color.parseColor("#ff0000"));
            geoJsonPolygonStyle.setStrokeWidth(2f);
            geoJsonPolygonStyle.setClickable(true);
            feature.setPolygonStyle(geoJsonPolygonStyle);
        }

    }

    private void addVietnamGeoJsonLayerToMap(GeoJsonLayer layer) {
        addStrokeArea(layer);
        layer.addLayerToMap();
        layer.setOnFeatureClickListener((GeoJsonLayer.GeoJsonOnFeatureClickListener) feature -> {
            CommonUtils.showError(MapActivity.this, "Tỉnh " + feature.getProperty("name"));
            presenter.getListSoilByProvince(Integer.parseInt(feature.getProperty("id_1")));
        });
    }
}

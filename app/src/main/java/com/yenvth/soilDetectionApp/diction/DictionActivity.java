package com.yenvth.soilDetectionApp.diction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.DistrictModel;
import com.yenvth.soilDetectionApp.models.ProvinceModel;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.models.SoilParamModel;
import com.yenvth.soilDetectionApp.models.WardModel;
import com.yenvth.soilDetectionApp.soilDetail.SoilDetailActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DictionActivity extends AppCompatActivity implements DictionView, View.OnClickListener, SoilAdapter.OnSoilItemClickListener {

    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.recycler_view_soil)
    protected RecyclerView recycler_view_soil;
    @BindView(R.id.spProvince)
    protected AppCompatSpinner spProvince;
    @BindView(R.id.spDistrict)
    protected AppCompatSpinner spDistrict;
    @BindView(R.id.spWard)
    protected AppCompatSpinner spWard;

    private SoilAdapter mAdapter;
    private ArrayList<SoilModel> dataList;
    private DictionPresenterImpl<DictionView> mPresenter;
    private String queryString;

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
        mPresenter.getListSoil(queryString, new SoilParamModel(0, 0, 0, 0, 0));
    }

    private void action() {
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnBack:
                onBackPressed();
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
    }

    @Override
    public void onGetListProvinceSuccess(ArrayList<ProvinceModel> provinceModels) {
        ArrayAdapter provinceAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, provinceModels);
        provinceAdapter.setDropDownViewResource(R.layout.item_spinner);
        spProvince.setAdapter(provinceAdapter);
    }

    @Override
    public void onGetListDistrictSuccess(ArrayList<DistrictModel> districtModels) {
        ArrayAdapter districtAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, districtModels);
        districtAdapter.setDropDownViewResource(R.layout.item_spinner);
        spDistrict.setAdapter(districtAdapter);
    }

    @Override
    public void onGetListWardSuccess(ArrayList<WardModel> wardModels) {
        ArrayAdapter wardAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, wardModels);
        wardAdapter.setDropDownViewResource(R.layout.item_spinner);
        spWard.setAdapter(wardAdapter);
    }

    @Override
    public void onSoilClickListener(SoilModel soilModel) {
        Intent intent = new Intent(this, SoilDetailActivity.class);
        intent.putExtra("soil_id", soilModel.getSoil_id());
        startActivity(intent);
    }
}

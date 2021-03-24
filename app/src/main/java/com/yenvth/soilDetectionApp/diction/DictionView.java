package com.yenvth.soilDetectionApp.diction;

import com.yenvth.soilDetectionApp.models.DistrictModel;
import com.yenvth.soilDetectionApp.models.ProvinceModel;
import com.yenvth.soilDetectionApp.models.SoilModel;
import com.yenvth.soilDetectionApp.models.WardModel;

import java.util.ArrayList;

public interface DictionView {
    void onGetListSoilSuccess(ArrayList<SoilModel> soilModels);

    void onGetListProvinceSuccess(ArrayList<ProvinceModel> provinceModels);

    void onGetListDistrictSuccess(ArrayList<DistrictModel> districtModels);

    void onGetListWardSuccess(ArrayList<WardModel> wardModels);

}

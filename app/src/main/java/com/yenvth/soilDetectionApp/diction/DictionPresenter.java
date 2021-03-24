package com.yenvth.soilDetectionApp.diction;


import com.yenvth.soilDetectionApp.models.SoilParamModel;

public interface DictionPresenter<V extends DictionView> {
    void getListSoil(String queryString, SoilParamModel model);
    void getListProvince();
    void getListDistrict(int province_id);
    void getListWard(int district_id);
}

package com.yenvth.soilDetectionApp.api;

import com.yenvth.soilDetectionApp.models.DistrictModel;
import com.yenvth.soilDetectionApp.models.ProvinceModel;
import com.yenvth.soilDetectionApp.models.WardModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ResourceAPI {
    @GET("resource/provinces")
    Call<List<ProvinceModel>> getListProvinces();

    @GET("resource/districts")
    Call<List<DistrictModel>> getListDistricts(@Query("province_id") int province_id);

    @GET("resource/wards")
    Call<List<WardModel>> getListWards(@Query("district_id") int district_id);
}

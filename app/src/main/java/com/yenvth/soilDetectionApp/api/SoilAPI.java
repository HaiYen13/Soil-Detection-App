package com.yenvth.soilDetectionApp.api;

import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.SoilModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SoilAPI {
    @GET("soils")
    Call<List<SoilModel>> getListSoils(@Query("queryString") String queryString,
                                       @Query("province_id") int province_id,
                                       @Query("district_id") int district_id,
                                       @Query("ward_id") int ward_id,
                                       @Query("lat") double lat,
                                       @Query("lon") double lon);

    @GET("soils/{soil_id}")
    Call<SoilModel> getSoilDetail(@Path("soil_id") int soil_id);

    @POST("soils/{soil_id}")
    Call<BaseResponse> updateSoilInformation(@Path("soil_id") int soil_id,
                                             @Body SoilModel soilModel);
}

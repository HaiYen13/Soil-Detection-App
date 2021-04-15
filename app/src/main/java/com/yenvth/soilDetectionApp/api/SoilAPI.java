package com.yenvth.soilDetectionApp.api;

import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.SoilDetectModel;
import com.yenvth.soilDetectionApp.models.SoilModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface SoilAPI {
    @GET("soils")
    Call<List<SoilModel>> getListSoils(@Query("queryString") String queryString);

    @GET("soils/{soil_id}")
    Call<SoilModel> getSoilDetail(@Path("soil_id") int soil_id);

    @GET("province-soils/{provinceId}")
    Call<List<SoilModel>> getSoilByProvince(@Path("provinceId") int provinceId);

    @PUT("soils/{soil_id}")
    Call<BaseResponse> updateSoil(@Path("soil_id") int soil_id,
                                  @Body SoilModel soilModel);

    @POST("soils/detection")
    Call<SoilDetectModel> detectSoils(@Body String url);
}

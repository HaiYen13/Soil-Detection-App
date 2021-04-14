package com.yenvth.soilDetectionApp.api;

import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.LabelingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LabelAPI {

    @GET("labels")
    Call<List<LabelingModel>> getListLabels(@Query("uid") String uid);

    @POST("labels")
    Call<BaseResponse> addLabel(@Body LabelingModel labelingModel);

    @POST("labels/{labelId}")
    Call<BaseResponse> deleteLabel(@Path("labelId") int labelId);

//    @DELETE("labels/{label_id}")
//    Call<BaseResponse> deleteLabel(@Path("label_id") int label_id);

}

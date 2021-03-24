package com.yenvth.soilDetectionApp.api;
import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.LabelingModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
public interface LabelAPI {

    @GET("labels")
    Call<List<LabelingModel>> getListLabels(@Query("uid") String uid);

    @PUT("labels/{label_id}")
    Call<BaseResponse> updateLabel(@Path("label_id") int label_id,
                                   @Body LabelingModel labelingModel);

    @DELETE("labels/{label_id}")
    Call<BaseResponse> deleteLabel(@Path("label_id") int label_id);

}
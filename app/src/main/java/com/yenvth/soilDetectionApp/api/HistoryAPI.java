package com.yenvth.soilDetectionApp.api;

import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.HistoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoryAPI {
    @GET("histories")
    Call<List<HistoryModel>> getListHistories(@Query("uid") String uid);

    @PUT("histories/{history_id}")
    Call<BaseResponse> updateHistory(@Path("history_id") int history_id,
                                     @Body HistoryModel historyModel);

    @DELETE("histories/{label_id}")
    Call<BaseResponse> deleteHistory(@Path("history_id") int history_id);
}
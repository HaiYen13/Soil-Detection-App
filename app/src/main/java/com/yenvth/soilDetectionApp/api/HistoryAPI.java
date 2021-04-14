package com.yenvth.soilDetectionApp.api;

import com.yenvth.soilDetectionApp.base.BaseResponse;
import com.yenvth.soilDetectionApp.models.HistoryModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface HistoryAPI {
    @GET("histories")
    Call<List<HistoryModel>> getListHistories(@Query("uid") String uid);

    @POST("histories")
    Call<BaseResponse> saveHistory(@Body HistoryModel historyModel);

    @POST("histories/{historiesId}")
    Call<BaseResponse> deleteHistory(@Path("historiesId") int history_id);

    @POST("histories/delete-all")
    Call<BaseResponse> deleteAllHistories(@Query("uid") String uid);

//    @DELETE("histories/{historiesId}")
//    Call<BaseResponse> deleteHistory(@Path("historiesId") int history_id);
}

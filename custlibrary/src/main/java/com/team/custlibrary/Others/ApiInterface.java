package com.team.custlibrary.Others;

import com.team.custlibrary.Model.ResponseModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiInterface {

    @FormUrlEncoded
    @POST("HomeData")
    Call<ResponseModel> getHomeData(@Field("details") String device_id);

    @FormUrlEncoded
    @POST("getWithdrawTypeList")
    Call<ResponseModel> getWithdrawData(@Field("details") String device_id);

}

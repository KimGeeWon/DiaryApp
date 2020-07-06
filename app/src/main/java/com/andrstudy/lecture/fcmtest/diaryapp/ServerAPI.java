package com.andrstudy.lecture.fcmtest.diaryapp;


import com.andrstudy.lecture.fcmtest.diaryapp.data.WeatherData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface ServerAPI {
    @Headers("Content-Type: application/json")
    @GET("/data/2.5/forecast")
    Call<WeatherData> getData(@Query("q") String q, @Query("appid") String appid);
}
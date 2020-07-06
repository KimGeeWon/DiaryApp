package com.andrstudy.lecture.fcmtest.diaryapp;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class Server {
    private static Server instance;
    private ServerAPI api;

    private Server(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        String url="http://api.openweathermap.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        api= retrofit.create(ServerAPI.class);
    }
    public static Server getInstance(){
        if(instance==null) instance=new Server();
        return instance;
    }
    public ServerAPI getAPI(){ return api; }
}
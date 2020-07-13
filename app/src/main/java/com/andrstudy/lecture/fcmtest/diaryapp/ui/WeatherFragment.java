package com.andrstudy.lecture.fcmtest.diaryapp.ui;

import android.content.ContentValues;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andrstudy.lecture.fcmtest.diaryapp.RequestHttpURLConnection;
import com.andrstudy.lecture.fcmtest.diaryapp.Server;
import com.andrstudy.lecture.fcmtest.diaryapp.WeatherViewAdapter;
import com.andrstudy.lecture.fcmtest.diaryapp.data.Clouds;
import com.andrstudy.lecture.fcmtest.diaryapp.data.Lists;
import com.andrstudy.lecture.fcmtest.diaryapp.data.WeatherData;
import com.andrstudy.lecture.fcmtest.diaryapp.data.Weathers;
import com.andrstudy.lecture.fcmtest.diaryapp.databinding.FragmentWeatherBinding;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WeatherFragment extends Fragment {

    private FragmentWeatherBinding binding;

    private WeatherData weatherData;
    WeatherViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentWeatherBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Call<WeatherData> request= Server.getInstance().getAPI().getData("seoul", "ebba52ccd5186c2dd6951d933d920642");
        request.enqueue(new Callback<WeatherData>() {
            @Override
            public void onResponse(Call<WeatherData> call, Response<WeatherData> response) {
                adapter = new WeatherViewAdapter();

                weatherData = response.body();
                WeatherData cod = weatherData;
                setDataView(weatherData);
            }

            @Override
            public void onFailure(Call<WeatherData> call, Throwable t) {
                Log.i("Error", t.toString());
            }
        });
    }

    public void setDataView(WeatherData data) {
        //adapter = new WeatherViewAdapter();
        for(Lists d : data.list) {
            Log.i("asdf", String.valueOf(d));
//            adapter.addItem(d.dt_txt, d.weather., Math.round(d.main.temp - 273) + "도");
        }
        binding.weatherListView.setAdapter(adapter);
    }
}
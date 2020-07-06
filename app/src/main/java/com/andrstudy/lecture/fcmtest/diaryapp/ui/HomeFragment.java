package com.andrstudy.lecture.fcmtest.diaryapp.ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.andrstudy.lecture.fcmtest.diaryapp.R;
import com.andrstudy.lecture.fcmtest.diaryapp.databinding.FragmentDiaryWriteBinding;
import com.andrstudy.lecture.fcmtest.diaryapp.databinding.FragmentHomeBinding;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

//    {
//        "coord":{
//        "lon":126.97, "lat":37.48
//    },"weather":[{
//        "id":500, "main":"Rain", "description":"light rain", "icon":"10d"
//    },{
//        "id":701, "main":"Mist", "description":"mist", "icon":"50d"
//    }],"base":"stations", "main":{
//        "temp":20.97, "feels_like":22.48, "temp_min":20, "temp_max":23, "pressure":991, "humidity":
//        94
//    },"visibility":8000, "wind":{
//        "speed":3.1, "deg":340
//    },"clouds":{
//        "all":90
//    },"dt":1593482883, "sys":{
//        "type":1, "id":8096, "country":"KR", "sunrise":1593461652, "sunset":1593514625
//    },"timezone":32400, "id":6800035, "name":"Banpobondong", "cod":200
//    }
}

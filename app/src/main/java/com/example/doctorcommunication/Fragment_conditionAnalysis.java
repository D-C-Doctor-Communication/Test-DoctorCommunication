package com.example.doctorcommunication;

//android 버전 30쓸거면 androidx.Fragment 사용할것
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Fragment_conditionAnalysis extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        Log.d("myapp","상태분석탭 열림");
        return inflater.inflate(R.layout.fragment_condition_analysis,container,false);
    }
}
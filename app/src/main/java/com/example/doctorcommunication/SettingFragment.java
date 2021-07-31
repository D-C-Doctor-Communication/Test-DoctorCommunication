package com.example.doctorcommunication;

import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SettingFragment extends PreferenceFragment {
    private static final String SETTING_PREF_FILENAME = "pomodoro_setting";

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("myapp", "home탭 열림");
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //PreferenceManager객체 생성하고 addPreferencesFromResource로 세팅 xml 지정
        //PreferenceManager preferenceManager = new PreferenceFragment();

        return view;
    }
    //onCreatePreferences 재정의
}

package com.example.doctorcommunication;

import android.os.Bundle;
import android.preference.PreferenceFragment;

import androidx.annotation.Nullable;


public class SettingFragment extends PreferenceFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getPreferenceManager().setSharedPreferencesName("DoctorCommunication_setting");
        addPreferencesFromResource(R.xml.setting_preferences);
    }


    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {


    }




}
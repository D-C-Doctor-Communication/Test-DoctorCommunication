package com.example.doctorcommunication.Settings;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.preference.ListPreference;
import androidx.preference.Preference;
import androidx.preference.PreferenceManager;
import androidx.preference.SeekBarPreference;
import androidx.preference.SwitchPreference;
import android.widget.Switch;

import com.example.doctorcommunication.R;

public class SettingActivity extends PreferenceActivity {

    //구현 x
    SharedPreferences sp;
    ListPreference textSize;
    SwitchPreference alert;
    SeekBarPreference alert_volume;
    //구현 o
    Preference notice;
    Preference feedback;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        Log.d("myapp","설정 열림");
        //ActionBar actionBar = getSupportActionBar();
        //actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#FFFFFF")));
        //actionBar.setDisplayHomeAsUpEnabled(true);
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_back_btn);

/*

        addPreferencesFromResource(R.xml.setting_preferences);
        textSize = (ListPreference) findPreference("textSize");
        alert = (SwitchPreference) findPreference("alert");
        alert_volume = (SeekBarPreference) findPreference("alert_volume");
        notice = (Preference) findPreference("notice");
        feedback  = (Preference) findPreference("feedback");

        //비활성화
        notice.setEnabled(false);
        textSize.setEnabled(false);
        alert.setEnabled(false);
        alert_volume.setEnabled(false);

        sp = PreferenceManager.getDefaultSharedPreferences(SettingActivity.this);
        if(!sp.getString("notice","").equals("")){
            notice.setSummary(sp.getString("notice",""));
        }
        if(!sp.getString("feedback","").equals("")){
            notice.setSummary(sp.getString("feedback",""));
        }



        sp.registerOnSharedPreferenceChangeListener(spListener);

 */

    }
/*

    @Override
    public void onOptionsMenuClosed(Menu menu) {
        int itemId = menu.getItem();
        Log.d("options",itemId+"");
        super.onOptionsMenuClosed(menu);
    }

 */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        finish();
        return true;
    }
}

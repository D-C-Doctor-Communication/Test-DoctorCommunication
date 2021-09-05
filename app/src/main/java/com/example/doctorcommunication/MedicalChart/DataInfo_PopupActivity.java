package com.example.doctorcommunication.MedicalChart;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.WindowManager;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.R;

public class DataInfo_PopupActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("myapp","팝업 실행됨");
        //투명배경
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //전체화면 모드(상태바 제거)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mc_info_popup);

        int data = 2;

        //동적으로 추가
        for(int i=0;i<2;i++){
            DataInfo_PopupLayout n_layout = new DataInfo_PopupLayout(getApplicationContext());
            LinearLayout addLayout = (LinearLayout)findViewById(R.id.addLayout);
            addLayout.addView(n_layout);
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
            finish();
            return false;
        }
        else {
            finish();
            return true;
        }

    }
    @Override
    public void onBackPressed() {
        finish();
    }

}
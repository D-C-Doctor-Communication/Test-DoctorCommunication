package com.example.doctorcommunication;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;

public class MC_PopupActivity extends AppCompatActivity {

    Button ok_btn;
    ImageView cancel_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //모서리 둥글게
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //전체화면 모드(상태바 제거)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mc_popup);

        TimePicker time_picker = findViewById(R.id.time_picker);

        //showTimePicker().show();

        ok_btn = findViewById(R.id.ok_btn);
        cancel_btn = findViewById(R.id.cancel_button);
        ok_btn.setOnClickListener(v->{
            finish();
        });
        cancel_btn.setOnClickListener(v->{
            finish();
        });

    }
//    public TimePickerCustom showTimePicker() {
//        Calendar cal = Calendar.getInstance();
//        TimePickerCustom tpd =
//                new TimePickerCustom(
//                        this,android.R.style.Theme_Holo_Light_Dialog_NoActionBar,
//                        (view, hourOfDay, minute) -> Log.d("myapp",
//                                hourOfDay +"시 " + minute+"분 을 선택했습니다"), // 값설정시 호출될 리스너 등록
//                        4,19, false);
//        tpd.getWindow().setBackgroundDrawableResource(android.R.color.transparent);
//        return tpd;
//    }
}
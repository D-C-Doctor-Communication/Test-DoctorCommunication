package com.example.doctorcommunication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;

import java.util.concurrent.atomic.AtomicReference;

public class MC_PopupActivity extends AppCompatActivity {

    //저장 버튼
    Button ok_btn;
    //x 버튼
    ImageView cancel_btn;
    //TimePicker
    TimePicker time_picker;
    //일정 이름
    EditText scheduleName;
    //장소 선택
    EditText location;
    //검사|진료 선택 버튼
    Button checkup_btn,treatment_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //모서리 둥글게
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //전체화면 모드(상태바 제거)
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_mc_popup);



        //저장 | X버튼
        ok_btn = findViewById(R.id.ok_btn);
        cancel_btn = findViewById(R.id.cancel_button);

        //timepicker 설정
        time_picker = findViewById(R.id.time_picker);
        //12시간 기준
        time_picker.setIs24HourView(false);

        //검사|진료 선택 버튼
        checkup_btn = findViewById(R.id.checkup_btn);
        treatment_btn = findViewById(R.id.treatment_btn);

        checkup_btn.setOnClickListener(v -> {
            checkWhichBtnClicked(checkup_btn,treatment_btn);
        });
        treatment_btn.setOnClickListener(v -> {
            checkWhichBtnClicked(treatment_btn,checkup_btn);
        });

        //저장버을 눌렀을 때에만 Fragment_medicalChart로 입력값 전달
        ok_btn.setOnClickListener(v->{
            //인텐트 객체 생성
            Intent intent =new Intent();

//TimePicker
            //선택된 시간 저장을 위한 String 변수(시,분,오전|오후)
            String hour,minute,AmPm;
            //AMPM계산을 위한 int형 시간
            int nHour;
            //선택된 시간값 저장 (버전마다 timepicker에서 시간을 받아오는 메소드가 다름)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                hour = time_picker.getHour() + "";
                minute = time_picker.getMinute() + "";
                nHour = time_picker.getHour();
            } else {
                hour = time_picker.getCurrentHour() + "";
                minute = time_picker.getCurrentMinute() + "";
                nHour = time_picker.getCurrentHour();
            }
            AmPm = getAmPm(nHour);
            // 선택된 시간 값을 String 값으로 변환하여 전달
            intent.putExtra("selected_time",hour+":"+minute+" "+AmPm);

// '일정이름' 입력 값을 String 값으로 그대로 전달
            scheduleName = findViewById(R.id.scheduleName) ;
            intent.putExtra("schedule_name", scheduleName.getText().toString()) ;

// '검사 | 진료 ' 버튼의 눌림여부를 boolean 값으로 전달 (각 버튼 선택되면 색상 바뀜 기능 추가)
            checkWhichBtnClicked(checkup_btn, treatment_btn);
            AtomicReference<String> selectedButton = new AtomicReference<>("검사"); //기본값 "검사"
            //검사 버튼
            checkup_btn.setOnClickListener( v1 -> {
                checkWhichBtnClicked(checkup_btn, treatment_btn);
                selectedButton.set("검사");
            });
            //진료 버튼
            treatment_btn.setOnClickListener( v1 -> {
                checkWhichBtnClicked(treatment_btn, checkup_btn);
                selectedButton.set("진료");
            });
            intent.putExtra("selected_button", selectedButton.get());

// '장소' 입력 값을 String 값으로 그대로 전달
            location = findViewById(R.id.location) ;
            intent.putExtra("location", location.getText().toString()) ;


// Activity 종료와 동시에 값이 저장된 intent 객체 전달
            setResult(RESULT_OK, intent) ;
            finish() ;
        });

        //X버튼 눌렀을 경우 데이터 저장 없이 activity 종료
        cancel_btn.setOnClickListener(v->{
            finish();
        });

    }
    private void checkWhichBtnClicked(Button selectedBtn,Button nonSelectedBtn){
        //선택된 버튼은 파란색으로, 나머지는 하얀색으로 지정
        selectedBtn.setBackgroundColor(Color.parseColor("#0078ff"));
        nonSelectedBtn.setBackgroundColor(Color.WHITE);
        //선택된 버튼의 글자색은 하얀색으로, 나머지는 회색으로 지정
        selectedBtn.setTextColor(Color.WHITE);
        nonSelectedBtn.setTextColor(Color.parseColor("#29000000"));
    }

    //PM,AM값 계산 메소드
    private String getAmPm(int hour) {
        if (hour <= 12)
            return "AM";
        else
            return "PM";
    }
}
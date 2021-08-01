package com.example.doctorcommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


public class MeetingDoc extends AppCompatActivity {

    private TextView startDate; //기간선택 - 시작 날짜를 표시할 TextView
    private TextView endDate; //기간선택 - 종료 날짜를 표시할 TextView

    private Button gotoGraph; // 심각도 그래프로 이동하는 버튼

    //날짜선택 버튼 위 증상 텍스트
    TextView symptom_title;
    //증상선택 버튼
    Button btn_1_symptom;
    Button btn_2_symptom;
    Button btn_3_symptom;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_doctor);
        Log.d("myapp","의사와의 만남탭 열림");
//        Log.d("myapp",Person1.symptom1.getDate());


//기본 세팅
//액션바 사라짐 -> ?
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction(); // 각 프레그먼트들로 이동하기 위한 객체 생성
        //이전버튼, 녹음버튼있는 툴바(액션바 기능)
        Toolbar toolbar = findViewById(R.id.toolbar_meeting_doctor); //마이크, 이전버튼 들어있는 toolbar 생성
        setSupportActionBar(toolbar); //toolbar를 액션바로서 지정

        // 증상에 대한 심각도 그래프로 이동하는 버튼
        gotoGraph = findViewById(R.id.btn_gotoGraph);
        gotoGraph.setOnClickListener(v->{
            Log.d("mytag","그래프 이동 버튼 눌림");
           //심각도 그래프 버튼을 누르면 상태분석 페이지 프래그먼트를 불러옴
            FragmentTransaction gotoGraph = getSupportFragmentManager().beginTransaction();
            Fragment_conditionAnalysis fragment = new Fragment_conditionAnalysis();
            //FrameLayout을 사용하였기 때문에 겹쳐보이지 않도록 meeting_doctor_Frame를 unvisible로 설정
            FrameLayout doc_frame = findViewById(R.id.meeting_doctor_Frame);
            doc_frame.setVisibility(View.INVISIBLE);
            //프레그먼트를 프레임과 교체하여 띄움
            gotoGraph.replace(R.id.meeting_doctor_Frame,fragment); //meeting_doctor_Frame : meeting_doctor 최상위 레이아웃
            gotoGraph.commit();
        });

        //기간 선택을 위한 DatePicker를 호출하는 버튼(TextView)
        startDate = (TextView)findViewById(R.id.startDate);//기간선택 - 시작 날짜를 표시할 TextView
        endDate = (TextView)findViewById(R.id.endDate);//기간선택 - 종료 날짜를 표시할 TextView

        //날짜선택 버튼 위 증상 텍스트
        symptom_title = findViewById(R.id.symptom_title);

        //각 증상 선택 버튼을 눌렀을때 누른 버튼의 아이디값을 받아옴
        btn_1_symptom = findViewById(R.id.btn_1_symptom);
        btn_2_symptom = findViewById(R.id.btn_2_symptom);
        btn_3_symptom = findViewById(R.id.btn_3_symptom);


// -> 증상 선택 기능

        //리스트 레이아웃 / 버튼 클릭시 사라질 "선택해주세요"레이아웃
        //symthom_description_notSelected = findViewById(R.id.symthom_description_notSelected);
        ListView dcListView = findViewById(R.id.DC_listView);

        ListView finalDcListView = dcListView;
        btn_1_symptom.setOnClickListener(v -> {
            Log.d("myapp","가래 버튼 눌림");
            symptom_title.setText("가래");
//              DC_listView.setVisibility(View.VISIBLE);
//              symthom_description_notSelected.setVisibility(View.INVISIBLE);
                createDCList(finalDcListView);
        });

        btn_2_symptom.setOnClickListener(v -> {
            Log.d("myapp","발열 버튼 눌림");
            symptom_title.setText("발열");
            createDCList(finalDcListView);
//            DC_listView.setVisibility(View.VISIBLE);
//            symthom_description_notSelected.setVisibility(View.INVISIBLE);
        });

        btn_3_symptom.setOnClickListener(v -> {
            Log.d("myapp","두통 버튼 눌림");
            symptom_title.setText("두통");
            createDCList(finalDcListView);
//            DC_listView.setVisibility(View.VISIBLE);
//            symthom_description_notSelected.setVisibility(View.INVISIBLE);
        });

// -> 날짜 선택 기능

        //시작날짜 텍스트를 누르면 DatePickerDialog 동작
        startDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance(); //Calendar 객체 생성
            int mYear = calendar.get(Calendar.YEAR); //기본으로 선택된 날짜의 "년도" 지정 (지금은 현재로 지정됨)
            int mMonth = calendar.get(Calendar.MONTH); //기본으로 선택된 날짜의 "월" 지정 (지금은 현재로 지정됨)
            int mDay = calendar.get(Calendar.DAY_OF_MONTH); //기본으로 선택된 날짜의 "일" 지정 (지금은 현재로 지정됨)

            //DatePickerDialog 불러옴
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    //0000.00.00의 형식으로 입력받은 날짜를 startDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) ->{
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        startDate.setText(PickedDate);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    ,2021,8,1
            );
            //DatePickerDialog 표시
            datePickerDialog.show();

        });

        //종료날짜 텍스트를 누르면 DatePickerDialog 동작 (위의 startDate방식과 동일함)
        endDate.setOnClickListener(v -> {
            final Calendar calendar = Calendar.getInstance(); //Calendar 객체 생성
            int mYear = calendar.get(Calendar.YEAR); //기본으로 선택된 날짜의 "년도" 지정 (지금은 현재로 지정됨)
            int mMonth = calendar.get(Calendar.MONTH); //기본으로 선택된 날짜의 "월" 지정 (지금은 현재로 지정됨)
            int mDay = calendar.get(Calendar.DAY_OF_MONTH); //기본으로 선택된 날짜의 "일" 지정 (지금은 현재로 지정됨)

            //DatePickerDialog 불러옴
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    this,
                    //0000.00.00의 형식으로 입력받은 날짜를 startDate 텍스트뷰의 텍스트로 지정
                    (view, year, month, dayOfMonth) -> {
                        String PickedDate = year + "." + (month + 1) + "." + dayOfMonth;
                        endDate.setText(PickedDate);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    ,2021,8,1
            );
            //DatePickerDialog 표시
            datePickerDialog.show();
        });


// -> List View 기능
        //Adapter 생성
        DCListViewAdapter dcListViewAdapter = new DCListViewAdapter();
        //ListView 참조 및 어댑터 연결
        dcListView = (ListView)findViewById(R.id.DC_listView);
        dcListView.setAdapter(dcListViewAdapter);

        //addItem시 데이터 삽입 순서
        //dc_list_title_date
        //dc_list_title_partAndLevel
        //dc_list_content_part
        //dc_list_content_level
        //dc_list_content_characteristics
        //dc_list_content_situation
        //dc_list_content_accompany_pain
        //dc_list_content_additional
        //if(dcListViewAdapter.getCount()!=0) dcListView.setVisibility(View.VISIBLE);
        /*
        Log.d("myapp","adapter addItem 전");
        dcListViewAdapter.addItem(Person1.symptom1.getDate(),
                Person1.symptom1.getPart()+Person1.symptom1.getPain_level()
                ,Person1.symptom1.getPart(),Person1.symptom1.getPain_level(),
                Person1.symptom1.getPain_characteristics(),Person1.symptom1.getPain_situation(),
                Person1.symptom1.getAccompany_pain(),Person1.symptom1.getAdditional());
        dcListViewAdapter.addItem(Person1.symptom2.getDate(),
                Person1.symptom2.getPart()+Person1.symptom2.getPain_level()
                ,Person1.symptom2.getPart(),Person1.symptom2.getPain_level(),
                Person1.symptom2.getPain_characteristics(),Person1.symptom2.getPain_situation(),
                Person1.symptom2.getAccompany_pain(),Person1.symptom2.getAdditional());
        dcListViewAdapter.addItem(Person1.symptom3.getDate(),
                Person1.symptom3.getPart()+Person1.symptom3.getPain_level()
                ,Person1.symptom3.getPart(),Person1.symptom3.getPain_level(),
                Person1.symptom3.getPain_characteristics(),Person1.symptom3.getPain_situation(),
                Person1.symptom3.getAccompany_pain(),Person1.symptom3.getAdditional());
        Log.d("myapp","dc Adapter added");
        dcListViewAdapter.notifyDataSetChanged();
        Log.d("myapp","adapter addItem 후");
*/

    }


// -> toolbar 기능
    @Override
    //toolbar와 메뉴 구성 layout 연결
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.meeting_doc_toolbar_menu,menu);
        return true;
    }
    @Override
    //toolbar의 메뉴에 따라 각 경우의 동작 지정
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            //이전버튼
            case R.id.backToHome:
                Intent backToHome = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(backToHome);
            //마이크버튼(녹음하기)
            case R.id.record:
                Intent record = new Intent(getApplicationContext(),Recording.class);
                startActivity(record);
        }
        return true;
    }

    public void createDCList(ListView listView){
        //listView 참조 및 Adapter 연결
        DCListViewAdapter adapter = new DCListViewAdapter();
        //Adapter 지정
        listView.setAdapter(adapter);
        //데이터의 날짜가 datePicker의 두 날짜 사이에있으면 true 반환
        if(checkIsBetween(Person1.symptom1.getDate())) adapter.addItem(Person1.symptom1.getDate(),
                Person1.symptom1.getPart()+Person1.symptom1.getPain_level()
                ,Person1.symptom1.getPart(),Person1.symptom1.getPain_level(),
                Person1.symptom1.getPain_characteristics(),Person1.symptom1.getPain_situation(),
                Person1.symptom1.getAccompany_pain(),Person1.symptom1.getAdditional());
        if(checkIsBetween(Person1.symptom2.getDate())) adapter.addItem(Person1.symptom2.getDate(),
                Person1.symptom2.getPart()+Person1.symptom2.getPain_level()
                ,Person1.symptom2.getPart(),Person1.symptom2.getPain_level(),
                Person1.symptom2.getPain_characteristics(),Person1.symptom2.getPain_situation(),
                Person1.symptom2.getAccompany_pain(),Person1.symptom2.getAdditional());
        if(checkIsBetween(Person1.symptom3.getDate())) adapter.addItem(Person1.symptom3.getDate(),
                Person1.symptom3.getPart()+Person1.symptom3.getPain_level()
                ,Person1.symptom3.getPart(),Person1.symptom3.getPain_level(),
                Person1.symptom3.getPain_characteristics(),Person1.symptom3.getPain_situation(),
                Person1.symptom3.getAccompany_pain(),Person1.symptom3.getAdditional());
        if(checkIsBetween(Person1.symptom4.getDate())) adapter.addItem(Person1.symptom4.getDate(),
                Person1.symptom4.getPart()+Person1.symptom4.getPain_level()
                ,Person1.symptom4.getPart(),Person1.symptom4.getPain_level(),
                Person1.symptom4.getPain_characteristics(),Person1.symptom4.getPain_situation(),
                Person1.symptom4.getAccompany_pain(),Person1.symptom4.getAdditional());
        if(checkIsBetween(Person1.symptom5.getDate())) adapter.addItem(Person1.symptom5.getDate(),
                Person1.symptom5.getPart()+Person1.symptom5.getPain_level()
                ,Person1.symptom5.getPart(),Person1.symptom5.getPain_level(),
                Person1.symptom5.getPain_characteristics(),Person1.symptom5.getPain_situation(),
                Person1.symptom5.getAccompany_pain(),Person1.symptom5.getAdditional());
        if(checkIsBetween(Person1.symptom6.getDate())) adapter.addItem(Person1.symptom6.getDate(),
                Person1.symptom6.getPart()+Person1.symptom6.getPain_level()
                ,Person1.symptom6.getPart(),Person1.symptom6.getPain_level(),
                Person1.symptom6.getPain_characteristics(),Person1.symptom6.getPain_situation(),
                Person1.symptom6.getAccompany_pain(),Person1.symptom6.getAdditional());
        if(checkIsBetween(Person1.symptom7.getDate())) adapter.addItem(Person1.symptom7.getDate(),
                Person1.symptom7.getPart()+Person1.symptom7.getPain_level()
                ,Person1.symptom7.getPart(),Person1.symptom7.getPain_level(),
                Person1.symptom7.getPain_characteristics(),Person1.symptom7.getPain_situation(),
                Person1.symptom7.getAccompany_pain(),Person1.symptom7.getAdditional());
        if(checkIsBetween(Person1.symptom8.getDate())) adapter.addItem(Person1.symptom8.getDate(),
                Person1.symptom8.getPart()+Person1.symptom8.getPain_level()
                ,Person1.symptom8.getPart(),Person1.symptom8.getPain_level(),
                Person1.symptom8.getPain_characteristics(),Person1.symptom8.getPain_situation(),
                Person1.symptom8.getAccompany_pain(),Person1.symptom8.getAdditional());
        if(checkIsBetween(Person1.symptom9.getDate())) adapter.addItem(Person1.symptom9.getDate(),
                Person1.symptom9.getPart()+Person1.symptom9.getPain_level()
                ,Person1.symptom9.getPart(),Person1.symptom9.getPain_level(),
                Person1.symptom9.getPain_characteristics(),Person1.symptom9.getPain_situation(),
                Person1.symptom9.getAccompany_pain(),Person1.symptom9.getAdditional());
        if(checkIsBetween(Person1.symptom10.getDate())) adapter.addItem(Person1.symptom10.getDate(),
                Person1.symptom10.getPart()+Person1.symptom10.getPain_level()
                ,Person1.symptom10.getPart(),Person1.symptom10.getPain_level(),
                Person1.symptom10.getPain_characteristics(),Person1.symptom10.getPain_situation(),
                Person1.symptom10.getAccompany_pain(),Person1.symptom10.getAdditional());
        if(checkIsBetween(Person1.symptom11.getDate())) adapter.addItem(Person1.symptom11.getDate(),
                Person1.symptom11.getPart()+Person1.symptom11.getPain_level()
                ,Person1.symptom11.getPart(),Person1.symptom11.getPain_level(),
                Person1.symptom11.getPain_characteristics(),Person1.symptom11.getPain_situation(),
                Person1.symptom11.getAccompany_pain(),Person1.symptom11.getAdditional());
        if(checkIsBetween(Person1.symptom12.getDate())) adapter.addItem(Person1.symptom12.getDate(),
                Person1.symptom12.getPart()+Person1.symptom12.getPain_level()
                ,Person1.symptom12.getPart(),Person1.symptom12.getPain_level(),
                Person1.symptom12.getPain_characteristics(),Person1.symptom12.getPain_situation(),
                Person1.symptom12.getAccompany_pain(),Person1.symptom12.getAdditional());

        adapter.notifyDataSetChanged();
    }
    public boolean checkIsBetween(String date){
/*
        @SuppressLint("SimpleDateFormat") SimpleDateFormat transFormat = new SimpleDateFormat("yyyy.MM.dd");

        Date to,to2;
         try {
             to =(Date) transFormat.parse(start);
             to2 =(Date) transFormat.parse(end);
         }catch (ParseException e) {
                e.printStackTrace();
                Log.d("myapp","실패");
         }
        return true;*/

        try {
            //startDate,endDate : DatePicker에서 선택한 시작/종료 날짜
            //wantCheck : 증상이 기록된 날짜
            String start = startDate.getText().toString();
            String end = endDate.getText().toString();

            Calendar StartDay,EndDay,CheckDate; //Date객체형으로 바꾼 시작 / 끝 날짜
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd", Locale.KOREA); //형변환을 위한 데이트포멧 객체 생성

            Date date_start = dateFormat.parse(start); //Date형으로 변환
            Date date_end = dateFormat.parse(end);
            Date date_wantCheck = dateFormat.parse(date);

            StartDay = Calendar.getInstance(); //Calender형으로 변환
            StartDay.setTime(date_start);
            EndDay  = Calendar.getInstance();
            EndDay.setTime(date_end);
            CheckDate  = Calendar.getInstance();
            CheckDate.setTime(date_wantCheck);

            if(CheckDate.before(EndDay)&&!CheckDate.before(StartDay)){
                Log.d("myapp","통과됨");
                return true;
            }
            Log.d("myapp","통과되지 않음");
            return false;
        }catch (Exception e) {
            Log.d("myapp","예외 발생");
            return false;
        }
/*        //데이터의 날짜가 datePicker에서 선택된 년도 사이에 있는지 판별
        if(Integer.parseInt(start.substring(0,4)) <= Integer.parseInt(date.substring(0,4))
            &&Integer.parseInt(end.substring(0,4)) >= Integer.parseInt(date.substring(0,4))){
            //데이터의 날짜가 datePicker에서 선택된 달 사이에 있는지 판별
            if(Integer.parseInt(start.substring(4,6)) <= Integer.parseInt(date.substring(4,6))
                    &&Integer.parseInt(end.substring(5,7)) >= Integer.parseInt(date.substring(5,7))){
                //데이터의 날짜가 datePicker에서 선택된 일 사이에 있는지 판별
                if(Integer.parseInt(start.substring(8,10)) <= Integer.parseInt(date.substring(8,10))
                        &&Integer.parseInt(end.substring(8,10)) >= Integer.parseInt(date.substring(8,10))) {
                    return true;
                }
                return false;
            }
            return false;
        }
        return false;*/

    }
}

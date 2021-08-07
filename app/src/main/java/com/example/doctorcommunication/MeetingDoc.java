package com.example.doctorcommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentTransaction;

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
import android.widget.TextView;

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
    Button[] symptomBtn = new Button[3]; //증상 개수(임시 3)

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
        symptomBtn[0] = findViewById(R.id.btn_1_symptom);
        symptomBtn[1] = findViewById(R.id.btn_2_symptom);
        symptomBtn[2] = findViewById(R.id.btn_3_symptom);


// -> 증상 선택 기능

        //리스트 레이아웃 / 버튼 클릭시 사라질 "선택해주세요"레이아웃
        //symthom_description_notSelected = findViewById(R.id.symthom_description_notSelected);
        ListView dcListView = findViewById(R.id.DC_listView);

        ListView finalDcListView = dcListView;
        symptomBtn[0].setOnClickListener(v -> {
            Log.d("myapp","가래 버튼 눌림");
            symptom_title.setText("가래");
            createDCList(0,finalDcListView);
        });

        symptomBtn[1].setOnClickListener(v -> {
            Log.d("myapp","발열 버튼 눌림");
            symptom_title.setText("발열");
            createDCList(1,finalDcListView);
        });

        symptomBtn[2].setOnClickListener(v -> {
            Log.d("myapp","두통 버튼 눌림");
            symptom_title.setText("두통");
            createDCList(2,finalDcListView);
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
                        String PickedDate = year + "." + (month+1) + "." + dayOfMonth;
                        startDate.setText(PickedDate);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    ,mYear,mMonth,mDay
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
                        String PickedDate = year + "." + (month+1) + "." + dayOfMonth;
                        endDate.setText(PickedDate);
                    }
                    //기본 세팅 날짜 지정 (위의 변수대로)
                    ,mYear,mMonth,mDay
            );
            //DatePickerDialog 표시
            datePickerDialog.show();
        });


// -> List View 기능

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
                Intent backToHome = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(backToHome);
            //마이크버튼(녹음하기)
            case R.id.record:
                Intent record = new Intent(getApplicationContext(),Recording.class);
                startActivity(record);
        }
        return true;
    }

    //클릭한 버튼의 증상명과 데이터의 증상이 일치한지 확인
    public boolean checkSymptom(int index,String part){
        //버튼별 증상 배열
        String[] btnString = new String[]{"가래","복통","두통"};
        if(part.equals(btnString[index])) return true;
        return false;
    }
    public void createDCList(int index,ListView listView){
        //listView 참조 및 Adapter 연결
        DCListViewAdapter adapter = new DCListViewAdapter();
        //Adapter 지정
        listView.setAdapter(adapter);
            //데이터의 날짜가 datePicker의 두 날짜 사이에있으면 true 반환
        for(int i=0;i<Person1.symptom.length;i++){
            if (checkIsBetween(Person1.symptom[i].getDate())&&checkSymptom(index,Person1.symptom[i].getPart()))
                adapter.addItem(Person1.symptom[i].getDate(),
                        Person1.symptom[i].getPart() + Person1.symptom[i].getPain_level()
                        , Person1.symptom[i].getPart(), Person1.symptom[i].getPain_level(),
                        Person1.symptom[i].getPain_characteristics(), Person1.symptom[i].getPain_situation(),
                        Person1.symptom[i].getAccompany_pain(), Person1.symptom[i].getAdditional());
        }

        adapter.notifyDataSetChanged();
    }




    //데이터의 날짜가 datePicker의 두 날짜 사이에있으면 true 반환
    public boolean checkIsBetween(String date){
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

            if((CheckDate.before(EndDay)||CheckDate.equals(EndDay))&&!CheckDate.before(StartDay)){
                Log.d("myapp","통과됨");
                return true;
            }
            Log.d("myapp","통과되지 않음");
            return false;
        }catch (Exception e) {
            Log.d("myapp","예외 발생");
            return false;
        }

    }
}

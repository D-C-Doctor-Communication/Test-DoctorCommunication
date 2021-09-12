package com.example.doctorcommunication.SymptomRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.R;

import java.util.LinkedList;


public class Search extends AppCompatActivity {
    String[] lately_symptom;
    LinkedList<String> LATELY = new LinkedList<String>();

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        LinearLayout View = (LinearLayout)findViewById(R.id.lately_layout);
        TextView Search_click = (TextView) findViewById(R.id.search_text02);
        lately_symptom=new String[5];

        SharedPreferences sharedPreferences= getSharedPreferences("symptom", MODE_PRIVATE);    // test 이름의 기본모드 설정, 만약 test key값이 있다면 해당 값을 불러옴.
        String symptom = sharedPreferences.getString("inputText","");
        Log.e("증상", "저장완료 - "+symptom);    // SharedPreferences에 저장되어있던 값 찍기.

        if(LATELY.size()>=5){
            LATELY.addFirst(symptom);
            LATELY.removeLast();
        }else{
            LATELY.addFirst(symptom);
        }

        for(int i=0; i<LATELY.size(); i++){
            Button btn = new Button(this);
            btn.setText(LATELY.get(i));
            View.addView(btn);
        }


        //textview 클릭되면 다음 페이지로 넘기기
        Search_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("시작", "서치 페이지" );
                Intent intent = new Intent(Search.this, SearchList.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

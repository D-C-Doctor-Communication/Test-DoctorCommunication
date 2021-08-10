package com.example.doctorcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectBody_stomach extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_STOMACH =new int[9];
    String [] STOMACH ={"오른쪽 위 복부","명치","왼쪽 위 복부","오른쪽 복부","배꼽 부근","왼쪽 복부","오른쪽 아래 복부","왼쪽 아래 복부","아래쪽 복부"};
    List<String> BODY = new ArrayList<String>();
    String [] select_stomach;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_stomach);

        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;
        ImageButton stomach01 = (ImageButton)findViewById(R.id.stomach01) ;
        ImageButton stomach02 = (ImageButton)findViewById(R.id.stomach02) ;
        ImageButton stomach03 = (ImageButton)findViewById(R.id.stomach03) ;
        ImageButton stomach04 = (ImageButton)findViewById(R.id.stomach04) ;
        ImageButton stomach05 = (ImageButton)findViewById(R.id.stomach05) ;
        ImageButton stomach06 = (ImageButton)findViewById(R.id.stomach06) ;
        ImageButton stomach07 = (ImageButton)findViewById(R.id.stomach07) ;
        ImageButton stomach08 = (ImageButton)findViewById(R.id.stomach08) ;
        ImageButton stomach09 = (ImageButton)findViewById(R.id.stomach09);

        stomach01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[0]==0){
                    stomach01.setSelected(true);
                    CHECK_STOMACH[0]=1;
                }else {
                    stomach01.setSelected(false);
                    CHECK_STOMACH[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        stomach02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[1]==0){
                    stomach02.setSelected(true);
                    CHECK_STOMACH[1]=1;
                }else {
                    stomach02.setSelected(false);
                    CHECK_STOMACH[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        stomach03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[2]==0){
                    stomach03.setSelected(true);
                    CHECK_STOMACH[2]=1;
                }else {
                    stomach03.setSelected(false);
                    CHECK_STOMACH[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        stomach04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[3]==0){
                    stomach04.setSelected(true);
                    CHECK_STOMACH[3]=1;
                }else {
                    stomach04.setSelected(false);
                    CHECK_STOMACH[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        stomach05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[4]==0){
                    stomach05.setSelected(true);
                    CHECK_STOMACH[4]=1;
                }else {
                    stomach05.setSelected(false);
                    CHECK_STOMACH[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
        stomach06.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[5]==0){
                    stomach06.setSelected(true);
                    CHECK_STOMACH[5]=1;
                }else {
                    stomach06.setSelected(false);
                    CHECK_STOMACH[5]=0;
                }
                Log.e("ji", "click2");
            }
        });
        stomach07.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[6]==0){
                    stomach07.setSelected(true);
                    CHECK_STOMACH[6]=1;
                }else {
                    stomach07.setSelected(false);
                    CHECK_STOMACH[6]=0;
                }
                Log.e("ji", "click3");
            }
        });
        stomach08.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[7]==0){
                    stomach08.setSelected(true);
                    CHECK_STOMACH[7]=1;
                }else {
                    stomach08.setSelected(false);
                    CHECK_STOMACH[7]=0;
                }
                Log.e("ji", "click4");
            }
        });
        stomach09.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_STOMACH[8]==0){
                    stomach09.setSelected(true);
                    CHECK_STOMACH[8]=1;
                }else {
                    stomach09.setSelected(false);
                    CHECK_STOMACH[8]=0;
                }
                Log.e("ji", "click5");
            }
        });
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_stomach.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);

                if(stomach01.isSelected()){
                    BODY.add(STOMACH[0]);
                }
                if(stomach02.isSelected()){
                    BODY.add(STOMACH[1]);
                }
                if(stomach03.isSelected()){
                    BODY.add(STOMACH[2]);
                }
                if(stomach04.isSelected()){
                    BODY.add(STOMACH[3]);
                }
                if(stomach05.isSelected()){
                    BODY.add(STOMACH[4]);
                }
                if(stomach06.isSelected()){
                    BODY.add(STOMACH[5]);
                }
                if(stomach07.isSelected()){
                    BODY.add(STOMACH[6]);
                }
                if(stomach08.isSelected()){
                    BODY.add(STOMACH[7]);
                }
                if(stomach09.isSelected()){
                    BODY.add(STOMACH[8]);
                }
                select_stomach = BODY.toArray(new String[BODY.size()]);
                for(int i = 0; i< select_stomach.length; i++)
                    Log.e("jj", select_stomach[i]);
                startActivity(intent);
                finish();
            }
        });

        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_stomach.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });

    }
}

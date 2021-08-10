package com.example.doctorcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectBody_waist extends AppCompatActivity {
    String symptom;
    int part;
    int [] CHECK_WAIST =new int[5];
    String []WAIST={"왼쪽 옆구리","허리 중앙","오른쪽 옆구리","허리 아래","꼬리뼈"};
    List<String> BODY = new ArrayList<String>();
    String [] select_waist;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_waist);

        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;
        ImageButton waist01 = (ImageButton)findViewById(R.id.waist01) ;
        ImageButton waist02 = (ImageButton)findViewById(R.id.waist02) ;
        ImageButton waist03 = (ImageButton)findViewById(R.id.waist03) ;
        ImageButton waist04 = (ImageButton)findViewById(R.id.waist04) ;
        ImageButton waist05 = (ImageButton)findViewById(R.id.waist05) ;

        waist01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[0]==0){
                    waist01.setSelected(true);
                    CHECK_WAIST[0]=1;
                }else {
                    waist01.setSelected(false);
                    CHECK_WAIST[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        waist02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[1]==0){
                    waist02.setSelected(true);
                    CHECK_WAIST[1]=1;
                }else {
                    waist02.setSelected(false);
                    CHECK_WAIST[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        waist03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[2]==0){
                    waist03.setSelected(true);
                    CHECK_WAIST[2]=1;
                }else {
                    waist03.setSelected(false);
                    CHECK_WAIST[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        waist04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[3]==0){
                    waist04.setSelected(true);
                    CHECK_WAIST[3]=1;
                }else {
                    waist04.setSelected(false);
                    CHECK_WAIST[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        waist05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_WAIST[4]==0){
                    waist05.setSelected(true);
                    CHECK_WAIST[4]=1;
                }else {
                    waist05.setSelected(false);
                    CHECK_WAIST[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_waist.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);

                if(waist01.isSelected()){
                    BODY.add(WAIST[0]);
                }
                if(waist02.isSelected()){
                    BODY.add(WAIST[1]);
                }
                if(waist03.isSelected()){
                    BODY.add(WAIST[2]);
                }
                if(waist04.isSelected()){
                    BODY.add(WAIST[3]);
                }
                if(waist05.isSelected()){
                    BODY.add(WAIST[4]);
                }
                select_waist = BODY.toArray(new String[BODY.size()]);
                for(int i = 0; i< select_waist.length; i++)
                    Log.e("jj", select_waist[i]);
                startActivity(intent);
                finish();
            }
        });

        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_waist.this,SearchList.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });

    }
}

package com.example.doctorcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SelectBody_head extends AppCompatActivity {
    String symptom;
    int part;
    int []CHECK_HEAD=new int[5];
    String []HEAD={"눈주위","이마","관자놀이","머리 전체","뒷머리"};
    List<String> BODY = new ArrayList<String>();
    String []select_head;

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        part = intent.getExtras().getInt("part");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_body_head);

        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;
        ImageButton head01 = (ImageButton)findViewById(R.id.head01) ;
        ImageButton head02 = (ImageButton)findViewById(R.id.head02) ;
        ImageButton head03 = (ImageButton)findViewById(R.id.head03) ;
        ImageButton head04 = (ImageButton)findViewById(R.id.head04) ;
        ImageButton head05 = (ImageButton)findViewById(R.id.head05) ;

        head01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[0]==0){
                    head01.setSelected(true);
                    CHECK_HEAD[0]=1;
                }else {
                    head01.setSelected(false);
                    CHECK_HEAD[0]=0;
                }
                Log.e("ji", "click1");
            }
        });
        head02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[1]==0){
                    head02.setSelected(true);
                    CHECK_HEAD[1]=1;
                }else {
                    head02.setSelected(false);
                    CHECK_HEAD[1]=0;
                }
                Log.e("ji", "click2");
            }
        });
        head03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[2]==0){
                    head03.setSelected(true);
                    CHECK_HEAD[2]=1;
                }else {
                    head03.setSelected(false);
                    CHECK_HEAD[2]=0;
                }
                Log.e("ji", "click3");
            }
        });
        head04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[3]==0){
                    head04.setSelected(true);
                    CHECK_HEAD[3]=1;
                }else {
                    head04.setSelected(false);
                    CHECK_HEAD[3]=0;
                }
                Log.e("ji", "click4");
            }
        });
        head05.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(CHECK_HEAD[4]==0){
                    head05.setSelected(true);
                    CHECK_HEAD[4]=1;
                }else {
                    head05.setSelected(false);
                    CHECK_HEAD[4]=0;
                }
                Log.e("ji", "click5");
            }
        });
         nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_head.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);

                if(head01.isSelected()){
                    BODY.add(HEAD[0]);
                }
                if(head02.isSelected()){
                    BODY.add(HEAD[1]);
                }
                if(head03.isSelected()){
                    BODY.add(HEAD[2]);
                }
                if(head04.isSelected()){
                    BODY.add(HEAD[3]);
                }
                if(head05.isSelected()){
                    BODY.add(HEAD[4]);
                }
                select_head = BODY.toArray(new String[BODY.size()]);
                for(int i=0; i<select_head.length; i++)
                    Log.e("jj", select_head[i]);
                startActivity(intent);
                finish();
            }
        });

        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectBody_head.this, SearchList.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });

    }
}

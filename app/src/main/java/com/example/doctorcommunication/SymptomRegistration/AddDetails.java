package com.example.doctorcommunication.SymptomRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.MainActivity;
import com.example.doctorcommunication.R;

public class AddDetails extends AppCompatActivity{
    EditText add_details;
    String select_details; //선택한 추가증상
    String selected_symptom;//전페이지에서 받아온 동반 증상
    String[] selected_osymptom;
    String[] selected_body;
    String[] selected_pattern;
    String[] selected_worse;
    String selected_level;
    String selected_levelNm;
    int part;

    TextView osymptom;
    String symptom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_details);
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        selected_body = intent.getStringArrayExtra("bparts");
        part =intent.getExtras().getInt("part");
        selected_levelNm = intent.getExtras().getString("levelNm");
        selected_pattern = intent.getStringArrayExtra("pattern");
        selected_worse = intent.getStringArrayExtra("worse");
        selected_osymptom = intent.getStringArrayExtra("osymptom");


        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;
        ImageButton addpage = (ImageButton)findViewById(R.id.addpage) ;

        add_details = findViewById(R.id.add_details);
        osymptom=findViewById(R.id.leveltext2);

        selected_symptom= selected_osymptom[0];
        for(int i = 1; i< selected_osymptom.length; i++){
            if(selected_osymptom.length==2)
                selected_symptom+="/";
            selected_symptom+= selected_osymptom[i];
            if((i+1)!= selected_osymptom.length)
                selected_symptom+="/";
        }

        //받아온 동반 증상을 textview에 띄우기
        osymptom.setText(selected_symptom);
        addpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                select_details =add_details.getText().toString();
            }
        });

        addpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDetails.this, MainActivity.class);

                intent.putExtra("symptom",symptom);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("part",part);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("pattern",selected_pattern);
                intent.putExtra("worse",selected_worse);
                intent.putExtra("osymptom",selected_osymptom);
                intent.putExtra("details",select_details);

                startActivity(intent);
                finish();
            }
        });

        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDetails.this, OtherSymptom.class);
                intent.putExtra("symptom",symptom);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("part",part);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("pattern",selected_pattern);
                intent.putExtra("worse",selected_worse);

                startActivity(intent);
                finish();
            }
        });
    }
}

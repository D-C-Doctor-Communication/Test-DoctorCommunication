package com.example.doctorcommunication.SymptomRegistration;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.MainActivity;
import com.example.doctorcommunication.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;

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
    int repeat;
    TextView osymptom;
    String symptom;
    FirebaseAuth firebaseAuth;

    long now = System.currentTimeMillis();
    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Date date = new Date();
    String date_txt = sdf.format(date);

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
        repeat = intent.getExtras().getInt("repeat");

        firebaseAuth =  FirebaseAuth.getInstance();

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
                intent.putExtra("repeat",repeat);

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference myRef = database.getReference().child("users");

                FirebaseUser user = firebaseAuth.getCurrentUser();
                String uid = user.getUid();

                myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("symptom").setValue(symptom);
                myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("part").setValue(selected_body[0]);
                myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("painLevel").setValue(selected_levelNm);
                myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("pain_characteristics").setValue(selected_pattern[0]);
                myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("pain_situation").setValue(selected_worse[0]);
                myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("accompany_pain").setValue(selected_osymptom[0]);
                myRef.child(uid).child("date").child(date_txt).child(String.valueOf(repeat)).child("additional").setValue(select_details);

                SharedPreferences sharedPreferences= getSharedPreferences("symptom", MODE_PRIVATE);    // test 이름의 기본모드 설정
                SharedPreferences.Editor editor= sharedPreferences.edit(); //sharedPreferences를 제어할 editor를 선언
                editor.putString("inputText",symptom); // key,value 형식으로 저장
                editor.commit();    //최종 커밋. 커밋을 해야 저장이 된다.
                Log.e("증상", "증상저장2");
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
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
                intent.putExtra("repeat",repeat);
                intent.addFlags (Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
                finish();
            }
        });
    }
}

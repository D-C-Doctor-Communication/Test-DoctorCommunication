package com.example.doctorcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class AddDetails extends AppCompatActivity{
    EditText add_details;
    String details; //추가증상
    String select_symptom;
    TextView osymptom;
    String symptom;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_details);

        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        String[] select_other = intent.getStringArrayExtra("osymptom");


        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;
        ImageButton addpage = (ImageButton)findViewById(R.id.addpage) ;

        add_details = findViewById(R.id.add_details);
        osymptom=findViewById(R.id.leveltext2);

        select_symptom=select_other[0];
        for(int i=1; i<select_other.length; i++){
            if(select_other.length==2)
                select_symptom+="/";
            select_symptom+=select_other[i];
            if((i+1)!=select_other.length)
                select_symptom+="/";
        }

        osymptom.setText(select_symptom);
        addpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                details =add_details.getText().toString();
            }
        });

        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddDetails.this, OtherSymptom.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });
    }
}

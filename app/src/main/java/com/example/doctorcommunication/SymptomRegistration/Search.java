package com.example.doctorcommunication.SymptomRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.R;

public class Search extends AppCompatActivity {
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search);

        TextView Search_click = (TextView) findViewById(R.id.search_text02);

        //textview 클릭되면 다음 페이지로 넘기기
        Search_click.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Search.this, SearchList.class);
                startActivity(intent);
                finish();
            }
        });
    }

}

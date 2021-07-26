package com.example.doctorcommunication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


public class MeetingDoc extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.meeting_doctor);
        Log.d("myapp","의사와의 만남탭 열림");

        Toolbar toolbar = findViewById(R.id.toolbar_meeting_doctor);
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.meeting_doc_toolbar_menu,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case R.id.backToHome:
                Intent backToHome = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(backToHome);
            case R.id.record:
                Intent record = new Intent(getApplicationContext(),Recording.class);
                startActivity(record);
        }
        return true;
    }

}
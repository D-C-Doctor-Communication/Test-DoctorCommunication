package com.example.doctorcommunication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class SelectWorse extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    EditText add_worse;
    Button worse_btn;

    String symptom;
    String[] select_worse; //선택한 악화상황
    List<String> worse = new ArrayList<String>();
    int cnt=0;
    String p;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_worse);

        adapter = new ListViewAdapter();

        listView = (ListView)findViewById(R.id.osymptom_list);
        listView.setAdapter(adapter);
        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;

        add_worse = findViewById(R.id.add_osymptom);
        worse_btn =findViewById(R.id.osymptom_btn);

       nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectWorse.this, OtherSymptom.class);

                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count = adapter.getCount();
                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        cnt++;
                    }
                }
                select_worse= new String[cnt];
                cnt=0;
                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        select_worse[cnt++]=worse.get(i);
                    }
                }
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectWorse.this, SelectPattern.class);
                //Toast.makeText(getApplicationContext(),"다음페이지입니다.",Toast.LENGTH_LONG).show();
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });
        worse_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(add_worse.getText().toString());
                worse.add(add_worse.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        //TextView textView = (TextView)findViewById(R.id.testtext);
        // 선택한 증상

        //textView.setText(text);
        AssetManager assetManager = getAssets();
        try {
            InputStream is =assetManager.open("symptom.json");
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader reader = new BufferedReader(isr);

            StringBuffer buffer = new StringBuffer();

            String line = reader.readLine();

            while(line!=null){
                buffer.append(line+"\n");
                line=reader.readLine();
            }

            String jsonData = buffer.toString();

            JSONArray jsonArray= new JSONArray(jsonData);
            for(int i=0; i<jsonArray.length();i++){
                JSONObject jo=jsonArray.getJSONObject(i);
                String name= jo.getString("symptom");
                JSONArray jsonArray2 = jo.getJSONArray("worse");
                if(name.equals(symptom)){
                    for(int j=0; j<jsonArray2.length(); j++){
                        JSONObject jo2=jsonArray2.getJSONObject(j);
                        p = jo2.getString("w");
                        worse.add(p);
                        adapter.addItem(p);
                    }

                }
                //textView.setText(p);

                adapter.notifyDataSetChanged();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}

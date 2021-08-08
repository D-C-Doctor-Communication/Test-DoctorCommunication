package com.example.doctorcommunication;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.util.Log;
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

public class SelectPattern extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    EditText add_pattern;
    Button pattern_btn;

    String symptom;
    String[] select_pattern; //선택한 양상
    List<String> pattern = new ArrayList<String>();
    int cnt=0;
    String p;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_pattern);

        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.osymptom_list);
        listView.setAdapter(adapter);
        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;

        add_pattern = findViewById(R.id.add_osymptom);
        pattern_btn =findViewById(R.id.osymptom_btn);

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count = adapter.getCount();

                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        cnt++;
                    }
                }
                select_pattern= new String[cnt];
                Log.e("HI", String.valueOf(count));
                Log.e("HI", String.valueOf(cnt));
                cnt=0;
                Log.e("HI", String.valueOf(cnt));
                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        select_pattern[cnt]=pattern.get(i);
                        Log.e("HI", select_pattern[cnt]);
                        cnt++;
                    }
                }
                Intent intent = new Intent(SelectPattern.this, SelectWorse.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();

            }
        });

        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectPattern.this, SelectLevel.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });

        pattern_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(add_pattern.getText().toString());
                pattern.add(add_pattern.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

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
                JSONArray jsonArray2 = jo.getJSONArray("pattern");
                //pattern = new String[jsonArray2.length()];
                if(name.equals(symptom)){
                    for(int j=0; j<jsonArray2.length(); j++){
                        JSONObject jo2=jsonArray2.getJSONObject(j);
                        p = jo2.getString("p");
                        pattern.add(p);
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

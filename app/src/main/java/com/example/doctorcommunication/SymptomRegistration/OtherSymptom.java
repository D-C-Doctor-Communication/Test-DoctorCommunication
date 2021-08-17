package com.example.doctorcommunication.SymptomRegistration;

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

import com.example.doctorcommunication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class OtherSymptom extends AppCompatActivity {
    private ListView listView;
    private ListViewAdapter adapter;
    EditText add_pattern;
    Button osymptom_btn;

    String symptom;//선택한 증상
    String[] select_osymptom; // 선택한 동반 증상
    List<String> osymptom = new ArrayList<String>();
    int cnt=0;
    String p;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.other_symptom);

        adapter = new ListViewAdapter();
        listView = (ListView)findViewById(R.id.osymptom_list);
        listView.setAdapter(adapter);

        ImageButton nextpage = (ImageButton)findViewById(R.id.nextpage) ;
        ImageButton backpage = (ImageButton)findViewById(R.id.backpage) ;

        add_pattern = findViewById(R.id.add_osymptom);
        osymptom_btn =findViewById(R.id.osymptom_btn);

        //다음 페이지 버튼
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //선택된 리스트 확인
                SparseBooleanArray checkedItems = listView.getCheckedItemPositions();
                int count = adapter.getCount();
                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        cnt++;
                    }
                }
                select_osymptom= new String[cnt];
                cnt=0;
                //선택되어 있다면 select_osymptom 배열에 넣기
                for(int i=0; i<=count-1; i++){
                    if(checkedItems.get(i)){
                        select_osymptom[cnt++]=osymptom.get(i);
                    }
                }

                Intent intent = new Intent(OtherSymptom.this, AddDetails.class);
                intent.putExtra("symptom",symptom);
                for(int i=0; i<=cnt-1; i++)
                    intent.putExtra("osymptom",select_osymptom);

                startActivity(intent);
                finish();
            }
        });

        //뒤로가기 버튼
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(OtherSymptom.this, SelectWorse.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });

        //동반 증상 추가 버튼
        osymptom_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.addItem(add_pattern.getText().toString());
                osymptom.add(add_pattern.getText().toString());
                adapter.notifyDataSetChanged();
            }
        });

        //증상정리된 symptom.json 파일에서 선택한 증상의 동반 증상 가져오기
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
                JSONArray jsonArray2 = jo.getJSONArray("other_symptom");

                if(name.equals(symptom)){
                    for(int j=0; j<jsonArray2.length(); j++){
                        JSONObject jo2=jsonArray2.getJSONObject(j);
                        p = jo2.getString("symptom");
                        osymptom.add(p);
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

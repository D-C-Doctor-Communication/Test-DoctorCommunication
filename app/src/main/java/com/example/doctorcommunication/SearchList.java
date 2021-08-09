package com.example.doctorcommunication;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
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

public class SearchList extends AppCompatActivity {

    private ListView listView;
    private List<String> list;
    private ArrayList<String> nameArr;
    private SearchAdapter adapter;
    private EditText search_text;
    String[] symptom_Nm;
    int[] part_num;
    JSONObject jo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_list);
        search_text = (EditText)findViewById(R.id.search_text);
        listView = (ListView)findViewById(R.id.search_list);

        list = new ArrayList<String>();
        settingList();

        nameArr = new ArrayList<>();
        nameArr.addAll(list);

        adapter = new SearchAdapter(list,this);
        listView.setAdapter(adapter);

        search_text.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String content = search_text.getText().toString();
                search(content);
            }
        });
        listView.setOnItemClickListener(listener);
    }


    AdapterView.OnItemClickListener listener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Intent intent;

            Log.e("her!", String.valueOf(part_num[position])); //선택한 증상 부위 번호
            switch (part_num[position])
            {
                case 1 : {
                    Log.e("her!", "1번");
                    intent = new Intent(SearchList.this, SelectBody_head.class);
                    intent.putExtra("symptom", symptom_Nm[position]);
                    startActivity(intent);
                    break;
                }
                case 6 : {
                    Log.e("her!", "6번");
                    intent = new Intent(SearchList.this, SelectBody_waist.class);
                    intent.putExtra("symptom", symptom_Nm[position]);
                    startActivity(intent);
                    break;
                }
                case 8 : {
                    Log.e("her!", "8번");
                    intent = new Intent(SearchList.this, SelectBody_stomach.class);
                    intent.putExtra("symptom", symptom_Nm[position]);
                    startActivity(intent);
                    break;
                }
            }

            finish();
        }
    };

    public void Click_search_btn(View v) {
        String search_content = search_text.getText().toString();

    }
    public void search(String content){
        list.clear();

        if(content.length()== 0){
            list.addAll(nameArr);
        }else{
            for(int i=0; i<nameArr.size(); i++){
                if(nameArr.get(i).toLowerCase().contains(content))
                {
                    list.add(nameArr.get(i));
                }
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void settingList(){
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
            symptom_Nm = new String[jsonArray.length()];
            part_num= new int[jsonArray.length()];
            for(int i=0; i<jsonArray.length();i++){
                jo=jsonArray.getJSONObject(i);

                String name= jo.getString("symptom");
                int part = jo.getInt("part");

                symptom_Nm[i]=name;
                part_num[i]=part;

                list.add(name);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}

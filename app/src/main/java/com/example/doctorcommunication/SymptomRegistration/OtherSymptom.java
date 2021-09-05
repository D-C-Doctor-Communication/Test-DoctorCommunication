package com.example.doctorcommunication.SymptomRegistration;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.HomeScreen.Fragment_home;
import com.example.doctorcommunication.MainActivity;
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

    String symptom;
    String[] select_osymptom; // 선택한 동반 증상
    String[] selected_body;
    String[] selected_pattern;
    String[] selected_worse;
    String selected_level;
    String selected_levelNm;
    int part;
    List<String> osymptom = new ArrayList<String>();
    int cnt=0;
    String p;
    AlertDialog Dialog;

    @SuppressLint("WrongViewCast")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");
        selected_body = intent.getStringArrayExtra("bparts");
        part =intent.getExtras().getInt("part");
        selected_levelNm = intent.getExtras().getString("levelNm");
        selected_pattern = intent.getStringArrayExtra("pattern");
        selected_worse = intent.getStringArrayExtra("worse");

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

                //선택 X 팝업 처리
                if(select_osymptom.length==0){
                    new AlertDialog.Builder(OtherSymptom.this)
                            .setMessage("동반증상을 선택해주세요")
                            .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .show();
                    return;
                }

                //해당없음 팝업 처리(뒤 페이지인 추가증상 등록으로 넘어가지 않음)
                if(select_osymptom[0].equals("해당없음")) {

                    AlertDialog.Builder Dialog_bd = new AlertDialog.Builder(OtherSymptom.this);
                    Dialog_bd.setMessage("증상이 입력되었습니다.");

                    Dialog = Dialog_bd.create();
                    Dialog.show();

                    intent = new Intent(OtherSymptom.this, MainActivity.class);
                    Handler mHandler = new Handler();
                    mHandler.postDelayed(new Runnable() {
                        public void run() {
                            Dialog.dismiss();
                        }
                    }, 1000);

                }


                intent.putExtra("symptom",symptom);
                intent.putExtra("part",part);
                intent.putExtra("bparts",selected_body);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("pattern", selected_pattern);
                intent.putExtra("worse",selected_worse);
                intent.putExtra("osymptom", select_osymptom);

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
                intent.putExtra("bparts",selected_body);
                intent.putExtra("part",part);
                intent.putExtra("levelNm",selected_levelNm);
                intent.putExtra("pattern",selected_pattern);

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

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}

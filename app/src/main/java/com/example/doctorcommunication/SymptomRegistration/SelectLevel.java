package com.example.doctorcommunication.SymptomRegistration;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.doctorcommunication.R;

public class SelectLevel extends AppCompatActivity {
    String symptom;
    int part;
    SeekBar level;
    TextView level_text1;
    TextView level_text2;
    int num=0; //선택한 통증 레벨(숫자)
    String []level_text={"통증이 없음","괜찮은 통증","조금 아픈 통증","웬만한 통증","괴로운 통증",
            "매우 괴로운 통증","극심한 통증","매우 극심한 통증","끔찍한 통증","참을 수 없는 통증","상상할 수 없는 통증"}; // 통증 레벨 설명
    StringBuilder change_level;
    String select_level;//선택한 통증 레벨(설명)

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom"); //선택한 증상 받아오기
        part =intent.getExtras().getInt("part");
        Log.e("backpart", String.valueOf(part));
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level);

        level_text1 = (TextView) findViewById(R.id.level_text1);
        level_text2 = (TextView) findViewById(R.id.level_text2);
        level = (SeekBar) findViewById(R.id.level_seekbar);

        ImageButton nextpage = (ImageButton) findViewById(R.id.nextpage);
        ImageButton backpage = (ImageButton) findViewById(R.id.backpage);

        //통증 정도 선택
        level.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                num = level.getProgress();
                update();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                num=level.getProgress();
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                num=level.getProgress();
            }
        });

        //다음페이지 이동
        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLevel.this, SelectPattern.class);
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });
        //전 페이지로 이동
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;


                switch (part)  //선택한 증상 부위 번호
                {
                    case 1: {  //머리
                        Log.e("intentL", "1번");
                        intent = new Intent(SelectLevel.this, SelectBody_head.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        startActivity(intent);
                        break;
                    }
                    case 6: {  //허리
                        Log.e("intentL", "6번");
                        intent = new Intent(SelectLevel.this, SelectBody_waist.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);
                        startActivity(intent);
                        break;
                    }
                    case 8: {  //복부
                        Log.e("intentL", "8번");
                        intent = new Intent(SelectLevel.this, SelectBody_stomach.class);
                        intent.putExtra("symptom", symptom);
                        intent.putExtra("part", part);

                        startActivity(intent);
                        break;
                    }
                }
                finish();
            }
        });

    }

    //선택한 통증 정도 따른 텍스트
    private void update() {
        change_level = new StringBuilder().append(level_text[num]);
        select_level=change_level.toString();
        level_text1.setText(select_level);
        level_text2.setText(new StringBuilder().append(num));
    }

}

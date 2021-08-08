package com.example.doctorcommunication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectLevel extends AppCompatActivity {
    String symptom;
    SeekBar level;
    TextView level_text1;
    TextView level_text2;
    int num=0;
    String []level_text={"통증이 없음","괜찮은 통증","조금 아픈 통증","웬만한 통증","괴로운 통증",
            "매우 괴로운 통증","극심한 통증","매우 극심한 통증","끔찍한 통증","참을 수 없는 통증","상상할 수 없는 통증"};

    StringBuilder change_level;
    String select_level;//선택한 통증 레벨

    public void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        symptom = intent.getExtras().getString("symptom");

        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_level);

        level_text1 = (TextView) findViewById(R.id.level_text1);
        level_text2 = (TextView) findViewById(R.id.level_text2);
        level = (SeekBar) findViewById(R.id.level_seekbar);
        ImageButton nextpage = (ImageButton) findViewById(R.id.nextpage);
        ImageButton backpage = (ImageButton) findViewById(R.id.backpage);



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

        nextpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLevel.this, SelectPattern.class);
                //Toast.makeText(getApplicationContext(),"다음페이지입니다.",Toast.LENGTH_LONG).show();
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });
        backpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SelectLevel.this, SelectBody_head.class);
                //Toast.makeText(getApplicationContext(),"다음페이지입니다.",Toast.LENGTH_LONG).show();
                intent.putExtra("symptom",symptom);
                startActivity(intent);
                finish();
            }
        });

    }

    private void update() {
        change_level = new StringBuilder().append(level_text[num]);
        select_level=change_level.toString();
        level_text1.setText(select_level);
        level_text2.setText(new StringBuilder().append(num));
    }

}

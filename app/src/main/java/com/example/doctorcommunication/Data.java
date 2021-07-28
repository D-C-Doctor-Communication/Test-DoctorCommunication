package com.example.doctorcommunication;

import java.util.Date;

public class Data{
    String date; //Date클래스 형식을 String형으로 변환
    String part; //부위
    String pain_level; //통증 정도
    String pain_characteristics; //통증 양상
    String pain_situation; //악화 상황
    String accompany_pain; //동반 증상
    String additional; //추가사항


    public Data(String date,String part,String pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain){
        this.date = date;
        this.part = part;
        this.pain_level = pain_level;
        this.pain_characteristics = pain_characteristics;
        this.pain_situation = pain_situation;
        this.accompany_pain = accompany_pain;
        this.additional = "해당 없음";

    }
    public Data(String date,String part,String pain_level,String pain_characteristics
            ,String pain_situation,String accompany_pain,String additional){
        super();
        this.additional = additional;
    }
}
class person1{
    Data symptom1 = new Data("2021.07.01","가래","목","투명하거나 하얀 가래","시간이 갈수록","목통증");
    Data symptom2 = new Data("2021.07.03","복통","배","쿡쿡 찌르듯이","시간이 갈수록","피로","몸살이 난것처럼 몸에 힘이 나지 않았다.");
    Data symptom3 = new Data("2021.07.03","두통","머리","지끈지끈한","피곤하면","발열");
    Data symptom4 = new Data("2021.07.06","복통","배","더부룩하며 팽창","음식을 섭취하고 난뒤","피로");
    Data symptom5 = new Data("2021.07.11","두통","머리","조이는 듯한","기침하면","구토","구토감이 들어 새벽에 잠에서 깼다.");
    Data symptom6 = new Data("2021.07.11","가래","목","노란 가래","아침(오전)","기침");
    Data symptom7 = new Data("2021.07.12","가래","목","노란 가래","아침(오전)","목통증");
    Data symptom8 = new Data("2021.07.26","복통","배","날카로운 통증","시간이 갈수록","피로","하루종일 지속되었다.");
    Data symptom9 = new Data("2021.07.27","복통","배","쿡쿡 찌르듯이","시간이 갈수록","기침");
    Data symptom10 = new Data("2021.07.27","두통","머리","단순 통증","피곤하면","메스꺼움");
    Data symptom11 = new Data("2021.07.27","가래","목","투명하거나 하얀 가래","시간이 갈수록","두통","차가운 물을 마시면 심해진다.");
    Data symptom12 = new Data("2021.08.01","가래","목","투명하거나 하얀 가래","시간이 갈수록","피로");
}

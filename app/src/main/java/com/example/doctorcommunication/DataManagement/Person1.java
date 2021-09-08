package com.example.doctorcommunication.DataManagement;

import com.example.doctorcommunication.DataManagement.Symptom;
import com.example.doctorcommunication.DataManagement.memo;

//임의의 데이터 저장
public class Person1{
    //증상등록
    public static Symptom[] symptom = {
            new Symptom("2021.07.01","두통","머리","2","투명하거나 하얀 가래","시간이 갈수록","목통증"),
            new Symptom("2021.08.01","복통","배","4","쿡쿡 찌르듯이","시간이 갈수록","피로","몸살이 난것처럼 몸에 힘이 나지 않았다."),
            new Symptom("2021.08.02","두통","머리","6","지끈지끈한","피곤하면","발열"),
            new Symptom("2021.08.03","복통","배","3","더부룩하며 팽창","음식을 섭취하고 난뒤","피로"),
            new Symptom("2021.08.09","두통","머리","9","조이는 듯한","기침하면","구토","구토감이 들어 새벽에 잠에서 깼다."),
            new Symptom("2021.08.10","가래","목","10","노란 가래","아침(오전)","기침"),
            new Symptom("2021.08.11","가래","목","4","노란 가래","아침(오전)","목통증"),
            new Symptom("2021.08.20","복통","배","6","날카로운 통증","시간이 갈수록","피로","하루종일 지속되었다."),
            new Symptom("2021.08.21","복통","배","8","쿡쿡 찌르듯이","시간이 갈수록","기침"),
            new Symptom("2021.08.22","두통","머리","2","단순 통증","피곤하면","메스꺼움"),
            new Symptom("2021.08.26","가래","목","4","투명하거나 하얀 가래","시간이 갈수록","두통","차가운 물을 마시면 심해진다."),
            new Symptom("2021.09.07","가래","목","6","투명하거나 하얀 가래","시간이 갈수록","피로"),
    };
    //메모
    public static memo[] memos = {
            new memo("2021.8.8","점심 저녁으로 약을 먹고 물을 충분히 마신다."),
            new memo("2021.8.29","자기전에 간단한 스트레칭을 한다."),
            new memo("2021.8.12","따뜻한 물을 마실것."),
            new memo("2021.9.8",""),
            new memo("2021.7.1","")
    };
    //병원진료|검사 예약
    //날짜 이름 시간 장소 종류
    public static Appointment[] appointments = {
            new Appointment("2021.8.6","건강검진","16:00","서울대학병원","검사"),
            new Appointment("2021.8.19","물리치료","18:00","정형외과","진료"),
            new Appointment("2021.8.19","물리9","19:00","정형외과","검사")
    };
}
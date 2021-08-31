package com.example.doctorcommunication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    EditText mEmailText, mPasswordText, mPasswordcheckText, mName;
    Button mregisterBtn;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference mDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);

        //액션 바 등록하기
        //ActionBar actionBar = getSupportActionBar();
        // actionBar.setTitle("뒤로가기");
        //actionBar.setDisplayHomeAsUpEnabled(true); //뒤로가기버튼

        //파이어베이스 접근 설정
        // user = firebaseAuth.getCurrentUser();
        firebaseAuth =  FirebaseAuth.getInstance();
        // firebaseDatabase = FirebaseDatabase.getInstance().getReference();

        mName = (EditText)findViewById(R.id.nameEt);
        mEmailText = (EditText)findViewById(R.id.emailEt);
        mPasswordText = (EditText)findViewById(R.id.passwordEdt);
        mPasswordcheckText = (EditText)findViewById(R.id.passwordcheckEdt);
        mregisterBtn = (Button)findViewById(R.id.register2_btn);

        // 데이터 읽고 쓰기 // firebase 정의
        mDatabase = FirebaseDatabase.getInstance().getReference();

        readUser();

        // 파이어베이스 user로 접근
        // 가입버튼 ->  firebase에 데이터 저장
        mregisterBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //가입 정보 가져오기
                final String email = mEmailText.getText().toString().trim();
                String pwd = mPasswordText.getText().toString().trim();
                String pwdcheck = mPasswordcheckText.getText().toString().trim();

                if(pwd.equals(pwdcheck)) {
                    Log.d(TAG, "등록 버튼 " + email + " , " + pwd);
                    final ProgressDialog mDialog = new ProgressDialog(RegisterActivity.this);
                    mDialog.setMessage("가입중입니다...");
                    mDialog.show();

                    // 파이어베이스에 신규계정 등록
                    firebaseAuth.createUserWithEmailAndPassword(email, pwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            // 가입 성공시
                            if (task.isSuccessful()) {
                                mDialog.dismiss();

                                FirebaseUser user = firebaseAuth.getCurrentUser();
                                String email = user.getEmail();
                                //String uid = user.getUid();
                                String name = mName.getText().toString().trim();

                                // 해쉬맵 테이블을 파이어베이스 데이터베이스에 저장
                                HashMap<Object,String> hashMap = new HashMap<>();

                               // hashMap.put("uid",uid);
                                hashMap.put("name",name);
                                hashMap.put("email",email);

                                writeNewUser("1", name, email);

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference("Users");
                                //reference.child(uid).setValue(hashMap);

                                // 가입이 이루어져을시 가입 화면을 빠져나감
                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                                Toast.makeText(RegisterActivity.this, "회원가입에 성공하셨습니다.", Toast.LENGTH_SHORT).show();

                            } else {
                                mDialog.dismiss();
                                Toast.makeText(RegisterActivity.this, "이미 존재하는 아이디입니다.", Toast.LENGTH_SHORT).show();
                                return;  // 해당 메소드 진행을 멈추고 빠져나감.

                            }

                        }
                    });
                    //비밀번호 오류시
                }else{
                    Toast.makeText(RegisterActivity.this, "비밀번호가 틀렸습니다. 다시 입력해 주세요.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }

    private void writeNewUser(String userId, String name, String email){
        User user = new User(name, email);
        mDatabase.child("user").child(userId).setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        System.out.println("회원정보 저장 완료");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("회원정보 저장 실패");
                    }
                });
    }

    private void readUser(){
        mDatabase.child("users").child("1").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.getValue(User.class) != null){
                    User post = snapshot.getValue(User.class);
                    Log.w("FireBaseData", "getData"+post.toString());
                }
                else{
                    Toast.makeText(RegisterActivity.this, "데이터가 없습니다.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w("FireBaseData", "loadPost:onCancelled", error.toException());
            }
        });
    }
    /*public boolean onSupportNavigateUp(){
        onBackPressed();; // 뒤로가기 버튼이 눌렸을시
        return super.onSupportNavigateUp(); // 뒤로가기 버튼
    }*/
}

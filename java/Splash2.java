package com.example.admin.agency;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

// '로그인 화면 -> 메인 화면' 이어주는 역할
public class Splash2 extends AppCompatActivity {
    // AsyncAPI, AsyncPerforInfo 에서 context 사용 가능하도록 선언
    public static Context context2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash2);

        // 로그인 후 전달받은 사용자 정보
        Intent intent = getIntent();
        String userID =  intent.getStringExtra("userID");
        String userName = intent.getStringExtra("userName");
        Integer total = intent.getIntExtra("total", 0);
        Integer aqua = intent.getIntExtra("aqua", 0);
        Integer museum = intent.getIntExtra("museum", 0);
        Integer art = intent.getIntExtra("art", 0);

        context2 = getApplicationContext();

        // 공연/전시 정보 openAPI 연결Log
        AsyncAPI apiList = new AsyncAPI();
        apiList.setUserInfo(userID, userName, total, aqua, museum, art);
        apiList.setPreActivitySplash(true);
        apiList.execute("");

    }

}


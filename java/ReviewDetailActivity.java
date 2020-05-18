package com.example.admin.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReviewDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);

        TextView idDetail = (TextView) findViewById(R.id.idDetail);
        TextView titleDetail = (TextView) findViewById(R.id.titleDetail);
        TextView userNameDetail = (TextView) findViewById(R.id.userNameDetail);
        TextView contentsDetail = (TextView) findViewById(R.id.contentsDetail);

        Intent sendInfoIntent = getIntent();
        idDetail.setText(sendInfoIntent.getStringExtra("id"));
        titleDetail.setText(sendInfoIntent.getStringExtra("title"));
        userNameDetail.setText(sendInfoIntent.getStringExtra("userName"));
        contentsDetail.setText(sendInfoIntent.getStringExtra("contents"));

        ActionBar actionBar = getSupportActionBar();
        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }
}

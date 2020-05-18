package com.example.admin.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

public class MyInfoActivity extends AppCompatActivity {

    ImageView userImage,achieve1,achieve2;
    TextView userName;
    int allCount,aquaCount,museumCount,artCount;
    ImageView allAchieve1,allAchieve2,allAchieve3,allAchieve4,allAchieve5;
    ImageView aquaAchieve1,aquaAchieve2,aquaAchieve3,aquaAchieve4,aquaAchieve5;
    ImageView museumAchieve1,museumAchieve2,museumAchieve3,museumAchieve4,museumAchieve5;
    ImageView artAchieve1,artAchieve2,artAchieve3,artAchieve4,artAchieve5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_info);

        userImage = (ImageView) findViewById(R.id.userImage);
        achieve1 = (ImageView) findViewById(R.id.achieve1);
        achieve2 = (ImageView) findViewById(R.id.achieve2);
        userName = (TextView) findViewById(R.id.reviewID);
        allAchieve1 = (ImageView) findViewById(R.id.allAchieve1);
        allAchieve2 = (ImageView) findViewById(R.id.allAchieve2);
        allAchieve3 = (ImageView) findViewById(R.id.allAchieve3);
        allAchieve4 = (ImageView) findViewById(R.id.allAchieve4);
        allAchieve5 = (ImageView) findViewById(R.id.allAchieve5);
        aquaAchieve1 = (ImageView) findViewById(R.id.aquaAchieve1);
        aquaAchieve2 = (ImageView) findViewById(R.id.aquaAchieve2);
        aquaAchieve3 = (ImageView) findViewById(R.id.aquaAchieve3);
        aquaAchieve4 = (ImageView) findViewById(R.id.aquaAchieve4);
        aquaAchieve5 = (ImageView) findViewById(R.id.aquaAchieve5);
        museumAchieve1 = (ImageView) findViewById(R.id.museumAchieve1);
        museumAchieve2 = (ImageView) findViewById(R.id.museumAchieve2);
        museumAchieve3 = (ImageView) findViewById(R.id.museumAchieve3);
        museumAchieve4 = (ImageView) findViewById(R.id.museumAchieve4);
        museumAchieve5 = (ImageView) findViewById(R.id.museumAchieve5);
        artAchieve1 = (ImageView) findViewById(R.id.artAchieve1);
        artAchieve2 = (ImageView) findViewById(R.id.artAchieve2);
        artAchieve3 = (ImageView) findViewById(R.id.artAchieve3);
        artAchieve4 = (ImageView) findViewById(R.id.artAchieve4);
        artAchieve5 = (ImageView) findViewById(R.id.artAchieve5);

        Intent myIntent = getIntent();

        if (myIntent != null) {
            userName.setText(myIntent.getStringExtra("name"));
            allCount = myIntent.getIntExtra("total", 0);
            aquaCount = myIntent.getIntExtra("aqua", 0);
            museumCount = myIntent.getIntExtra("museum", 0);
            artCount = myIntent.getIntExtra("art", 0);
            if(allCount >= 50)
                allAchieve1.setImageResource(R.drawable.ok1);
            if(allCount >= 100)
                allAchieve2.setImageResource(R.drawable.ok2);
            if(allCount >= 150)
                allAchieve3.setImageResource(R.drawable.ok3);
            if(allCount >= 200)
                allAchieve4.setImageResource(R.drawable.ok4);
            if(allCount >= 300)
                allAchieve5.setImageResource(R.drawable.ok5);
            if(aquaCount >= 10)
                aquaAchieve1.setImageResource(R.drawable.ok1);
            if(aquaCount >= 30)
                aquaAchieve2.setImageResource(R.drawable.ok2);
            if(aquaCount >= 50)
                aquaAchieve3.setImageResource(R.drawable.ok3);
            if(aquaCount >= 70)
                aquaAchieve4.setImageResource(R.drawable.ok4);
            if(aquaCount >= 100)
                aquaAchieve5.setImageResource(R.drawable.ok5);
            if(museumCount >= 10)
                museumAchieve1.setImageResource(R.drawable.ok1);
            if(museumCount >= 30)
                museumAchieve2.setImageResource(R.drawable.ok2);
            if(museumCount >= 50)
                museumAchieve3.setImageResource(R.drawable.ok3);
            if(museumCount >= 70)
                museumAchieve4.setImageResource(R.drawable.ok4);
            if(museumCount >= 100)
                museumAchieve5.setImageResource(R.drawable.ok5);
            if(artCount >= 10)
                artAchieve1.setImageResource(R.drawable.ok1);
            if(artCount >= 30)
                artAchieve2.setImageResource(R.drawable.ok2);
            if(artCount >= 50)
                artAchieve3.setImageResource(R.drawable.ok3);
            if(artCount >= 70)
                artAchieve4.setImageResource(R.drawable.ok4);
            if(artCount >= 100)
                artAchieve5.setImageResource(R.drawable.ok5);

        }

        ActionBar actionBar = getSupportActionBar();
        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}

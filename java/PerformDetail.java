package com.example.admin.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by a on 2018-06-17.
 */

public class PerformDetail extends AppCompatActivity{
    ImageView imageView;
    TextView nameTextView, startDayTextView, endDayTextView, placeTextView, priceTextView;
    ImageButton heartBtn, colorHeartBtn;
    Button writeReviewBtn;

    // 되돌아가기 눌렀을 때 메인 페이지로 이동
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perform_detail);

        imageView = (ImageView) findViewById(R.id.imageView);
        nameTextView = (TextView) findViewById(R.id.nameTextView);
        startDayTextView = (TextView) findViewById(R.id.startDayTextView);
        endDayTextView = (TextView) findViewById(R.id.endDayTextView);
        placeTextView = (TextView) findViewById(R.id.placeTextView);
        priceTextView = (TextView) findViewById(R.id.priceTextView);
        heartBtn = (ImageButton) findViewById(R.id.imageButton);
        colorHeartBtn = (ImageButton) findViewById(R.id.imageButton2);
        writeReviewBtn = (Button) findViewById(R.id.writeReviewBtn);

        //인텐트 정보를 가져와서 저장
        final Intent intent = getIntent();
        String url = intent.getStringExtra("imgUrl");

        final ImageSetting asyncTask = new ImageSetting();
        asyncTask.imageView(imageView);
        asyncTask.execute(url);

        imageView.setImageBitmap(asyncTask.getBitmap());
        nameTextView.setText(intent.getStringExtra("perforname"));
        startDayTextView.setText(intent.getStringExtra("startDay"));
        endDayTextView.setText(intent.getStringExtra("endDay"));
        placeTextView.setText(intent.getStringExtra("place"));
        priceTextView.setText(intent.getStringExtra("price"));

        // 북마크 표시
        // 빈 하트 클릭 -> 빨간 하트로 변경
        heartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                heartBtn.setVisibility(View.GONE);
                colorHeartBtn.setVisibility(View.VISIBLE);
            }
        });
        // 빨간 하트 클릭 -> 빈 하트로 변경
        colorHeartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                colorHeartBtn.setVisibility(View.GONE);
                heartBtn.setVisibility(View.VISIBLE);
            }
        });

        // 리뷰 작성 페이지로 이동
        writeReviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(getApplicationContext(), WriteReviewActivity.class);
                intent1.putExtra("img", asyncTask.getBitmap());
                intent1.putExtra("writer",intent.getStringExtra("writer"));
                intent1.putExtra("perforname", intent.getStringExtra("perforname"));
                intent1.putExtra("startDay", intent.getStringExtra("startDay"));
                intent1.putExtra("endDay", intent.getStringExtra("endDay"));
                intent1.putExtra("place", intent.getStringExtra("place"));
                intent1.putExtra("price", intent.getStringExtra("price"));
                startActivity(intent1);
            }
        });

    }
}

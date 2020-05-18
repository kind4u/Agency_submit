package com.example.admin.agency;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class WriteReviewActivity extends AppCompatActivity {
    String writer;
    LinearLayout itemLayout;

    ImageView perforImg;
    TextView perfornameTextView;
    Button okBtn;
    Button cancelBtn;

    int img;
    Bitmap thumbnail;
    String performName;
    String startDay;
    String endDay;
    String place;
    String price;

    @Override
    public void onBackPressed() {
        super.onBackPressed();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);

        perforImg = (ImageView) findViewById(R.id.perforImg);
        okBtn = (Button) findViewById(R.id.submitBtn);
        cancelBtn = (Button) findViewById(R.id.cancelBtn);

        // SingerDetail에서 인텐트 받아오기
        Intent intent = getIntent();
        thumbnail = intent.getParcelableExtra("img");
        writer = intent.getStringExtra("writer");
        performName = intent.getStringExtra("perforname");
        startDay = intent.getStringExtra("startDay");
        endDay = intent.getStringExtra("endDay");
        place = intent.getStringExtra("place");
        price = intent.getStringExtra("price");

        perforImg.setImageBitmap(thumbnail);

        perfornameTextView = (TextView) findViewById(R.id.perfornameTextView);
        perfornameTextView.setText(performName);

        final EditText reviewTitle = (EditText) findViewById(R.id.reviewTitle);
        final EditText reviewContents = (EditText) findViewById(R.id.reviewContents);

        Button submitReviewButton = (Button) findViewById(R.id.submitReviewButton);

        submitReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String rvTitle = reviewTitle.getText().toString();
                String rvContents = reviewContents.getText().toString();
                String rvName = writer;

                Response.Listener<String> reviewResponseListener = new Response.Listener<String>()  {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteReviewActivity.this);
                                builder.setMessage("후기를 정상적으로 작성 완료했습니다.")
                                        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                setResult(RESULT_OK);
                                                WriteReviewActivity.this.finish();
                                            }
                                        })
                                        .create()
                                        .show();
                            } else {
                                AlertDialog.Builder builder = new AlertDialog.Builder(WriteReviewActivity.this);
                                builder.setMessage("후기 등록에 실패하셨습니다.")
                                        .setNegativeButton("다시 시도", null)
                                        .create()
                                        .show();
                            }
                        }   catch(Exception e)  {
                            e.printStackTrace();
                        }
                    }
                };
                WriteRequest writeRequest = new WriteRequest(rvTitle, rvName, rvContents, reviewResponseListener);
                RequestQueue queue = Volley.newRequestQueue(WriteReviewActivity.this);
                queue.add(writeRequest);
            }
        });

        ActionBar actionBar = getSupportActionBar();
        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // 취소 버튼 클릭
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent backIntent = new Intent(getApplicationContext(), SingerDetail.class);
//                startActivity(backIntent);
                onBackPressed();
            }
        });


    }
}

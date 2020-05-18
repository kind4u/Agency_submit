package com.example.admin.agency;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class UserInfoActivity extends AppCompatActivity {

    ImageView userImage,achieve1,achieve2;
    TextView userName;
    int totalCount,aquaCount,museumCount,artCount;
    ImageView allAchieve,aquaAchieve,museumAchieve,artAchieve;
    ListView listView;
    String myName, userStringName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        userImage = (ImageView) findViewById(R.id.userImage);
        achieve1 = (ImageView) findViewById(R.id.achieve1);
        achieve2 = (ImageView) findViewById(R.id.achieve2);
        userName = (TextView) findViewById(R.id.reviewID);
        allAchieve = (ImageView) findViewById(R.id.allAchieve);
        aquaAchieve = (ImageView) findViewById(R.id.aquaAchieve);
        museumAchieve = (ImageView) findViewById(R.id.museumAchieve);
        artAchieve = (ImageView) findViewById(R.id.artAchieve);
        ListView listView = (ListView) findViewById(R.id.listView);
        Button searchListButton = (Button) findViewById(R.id.searchListButton);

        Intent userIntent = getIntent();
        myName = userIntent.getStringExtra("myName");
        userStringName = userIntent.getStringExtra("name");

        if(userIntent != null)  {
            userName.setText(userStringName);
            totalCount = userIntent.getIntExtra("total", 0);
            aquaCount = userIntent.getIntExtra("aqua", 0);
            museumCount = userIntent.getIntExtra("museum", 0);
            artCount = userIntent.getIntExtra("art", 0);

            if(totalCount >= 50)
                allAchieve.setImageResource(R.drawable.ok1);
            if(totalCount >= 100)
                allAchieve.setImageResource(R.drawable.ok2);
            if(totalCount >= 150)
                allAchieve.setImageResource(R.drawable.ok3);
            if(totalCount >= 200)
                allAchieve.setImageResource(R.drawable.ok4);
            if(totalCount >= 300)
                allAchieve.setImageResource(R.drawable.ok5);
            if(aquaCount >= 10)
                aquaAchieve.setImageResource(R.drawable.ok1);
            if(aquaCount >= 30)
                aquaAchieve.setImageResource(R.drawable.ok2);
            if(aquaCount >= 50)
                aquaAchieve.setImageResource(R.drawable.ok3);
            if(aquaCount >= 70)
                aquaAchieve.setImageResource(R.drawable.ok4);
            if(aquaCount >= 100)
                aquaAchieve.setImageResource(R.drawable.ok5);
            if(museumCount >= 10)
                museumAchieve.setImageResource(R.drawable.ok1);
            if(museumCount >= 30)
                museumAchieve.setImageResource(R.drawable.ok2);
            if(museumCount >= 50)
                museumAchieve.setImageResource(R.drawable.ok3);
            if(museumCount >= 70)
                museumAchieve.setImageResource(R.drawable.ok4);
            if(museumCount >= 100)
                museumAchieve.setImageResource(R.drawable.ok5);
            if(artCount >= 10)
                artAchieve.setImageResource(R.drawable.ok1);
            if(artCount >= 30)
                artAchieve.setImageResource(R.drawable.ok2);
            if(artCount >= 50)
                artAchieve.setImageResource(R.drawable.ok3);
            if(artCount >= 70)
                artAchieve.setImageResource(R.drawable.ok4);
            if(artCount >= 100)
                artAchieve.setImageResource(R.drawable.ok5);
        }

        searchListButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UserReviewTask().execute();
            }
        });
        ActionBar actionBar = getSupportActionBar();
        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);
    }

    class UserReviewTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://1.255.57.7/List.php";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent sendReviewIntent = new Intent(UserInfoActivity.this, ReviewActivity.class);
            sendReviewIntent.putExtra("userList",result);
            sendReviewIntent.putExtra("name",myName);
            sendReviewIntent.putExtra("search",userStringName);
            UserInfoActivity.this.startActivity(sendReviewIntent);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }   catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
    }   //end of ReviewTask
}

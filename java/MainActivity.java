package com.example.admin.agency;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    final int viewCount = 10;

    private BackPressCloseHandler backPressCloseHandler;
    DrawerLayout drawer;
    ActionBarDrawerToggle toggle;

    String setUserID;
    String setUserName;
    Integer setTotal;
    Integer setAqua;
    Integer setMuseum;
    Integer setArt;

    ListView listView;
    ListViewAdapter adapter;

    //가수 앨범을 담을 리스트(SingerItem 객체를 담아둘 ArrayList 생성)
    ArrayList<PerformItem> performance = new ArrayList<PerformItem>();

    @Override
    protected void onRestart() {
        super.onRestart();
    }

    // 액션바에 검색 버튼 배치
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search_item, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Splash2 로부터 전달받은 사용자 정보
        Intent intent = getIntent();
        setUserID =  intent.getStringExtra("userID");
        setUserName =  intent.getStringExtra("userName");
        setTotal = intent.getIntExtra("total", 0);
        setAqua = intent.getIntExtra("aqua", 0);
        setMuseum = intent.getIntExtra("museum", 0);
        setArt = intent.getIntExtra("art", 0);

        final Intent apiIntent = getIntent();
        apiIntent.getExtras();
        ArrayList<String> titleList = ((ArrayList<String>)apiIntent.getSerializableExtra("titleList"));
        ArrayList<String> startList = ((ArrayList<String>)apiIntent.getSerializableExtra("startList"));
        ArrayList<String> endList = ((ArrayList<String>)apiIntent.getSerializableExtra("endList"));
        ArrayList<String> placeList = ((ArrayList<String>)apiIntent.getSerializableExtra("placeList"));
        final ArrayList<String> seqList = ((ArrayList<String>)apiIntent.getSerializableExtra("seq"));
        final ArrayList<String> thumbList = ((ArrayList<String>)apiIntent.getSerializableExtra("thumbList"));

        for(int i=0; i<viewCount; i++) {
            performance.add(new PerformItem(thumbList.get(i), titleList.get(i), startList.get(i), endList.get(i), placeList.get(i)));
        }

        // 어댑터 객체 생성
        adapter = new ListViewAdapter(this, R.layout.perform_item, performance);

        //listView 레이아웃 참조
        listView = (ListView) findViewById(R.id.listView);

        //어댑터 객체를 리스트 뷰에 설정
        listView.setAdapter(adapter);


        //리스트뷰에서 아이템 클릭시 이벤트 처리
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* params
               - parent : 클릭한 아이템을 포함하는 부모 뷰(ListView)
               - view : 클릭한 항목의 View
               - position : 클릭한 아이템의 Adepter에서의 위치값(0, 1, 2,...)
               - id : DB를 사용했을 때 Cursor의 id 값값
            */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // 공연/전시 일련 번호 받아오기
                String seq = seqList.get(position);

                // 클릭한 공연/전시의 정보를 받아 ListItem 객체 생성
                ListItem listItem = new ListItem(thumbList.get(position), performance.get(position).name, performance.get(position).place,
                        performance.get(position).startDay, performance.get(position).endDay);

                // 클릭한 전시/공연의 일련번호를 사용해 상세정보를 표현하는 정보를 가져오는 Asynctask 작업 실행
                AsyncPerforInfo asyncPerforInfo = new AsyncPerforInfo();

                // 선택한 전시/공연의 내용을 전달
                asyncPerforInfo.setListItem(listItem);
                asyncPerforInfo.execute(seq,setUserName);

            }
        });//end of setOnItemClickListener

        //커스텀 타이틀을 사용하기 때문에 ActionBar()에 기본 타이틀을 표시하지 않도록 false로 지정
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        //DrawerLayout 참조
        drawer=(DrawerLayout)findViewById(R.id.main_drawer);

        /* ActionBarDrawerToggle 생성
           - 3, 4번째 매개변수:ActionBarDrawerToggle가 열린 상태, 닫힌 상태를 표현하기 위한 문자열(임의의 문자열 지정)
           - 하지만 화면 출력과 무관함으로 임의로 작성하면 됨(sting.xml)
        */
        toggle = new ActionBarDrawerToggle(this, drawer, R.string.drawer_open, R.string.drawer_close);

        //ActionBar(ToolBar)에서 기본 홈 버튼(<-)을 사용 가능하도록 설정
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //ActionBarDrawerToggle 의 상태를 sync(동기화)
        toggle.syncState();

        //NavigationView 참조
        NavigationView navigationView=(NavigationView)findViewById(R.id.main_drawer_view);

        //navigationView에 이벤트 설정
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id=item.getItemId();

                if(id==R.id.perforListTab){
                    showToast("내 정보");
                    Intent myIntent = new Intent(MainActivity.this, MyInfoActivity.class);
                    myIntent.putExtra("name", setUserName);
                    myIntent.putExtra("total", setTotal);
                    myIntent.putExtra("aqua", setAqua);
                    myIntent.putExtra("museum", setMuseum);
                    myIntent.putExtra("art", setArt);
                    startActivity(myIntent);
                }else if(id==R.id.reviewTab){
                    showToast("후기");
                    new ReviewTask().execute();
                }else if(id==R.id.rankingTab){
                    showToast("랭킹 확인");
                    new RankTask().execute();
//                    Intent rankIntent = new Intent(getApplicationContext(),RankingActivity.class);
//                    startActivity(rankIntent);
                }
                return false;
            }
        });
        backPressCloseHandler = new BackPressCloseHandler(this);
    }//end of onCreate

    //Toast 메시지 메서드
    private void showToast(String message){
        Toast toast=Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    @Override
    public void onBackPressed() {
        backPressCloseHandler.onBackPressed();
    }

    /* ActionBarDrawerToggle 아이콘 이벤트 처리
       - 내부적으로 ActionBarDrawerToogle 아이콘 클릭이 메뉴 이벤트로 처리되기 때문에
         onOptionsItemSelected() 메서드를 정의해야 NavigationDrawer가 열리거나 닫힘
       - onOptionsItemSelected() 메서드를 정의하지 않으면 토글 아이콘은 표시되지만
         열리거나 닫히지 않음
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // 이벤트가 toggle에서 발생한 거라면 메뉴 이벤트 로직에서 벗어나게 처리
        if(toggle.onOptionsItemSelected(item)){
            return false;
        }
        // 검색 버튼 클릭 시 검색 액티비티로 이동
        switch (item.getItemId()) {
            case R.id.menu_search_button:
                Intent searchIntent = new Intent(getApplicationContext(), SearchActivity.class);
                searchIntent.putExtra("userID",setUserID);
                searchIntent.putExtra("userName",setUserName);
                searchIntent.putExtra("total",setTotal);
                searchIntent.putExtra("aqua",setAqua);
                searchIntent.putExtra("museum",setMuseum);
                searchIntent.putExtra("art",setArt);
                searchIntent.setFlags(searchIntent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(searchIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    // 리스트뷰에서 클릭한 아이템의 정보를 담는 클래스
    public static class ListItem {
        int img;
        String thumbnailUrl;
        String name;
        String place;
        String startDay;
        String endDay;

        ListItem(String thumbnailUrl, String name, String place, String startDay, String endDay) {
            this.thumbnailUrl = thumbnailUrl;
            this.name = name;
            this.place = place;
            this.startDay = startDay;
            this.endDay = endDay;
        }
    }

    class ReviewTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://1.255.57.7/List.php";
        }

        @Override
        protected void onPostExecute(String result) {
            Intent sendReviewIntent = new Intent(MainActivity.this, ReviewActivity.class);
            sendReviewIntent.putExtra("userList",result);
            sendReviewIntent.putExtra("name",setUserName);
            sendReviewIntent.putExtra("search","");
            MainActivity.this.startActivity(sendReviewIntent);
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

    class RankTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://1.255.57.7/TotalRank.php";
        }

        @Override
        protected void onPostExecute(String rankResult) {
            Intent rankIntent = new Intent(MainActivity.this, RankingActivity.class);
            rankIntent.putExtra("ranking", rankResult);
            rankIntent.putExtra("myName",setUserName);
            MainActivity.this.startActivity(rankIntent);
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url2 = new URL(target);
                HttpURLConnection httpURLConnect2 = (HttpURLConnection) url2.openConnection();
                InputStream inputStream2 = httpURLConnect2.getInputStream();
                BufferedReader bufferedReader2 = new BufferedReader(new InputStreamReader(inputStream2));
                String temp2;
                StringBuilder stringBuilder2 = new StringBuilder();
                while((temp2 = bufferedReader2.readLine()) != null)
                {
                    stringBuilder2.append(temp2 + "\n");
                }
                bufferedReader2.close();
                inputStream2.close();
                httpURLConnect2.disconnect();
                return stringBuilder2.toString().trim();
            }   catch(Exception e)  {
                e.printStackTrace();
            }
            return null;
        }
    }



}


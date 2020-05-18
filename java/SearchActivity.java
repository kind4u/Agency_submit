package com.example.admin.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    String setUserID;
    String setUserName;
    Integer setTotal;
    Integer setAqua;
    Integer setMuseum;
    Integer setArt;

    String keyword;
    EditText keywordInput;
    ImageButton searchBtn;

    ListView searchListView;
    ListViewAdapter adapter;

    AsyncAPI keywordAsync;
    //공연/전시 정보를 담을 리스트(SingerItem 객체를 담아둘 ArrayList 생성)
    ArrayList<PerformItem> perform = new ArrayList<PerformItem>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        keywordInput = (EditText) findViewById(R.id.keywordInput);
        searchBtn = (ImageButton) findViewById(R.id.keywordSearch);
        searchListView = (ListView) findViewById(R.id.searchListView);

        // 사용자 정보 받아오기
        Intent intent = getIntent();
        setUserID =  intent.getStringExtra("userID");
        setUserName =  intent.getStringExtra("userName");
        setTotal = intent.getIntExtra("total", 0);
        setAqua = intent.getIntExtra("aqua", 0);
        setMuseum = intent.getIntExtra("museum", 0);
        setArt = intent.getIntExtra("art", 0);

        // TODO : 왜 안돼...
        //ActionBar(ToolBar)에서 기본 홈 버튼(<-)을 사용 가능하도록 설정
        ActionBar actionBar = getSupportActionBar();
        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);

        // 어댑터 객체 생성
        adapter = new ListViewAdapter(this, R.layout.perform_item, perform);

        //리스트뷰에서 아이템 클릭시 이벤트 처리
        searchListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            /* params
               - parent : 클릭한 아이템을 포함하는 부모 뷰(ListView)
               - view : 클릭한 항목의 View
               - position : 클릭한 아이템의 Adepter에서의 위치값(0, 1, 2,...)
               - id : DB를 사용했을 때 Cursor의 id 값값
            */
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // 선택 항목의 공연/전시 일련 번호 받아오기
                String seq = keywordAsync.getSeq().get(position);

                // 공연/전시 정보 받아오기
                perform = keywordAsync.getPerform();

                // 리스트뷰의 아이템 객체 정리한 객체 생성
                MainActivity.ListItem listItem = new MainActivity.ListItem(perform.get(position).thumb, perform.get(position).name, perform.get(position).place,
                        perform.get(position).startDay, perform.get(position).endDay);

                // 클릭한 전시/공연의 일련번호를 사용해 상세정보를 표현하는 정보를 가져오는 Asynctask 작업 실행
                AsyncPerforInfo asyncPerforInfo = new AsyncPerforInfo();
                // 선택한 전시/공연의 내용을 전달
                asyncPerforInfo.setListItem(listItem);
                asyncPerforInfo.execute(seq, setUserName);

            }
        });//end of setOnItemClickListener

        // 검색 버튼 클릭 이벤트
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                keyword = keywordInput.getText().toString();

                // AsyncAPI 클래스에 검색 키워드를 매개변수로 설정하여 검색 실행
                keywordAsync = new AsyncAPI(SearchActivity.this);
                keywordAsync.execute(keyword);
                keywordAsync.settingListView(searchListView, adapter);

            }
        });
    }

    // 되돌아가기 터치 시 메인액티비티로 이동
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent backIntent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(backIntent);
    }
}

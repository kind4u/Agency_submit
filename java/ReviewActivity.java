package com.example.admin.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ReviewActivity extends AppCompatActivity {
    String userName;
    String searchContents;

    private ListView reviewListView;
    private ReviewUserListAdapter reviewAdapter;
    private List<ReviewUser> userList;
    private Runnable updateUI = new Runnable() {
        public void run() {
            ReviewActivity.this.reviewAdapter.notifyDataSetChanged();
        }
    };
    private List<ReviewUser> saveList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);
        Intent receiveReviewIntent = getIntent();
        userName = receiveReviewIntent.getStringExtra("name");
        searchContents = receiveReviewIntent.getStringExtra("search");

        final EditText search = (EditText) findViewById(R.id.search);
        search.setText(searchContents);
        reviewListView = (ListView) findViewById(R.id.reviewListView);
        userList = new ArrayList<ReviewUser>();
        saveList = new ArrayList<ReviewUser>();
        reviewAdapter = new ReviewUserListAdapter(getApplicationContext(), userList, this, saveList);
        reviewListView.setAdapter(reviewAdapter);

        try {
            JSONObject jsonObject = new JSONObject(receiveReviewIntent.getStringExtra("userList"));
            final JSONArray jsonArray = jsonObject.getJSONArray("response");
            int count = 0;
            String ID, title, writer, contents;
            while (count < jsonArray.length()) {
                JSONObject object = jsonArray.getJSONObject(count);
                ID = object.getString("ID");
                title = object.getString("title");
                writer = object.getString("writer");
                contents = object.getString("contents");
                ReviewUser reviewUser = new ReviewUser(ID, title, writer, contents);
                userList.add(reviewUser);
                saveList.add(reviewUser);
                count++;
            }
            if(search != null)  {
                searchUser(searchContents);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reviewListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent sendDetailIntent = new Intent(getApplicationContext(), ReviewDetailActivity.class);
                sendDetailIntent.putExtra("id",userList.get(position).reviewID);
                sendDetailIntent.putExtra("title",userList.get(position).title);
                sendDetailIntent.putExtra("userName",userList.get(position).reviewerName);
                sendDetailIntent.putExtra("contents",userList.get(position).contents);
                sendDetailIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                ReviewActivity.this.startActivity(sendDetailIntent);
            }
        });
        ActionBar actionBar = getSupportActionBar();
        //메뉴바에 '<' 버튼이 생긴다.(두개는 항상 같이다닌다)
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setHomeButtonEnabled(true);


        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                searchUser(searchContents);
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int start, int before, int count) {
                searchUser(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }//end of onCreate

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_review, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.menu_write) {
            Intent writeIntent = new Intent(getApplicationContext(), WriteReviewActivity.class);
            writeIntent.putExtra("writer", userName);
            writeIntent.putExtra("perforname", "공연 미지정");
            writeIntent.putExtra("startDay", "");
            writeIntent.putExtra("endDay", "");
            writeIntent.putExtra("place", "");
            writeIntent.putExtra("price", "");
            writeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(writeIntent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            this.runOnUiThread(updateUI);
        }
    }


    private void addItem(ReviewUser item) {
        // ArrayList에 데이터를 추가하고, 화면에 반영하기 위해runOnUiThread()를 호출하여 실시간 갱신한다.
        this.userList.add(item);
        this.runOnUiThread(updateUI);
    }

    // 되돌아가기 버튼 클릭시 메인 액티비티로 이동
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);

    }

    public void searchUser(String search)   {
        userList.clear();
        for(int i=0; i<saveList.size(); i++)    {
            if(saveList.get(i).getReviewerName().contains(search))  {
                userList.add(saveList.get(i));
            }
        }
        reviewAdapter.notifyDataSetChanged();
    }
}

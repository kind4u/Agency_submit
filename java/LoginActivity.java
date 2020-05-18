package com.example.admin.agency;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {
    private BackPressCloseHandler loginBackPressCloseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        final EditText idText = (EditText) findViewById(R.id.inputID);
        final EditText passwordText = (EditText) findViewById(R.id.inputPW);
        final Button loginButton = (Button) findViewById(R.id.loginBtn);
        final Button joinButton = (Button) findViewById(R.id.joinBtn);

        //회원가입 화면 이동 onclickListener
        joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent joinIntent = new Intent(LoginActivity.this, JoinActivity.class);
                LoginActivity.this.startActivity(joinIntent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userID = idText.getText().toString();
                final String userPassword = passwordText.getText().toString();

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if(success) {
                                String userID = jsonResponse.getString("userID");
                                String userName = jsonResponse.getString("userName");
                                Integer total = jsonResponse.getInt("total");
                                Integer aqua = jsonResponse.getInt("aqua");
                                Integer museum = jsonResponse.getInt("museum");
                                Integer art = jsonResponse.getInt("art");

                                Intent intentToMain = new Intent(getApplicationContext(), MainActivity.class);
                                intentToMain.putExtra("userName", userName);

                                Intent intent = new Intent(LoginActivity.this, Splash2.class);
                                intent.putExtra("userID",userID);
                                intent.putExtra("userName",userName);
                                intent.putExtra("total",total);
                                intent.putExtra("aqua",aqua);
                                intent.putExtra("museum",museum);
                                intent.putExtra("art",art);
                                intent.setFlags(intent.FLAG_ACTIVITY_NO_HISTORY);   //미확인
                                startActivity(intent);
                                finish();
                            }
                            else    {
                                AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                                builder.setMessage("로그인에 실패하였습니다.")
                                        .setNegativeButton("다시 시도",null)
                                        .create()
                                        .show();
                            }
                        } catch (Exception e)   {
                            e.printStackTrace();
                        }   //end of try-catch
                    }
                };
                LoginRequest loginRequest = new LoginRequest(userID, userPassword, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LoginActivity.this);
                queue.add(loginRequest);
            }
        });
        loginBackPressCloseHandler = new BackPressCloseHandler(LoginActivity.this);
    }

    @Override
    public void onBackPressed() {
        loginBackPressCloseHandler.onBackPressed();
    }
}

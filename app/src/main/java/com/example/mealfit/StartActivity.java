package com.example.mealfit;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class StartActivity extends AppCompatActivity {
    private static final int SPLASH_TIMEOUT = 4000; //스플래시 화면 보여줄시간(ms)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                UserInfo userInfo = UserInfo.getInstance();
                                if (userInfo.getAge() != 0 || userInfo.getHeight() != 0 || userInfo.getWeight() != 0 || userInfo.getGender() != null) { // 사용자 정보가 있는 경우
                                    Intent intent = new Intent(StartActivity.this, Home.class);
                                    startActivity(intent);
                                } else { // 사용자 정보가 없는 경우
                                    Intent intent = new Intent(StartActivity.this, IntroActivity.class);
                                    startActivity(intent);
                                }
                                finish();
                            }

        },SPLASH_TIMEOUT);

    }
}
package com.example.mealfit;

import static android.app.PendingIntent.getActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class PersonalInfo extends AppCompatActivity {
    ImageButton backB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_info);
        backB=findViewById(R.id.backB);
        backB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                Fragment currentFragment = fragmentManager.findFragmentById(R.id.surveyFrame);

                if (currentFragment instanceof MBirth) {
                    // 프래그먼트가 열려있는 경우
                    fragmentManager.beginTransaction().remove(currentFragment).commit();
                } else {
                    // 프래그먼트가 없으면 액티비티 종료
                    finish();
                }
            }
        });

    }

    public void MaleOnClick(View view) {
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setGender("남성");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.surveyFrame, new MBirth(), "Male");
        ft.commitAllowingStateLoss();
    }

    public void FemaleOnClick(View view) {
        UserInfo userInfo = UserInfo.getInstance();
        userInfo.setGender("여성");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        ft.replace(R.id.surveyFrame, new MBirth(), "Female");
        ft.commitAllowingStateLoss();
    }
}
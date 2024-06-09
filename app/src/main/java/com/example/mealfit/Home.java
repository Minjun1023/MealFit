package com.example.mealfit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        //액티비티가 실행 될 때 홈 프래그먼트를 바로 띄우기
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, new homeF());
        transaction.commit();
        BottomNavigationView bottomNav = findViewById(R.id.navigationView);
        //home이 기본으로 눌려 있게 설정
        bottomNav.setSelectedItemId(R.id.home);
        bottomNav.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                    if (item.getItemId() == R.id.home) {
                        // 현재 열려있는 모든 프래그먼트를 종료
                        getSupportFragmentManager().popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
                        // 기본 프래그먼트를 추가
                        transaction.replace(R.id.frameLayout, new homeF());
                        transaction.commit();
                        return true;
                    } else if (item.getItemId() == R.id.exerciseB) {
                        transaction.replace(R.id.frameLayout, new exerciseF());
                    } else if (item.getItemId() == R.id.mealRecommend) {
                        transaction.replace(R.id.frameLayout, new mealRecommendF());
                    } else if (item.getItemId() == R.id.mypage) {
                        transaction.replace(R.id.frameLayout, new mypageF());
                    }
                    transaction.commit();
                    return true;
                }
        });
    }
}
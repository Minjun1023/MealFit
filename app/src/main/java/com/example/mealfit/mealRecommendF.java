package com.example.mealfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class mealRecommendF extends Fragment {
    private TextView mealRecommendB;
    private TextView addFoodB;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_recommend, container, false);

        mealRecommendB = view.findViewById(R.id.mealRecommendB);
        addFoodB = view.findViewById(R.id.addFoodB);

        // 기본 상태에서 mealRecommendB의 텍스트 색상과 배경색을 선택된 색상으로 설정
        mealRecommendB.setTextColor(0xFFB55D76);
        mealRecommendB.setBackgroundColor(0xFFF8F8F8);
        addFoodB.setTextColor(0xFF000000);
        addFoodB.setBackgroundColor(0xFFADB2D8);

        mealRecommendB.setOnClickListener(v -> switchToMealRecommend());
        addFoodB.setOnClickListener(v -> switchToAddFood());

        // Default fragment
        if (savedInstanceState == null) {
            switchToMealRecommend();
        }

        return view;
    }

    private void switchToMealRecommend() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, new mealrecommendbuttonF());
        // mealRecommendB 선택된 상태로 설정
        mealRecommendB.setTextColor(0xFFB55D76);
        mealRecommendB.setBackgroundColor(0xFFF8F8F8);
        addFoodB.setTextColor(0xFF000000);
        addFoodB.setBackgroundColor(0xFFADB2D8);

        fragmentTransaction.commit();
    }

    private void switchToAddFood() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout2, new addFoodF());
        // addFoodB 선택된 상태로 설정
        addFoodB.setTextColor(0xFFB55D76);
        addFoodB.setBackgroundColor(0xFFF8F8F8);
        mealRecommendB.setTextColor(0xFF000000);
        mealRecommendB.setBackgroundColor(0xFFADB2D8);

        fragmentTransaction.commit();
    }
}

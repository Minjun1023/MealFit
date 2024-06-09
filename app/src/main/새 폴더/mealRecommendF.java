package com.example.mealfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class mealRecommendF extends Fragment implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView mealRecommendB;
    private TextView addFoodB;

    public mealRecommendF() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_meal_recommend, container, false);

        // mealRecommendB, addFoodB TextView에 클릭 리스너 설정
        mealRecommendB = view.findViewById(R.id.mealRecommendB);
        addFoodB = view.findViewById(R.id.addFoodB);
        mealRecommendB.setOnClickListener(this);
        addFoodB.setOnClickListener(this);

        // mealRecommendF 프래그먼트를 기본으로 frameLayout2에 표시
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout2, new mealrecommendbuttonF());
        fragmentTransaction.commit();

        // 기본 상태에서 mealRecommendB의 텍스트 색상과 배경색을 선택된 색상으로 설정
        mealRecommendB.setTextColor(0xFFB55D76);
        mealRecommendB.setBackgroundColor(0xFFF8F8F8);
        addFoodB.setTextColor(0xFF000000);
        addFoodB.setBackgroundColor(0xFFADB2D8);

        return view;
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = fragmentManager.beginTransaction();

        if (v.getId() == R.id.mealRecommendB) {
            fragmentTransaction.replace(R.id.frameLayout2, new mealrecommendbuttonF());
            // mealRecommendB 선택된 상태로 설정
            mealRecommendB.setTextColor(0xFFB55D76);
            mealRecommendB.setBackgroundColor(0xFFF8F8F8);
            addFoodB.setTextColor(0xFF000000);
            addFoodB.setBackgroundColor(0xFFADB2D8);
        } else if (v.getId() == R.id.addFoodB) {
            fragmentTransaction.replace(R.id.frameLayout2, new addFoodF());
            addFoodB.setTextColor(0xFFB55D76);
            addFoodB.setBackgroundColor(0xFFF8F8F8);
            mealRecommendB.setTextColor(0xFF000000);
            mealRecommendB.setBackgroundColor(0xFFADB2D8);
        }
        fragmentTransaction.commit();
    }
}
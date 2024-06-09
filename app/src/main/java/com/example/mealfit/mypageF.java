package com.example.mealfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class mypageF extends Fragment implements View.OnClickListener {
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private TextView myinfoB;
    private TextView myrecordB;

    public mypageF() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mypage, container, false);

        // myinfoB , myrecordB TextView에 클릭 리스너 설정
        myinfoB = view.findViewById(R.id.myinfoB);
        myrecordB = view.findViewById(R.id.myrecordB);
        myinfoB.setOnClickListener(this);
        myrecordB.setOnClickListener(this);

        // EditmyinfoF 프래그먼트를 기본으로 frameLayout2에 표시
        fragmentManager = getChildFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frameLayout2, new EditmyinfoF());
        fragmentTransaction.commit();

        // 기본 상태에서 myinfoB의 텍스트 색상과 배경색을 선택된 색상으로 설정
        myinfoB.setTextColor(0xFFB55D76);
        myinfoB.setBackgroundColor(0xFFF8F8F8);
        myrecordB.setTextColor(0xFF000000);
        myrecordB.setBackgroundColor(0xFFADB2D8);

        return view;
    }

    @Override
    public void onClick(View v) {
        fragmentTransaction = fragmentManager.beginTransaction();

        if (v.getId() == R.id.myinfoB) {
            // EditmyinfoF 프래그먼트를 Framelayout2에 표시
            fragmentTransaction.replace(R.id.frameLayout2, new EditmyinfoF());
            // myinfoB 선택된 상태로 설정
            myinfoB.setTextColor(0xFFB55D76);
            myinfoB.setBackgroundColor(0xFFF8F8F8);
            myrecordB.setTextColor(0xFF000000);
            myrecordB.setBackgroundColor(0xFFADB2D8);
        } else if (v.getId() == R.id.myrecordB) {
            // myinfocalendarF 프래그먼트를 Framelayout2에 표시
            fragmentTransaction.replace(R.id.frameLayout2, new myinfocalendarF());
            // myrecordB 선택된 상태로 설정
            myrecordB.setTextColor(0xFFB55D76);
            myrecordB.setBackgroundColor(0xFFF8F8F8);
            myinfoB.setTextColor(0xFF000000);
            myinfoB.setBackgroundColor(0xFFADB2D8);
        }
        fragmentTransaction.commit();
    }
}
package com.example.mealfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class myinfocalendarF extends Fragment {
    private CustomCalendarView calendarView;
    private TextView textViewInfo;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_myinfocalendar, container, false);
        calendarView = view.findViewById(R.id.calendarView);
        textViewInfo = view.findViewById(R.id.textViewInfo);
        databaseHelper = new DatabaseHelper(getContext());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // 데이터베이스에서 데이터가 있는 날짜 가져오기
        List<String> dates = databaseHelper.getDatesWithData();

        // 해당 날짜에 마커 표시
        for (String date : dates) {
            try {
                Date parsedDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                calendarView.markDate(parsedDate, true);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        // 달력 날짜 선택 이벤트 리스너 설정
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                // 선택한 날짜 문자열 생성
                String date = year + "-" + (month + 1) + "-" + dayOfMonth;

                // 데이터베이스에서 해당 날짜의 데이터 가져오기
                int currentCalories = databaseHelper.getCurrentCalories(date);
                int burnedCalories = databaseHelper.getBurnedCalories(date);

                if (currentCalories == 0 && burnedCalories == 0) {
                    textViewInfo.setText("");
                } else {
                    String info = "이날 섭취한 칼로리는 " + currentCalories + "이에요\n이날 운동으로 소모한 칼로리는 "
                            + burnedCalories + "이에요";
                    textViewInfo.setText(info);
                }
            }
        });
    }
}
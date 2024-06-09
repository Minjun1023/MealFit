package com.example.mealfit;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Calendar;

public class homeF extends Fragment {
    private ProgressBar calorieProgressBar, exerciseProgressBar;
    private TextView caloriePercentText, exercisetext;
    private DatabaseHelper databaseHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        calorieProgressBar = view.findViewById(R.id.calorieprogressbar);
        exerciseProgressBar = view.findViewById(R.id.exerciseprogressbar);
        caloriePercentText = view.findViewById(R.id.cal_per_text);
        exercisetext = view.findViewById(R.id.exercise_per_text);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // DatabaseHelper 인스턴스 생성
        databaseHelper = new DatabaseHelper(getContext());

        // UserInfo 인스턴스 가져오기
        UserInfo userInfo = UserInfo.getInstance();

        // UserInfo에서 데이터 가져오기
        String gender = userInfo.getGender();
        int age = userInfo.getAge();
        double weight = userInfo.getWeight();
        double height = userInfo.getHeight();

        // 권장 섭취 칼로리 계산
        double recommendedCalories;
        if (gender.equals("남성")) {
            recommendedCalories = (((66.42 + (13.75 * weight) + (5 * height)) - (6.76 * age)) * 1.375) - 300;
        } else {
            recommendedCalories = (((655.1 + (9.56 * weight) + (1.85 * height)) - (4.68 * age)) * 1.375) - 200;
        }

        // 현재 섭취 칼로리 가져오기
        int currentCalories = databaseHelper.getCurrentCalories();

        // 퍼센트 계산
        int percent = (int) ((currentCalories * 100) / recommendedCalories);

        // 칼로리 진행도 업데이트
        calorieProgressBar.setMax(100);
        calorieProgressBar.setProgress(percent);

        // 가운데 텍스트뷰 업데이트
        caloriePercentText.setText(percent + "%");
        //longclick이벤트
        calorieProgressBar.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                String detailInfo = "현재 섭취 칼로리: " + currentCalories + " kcal\n"
                        + "권장 섭취 칼로리: " + (int) recommendedCalories + " kcal";
                //커스텀 다이얼로그 사용

                CustomDialog customDialog = new CustomDialog(getActivity());
                customDialog.show();

                // 다이얼로그 내용 설정
                TextView messageTextView = customDialog.findViewById(R.id.textView14);
                TextView messageTextView2 = customDialog.findViewById(R.id.textView15);
                TextView messageTextView3 = customDialog.findViewById(R.id.textView13);
                messageTextView.setText(detailInfo);
                messageTextView2.setText("");
                messageTextView3.setText("칼로리 정보");

                return true;
            }
        });

        // 운동으로 소모한 칼로리 가져오기
        int burnedCalories = databaseHelper.getBurnedCalories();

        // 운동 프로그래스 바 업데이트
        exerciseProgressBar.setMax(1000);
        exerciseProgressBar.setProgress(1000 - burnedCalories);
        exercisetext.setText(burnedCalories + "Kcal");

        // 매일 23시 59분 59초에 데이터 저장
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);

        long currentTime = System.currentTimeMillis();
        long targetTime = calendar.getTimeInMillis();

        if (currentTime >= targetTime) {
            // 날짜 문자열 생성
            String date = String.format("%04d-%02d-%02d", calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));

            // 데이터 저장
            databaseHelper.saveCalorieData(date, currentCalories, burnedCalories);

            // currentCalories와 burnedCalories 초기화
            databaseHelper.resetCurrentCalories();
            databaseHelper.resetBurnedCalories();
        }
    }
}
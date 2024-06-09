package com.example.mealfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class exerciseF extends Fragment {

    private Button addRun, addRideBike, addSwim, addGolf, addClimbing, addSki, addSkating, addAir, addTennis;
    private TextView runtext, biketext, swimtext, golftext, climbingtext, skitext, skatingtext, airtext, tennistext;
    private DatabaseHelper dbHelper;
    private Map<String, Integer> exerciseCountMap;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        exerciseCountMap = new HashMap<>();
        exerciseCountMap.put("달리기를 ", 0);
        exerciseCountMap.put("자전거타기를 ", 0);
        exerciseCountMap.put("수영을 ", 0);
        exerciseCountMap.put("골프를 ", 0);
        exerciseCountMap.put("클라이밍을", 0);
        exerciseCountMap.put("스키타기를 ", 0);
        exerciseCountMap.put("스케이팅을 ", 0);
        exerciseCountMap.put("에어로빅을 ", 0);
        exerciseCountMap.put("테니스를 ", 0);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exercise, container, false);
        dbHelper = new DatabaseHelper(getActivity());

        addRun = view.findViewById(R.id.addrun);
        addRideBike = view.findViewById(R.id.addridebike);
        addSwim = view.findViewById(R.id.addswim);
        addGolf = view.findViewById(R.id.addgolf);
        addClimbing = view.findViewById(R.id.addclimbing);
        addSki = view.findViewById(R.id.addski);
        addSkating = view.findViewById(R.id.addskating);
        addAir = view.findViewById(R.id.addair);
        addTennis = view.findViewById(R.id.addtennis);

        runtext = view.findViewById(R.id.runtext);
        biketext = view.findViewById(R.id.biketext);
        swimtext = view.findViewById(R.id.swimtext);
        golftext = view.findViewById(R.id.golftext);
        climbingtext = view.findViewById(R.id.climbingtext);
        skitext = view.findViewById(R.id.skitext);
        skatingtext = view.findViewById(R.id.skatingtext);
        airtext = view.findViewById(R.id.airtext);
        tennistext = view.findViewById(R.id.tennistext);

        final String currentDate = getCurrentDate();

        addRun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("달리기를 ", runtext, 300, currentDate);
            }
        });

        addRideBike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("자전거타기를 ", biketext, 300, currentDate);
            }
        });

        addSwim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("수영을 ", swimtext, 300, currentDate);
            }
        });

        addGolf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("골프를 ", golftext, 300, currentDate);
            }
        });

        addClimbing.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("클라이밍을", climbingtext, 300, currentDate);
            }
        });

        addSki.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("스키타기를 ", skitext, 300, currentDate);
            }
        });

        addSkating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("스케이팅을 ", skatingtext, 300, currentDate);
            }
        });

        addAir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("에어로빅을 ", airtext, 300, currentDate);
            }
        });

        addTennis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleExerciseButtonClick("테니스를 ", tennistext, 300, currentDate);
            }
        });

        updateTextViews();

        return view;
    }

    private void handleExerciseButtonClick(String exercise, TextView textView, int calories, String currentDate) {
        dbHelper.addBurnedCalories(currentDate, calories);
        int count = exerciseCountMap.get(exercise) + 1;
        exerciseCountMap.put(exercise, count);
        textView.setText(exercise + count + "시간만큼 했어요");
        updateTextViews();
    }

    private void updateTextViews() {
        if (dbHelper.getBurnedCalories() == 0) {
            runtext.setText("");
            biketext.setText("");
            swimtext.setText("");
            golftext.setText("");
            climbingtext.setText("");
            skitext.setText("");
            skatingtext.setText("");
            airtext.setText("");
            tennistext.setText("");
        }
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}
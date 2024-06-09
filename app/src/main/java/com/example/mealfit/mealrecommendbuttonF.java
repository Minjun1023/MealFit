package com.example.mealfit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

public class mealrecommendbuttonF extends Fragment {

    private DatabaseHelper dbHelper;
    private Button recommendButton, checkBreak, checkLunch, checkDinner;
    private List<Food> bestCombination;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mealrecommendbutton, container, false);
        recommendButton = view.findViewById(R.id.recommendbutton);
        checkBreak = view.findViewById(R.id.checkbreak);
        checkLunch = view.findViewById(R.id.checklunch);
        checkDinner = view.findViewById(R.id.checkdinner);

        dbHelper = new DatabaseHelper(getContext());
        bestCombination = new ArrayList<>();

        checkBreak.setOnClickListener(v -> {
            checkBreak.setText("식사 완료");
            checkBreak.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorFFB55D76)));
            handleSelectedFoods();
        });

        checkLunch.setOnClickListener(v -> {
            checkLunch.setText("식사 완료");
            checkLunch.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorFFB55D76)));
            handleSelectedFoods();
        });

        checkDinner.setOnClickListener(v -> {
            checkDinner.setText("식사 완료");
            checkDinner.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorFFB55D76)));
            handleSelectedFoods();
        });

        recommendButton.setOnClickListener(v -> {
            Log.d("mealrecommendbuttonF", "추천버튼 클릭");
            recommendFoods();
        });

        loadRecommendedMealsFromPreferences();
        loadButtonStatesFromPreferences();

        return view;
    }

    private void recommendFoods() {
        List<Food> selectedFoods = dbHelper.getAllSelectedFoods();

        if (selectedFoods == null || selectedFoods.isEmpty()) {
            Toast.makeText(getContext(), "선택된 음식이 없습니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        double recommendedCalories = calculateRecommendedCalories();
        bestCombination = findBestCombination(selectedFoods, recommendedCalories);

        Collections.sort(bestCombination, Comparator.comparingDouble(Food::getEnergy));

        if (bestCombination.size() >= 3) {
            saveRecommendedMealsToPreferences(bestCombination);
            displayRecommendedMeals();
        } else {
            Toast.makeText(getContext(), "충분한 음식이 선택되지 않았습니다.", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveRecommendedMealsToPreferences(List<Food> meals) {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mealfit_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(meals);
        editor.putString("recommended_meals", json);
        editor.apply();
    }

    private void displayRecommendedMeals() {
        if (bestCombination.size() >= 3) {
            checkBreak.setText(bestCombination.get(0).getFoodName());
            checkLunch.setText(bestCombination.get(1).getFoodName());
            checkDinner.setText(bestCombination.get(2).getFoodName());
        }
    }

    private void handleSelectedFoods() {
        String currentDate = getCurrentDate();
        for (Food food : bestCombination) {
            String foodName = food.getFoodName();
            double foodCalories = food.getEnergy();
            dbHelper.removeSelectedFood(foodName);
            dbHelper.addCalories(currentDate, (int) foodCalories);
        }
        saveButtonStatesToPreferences();
    }

    private void saveButtonStatesToPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mealfit_prefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("breakfastCompleted", checkBreak.getText().equals("식사 완료"));
        editor.putBoolean("lunchCompleted", checkLunch.getText().equals("식사 완료"));
        editor.putBoolean("dinnerCompleted", checkDinner.getText().equals("식사 완료"));
        editor.apply();
    }

    private void loadButtonStatesFromPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mealfit_prefs", Context.MODE_PRIVATE);
        boolean breakfastCompleted = sharedPreferences.getBoolean("breakfastCompleted", false);
        boolean lunchCompleted = sharedPreferences.getBoolean("lunchCompleted", false);
        boolean dinnerCompleted = sharedPreferences.getBoolean("dinnerCompleted", false);

        if (breakfastCompleted) {
            checkBreak.setText("식사 완료");
            checkBreak.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorFFB55D76)));
        }
        if (lunchCompleted) {
            checkLunch.setText("식사 완료");
            checkLunch.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorFFB55D76)));
        }
        if (dinnerCompleted) {
            checkDinner.setText("식사 완료");
            checkDinner.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(getContext(), R.color.colorFFB55D76)));
        }
    }

    private void loadRecommendedMealsFromPreferences() {
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("mealfit_prefs", Context.MODE_PRIVATE);
        String json = sharedPreferences.getString("recommended_meals", null);
        if (json != null) {
            Gson gson = new Gson();
            Type type = new TypeToken<List<Food>>() {}.getType();
            bestCombination = gson.fromJson(json, type);
            displayRecommendedMeals();
        }
    }

    private double calculateRecommendedCalories() {
        UserInfo userInfo = UserInfo.getInstance();
        String gender = userInfo.getGender();
        int age = userInfo.getAge();
        double weight = userInfo.getWeight();
        double height = userInfo.getHeight();

        if (gender.equals("남성")) {
            return (((66.42 + (13.75 * weight) + (5 * height)) - (6.76 * age)) * 1.375) - 300;
        } else {
            return (((655.1 + (9.56 * weight) + (1.85 * height)) - (4.68 * age)) * 1.375) - 200;
        }
    }

    private List<Food> findBestCombination(List<Food> foods, double targetCalories) {
        List<Food> bestCombination = new ArrayList<>();
        double closestCalories = Double.MAX_VALUE;

        for (int i = 0; i < foods.size() - 2; i++) {
            for (int j = i + 1; j < foods.size() - 1; j++) {
                for (int k = j + 1; k < foods.size(); k++) {
                    double totalCalories = foods.get(i).getEnergy() + foods.get(j).getEnergy() + foods.get(k).getEnergy();
                    if (Math.abs(targetCalories - totalCalories) < closestCalories) {
                        closestCalories = Math.abs(targetCalories - totalCalories);
                        bestCombination.clear();
                        bestCombination.add(foods.get(i));
                        bestCombination.add(foods.get(j));
                        bestCombination.add(foods.get(k));
                    }
                }
            }
        }

        return bestCombination;
    }

    private String getCurrentDate() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        return dateFormat.format(calendar.getTime());
    }
}

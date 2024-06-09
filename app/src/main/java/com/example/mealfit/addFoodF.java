package com.example.mealfit;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class addFoodF extends Fragment {

    private RecyclerView recyclerView;
    private TextView infotext;
    private FoodAdapter adapter;
    private List<Food> foodList;
    private List<Food> filteredFoodList;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_food, container, false);
        recyclerView = view.findViewById(R.id.recyclerView);
        SearchView searchView = view.findViewById(R.id.searchView);
        infotext = view.findViewById(R.id.infotext);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        // Load the CSV data
        foodList = loadCSVData();
        filteredFoodList = new ArrayList<>();
        adapter = new FoodAdapter(filteredFoodList, getContext());
        recyclerView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

        return view;
    }

    private void filter(String text) {
        filteredFoodList.clear();
        if (!TextUtils.isEmpty(text)) {
            for (Food food : foodList) {
                if (food.getFoodName().toLowerCase().contains(text.toLowerCase())) {
                    filteredFoodList.add(food);
                }
            }
        }
        adapter.notifyDataSetChanged();
        updateInfoText(filteredFoodList.size());
    }

    private void updateInfoText(int count) {
        if (count == 0) {
            infotext.setText("");
        } else if (count == 1) {
            infotext.setText("최소한 세가지의 음식을 선택해야 \n 식단을 추천해 드릴수 있어요 \n 현재 1개의 음식을 선택했어요");
        } else if (count == 2) {
            infotext.setText("최소한 세가지의 음식을 선택해야 \n  식단을 추천해 드릴수 있어요 \n 현재 2개의 음식을 선택했어요");
        } else {
            infotext.setText("이제 식단을 추천해 드릴수 있어요!\n음식을 더 추가하여도 좋아요");
        }
    }

    private List<Food> loadCSVData() {
        List<Food> foodList = new ArrayList<>();
        InputStream inputStream = getResources().openRawResource(R.raw.foodinfo);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        try {
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] tokens = line.split(",");
                if (tokens.length >= 5) {
                    String foodName = tokens[0];
                    double energy = parseDoubleOrZero(tokens[1]);
                    double protein = parseDoubleOrZero(tokens[2]);
                    double fat = parseDoubleOrZero(tokens[3]);
                    double carbohydrate = parseDoubleOrZero(tokens[4]);
                    Food food = new Food(foodName, energy, protein, fat, carbohydrate);
                    foodList.add(food);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return foodList;
    }

    private double parseDoubleOrZero(String value) {
        if (value == null || value.isEmpty()) {
            return 0.0;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
}

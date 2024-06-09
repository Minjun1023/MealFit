package com.example.mealfit;

import android.content.Context;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class FoodAdapter extends RecyclerView.Adapter<FoodAdapter.FoodViewHolder> {

    private List<Food> foodList;
    private Context context;
    private DatabaseHelper dbHelper;

    public FoodAdapter(List<Food> foodList, Context context) {
        this.foodList = foodList;
        this.context = context;
        dbHelper = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public FoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item, parent, false);
        return new FoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FoodViewHolder holder, int position) {
        Food food = foodList.get(position);
        holder.foodButton.setText(food.getFoodName());
        holder.energyTextView.setText("칼로리: " + food.getEnergy() + " kcal");
        holder.proteinTextView.setText("단백질: " + food.getProtein() + " g");
        holder.fatTextView.setText("지방: " + food.getFat() + " g");
        holder.carbohydrateTextView.setText("탄수화물: " + food.getCarbohydrate() + " g");

        holder.foodButton.setOnClickListener(v -> {
            if (holder.nutritionInfoLayout.getVisibility() == View.GONE) {
                holder.nutritionInfoLayout.setVisibility(View.VISIBLE);
            } else {
                holder.nutritionInfoLayout.setVisibility(View.GONE);
            }
        });

        holder.selectButton.setOnClickListener(v -> {
            String foodName = food.getFoodName();
            double foodCalories = food.getEnergy();
            dbHelper.addSelectedFood(foodName, foodCalories);
        });
    }

    @Override
    public int getItemCount() {
        return foodList.size();
    }

    public static class FoodViewHolder extends RecyclerView.ViewHolder {
        Button foodButton;
        LinearLayout nutritionInfoLayout;
        TextView energyTextView, proteinTextView, fatTextView, carbohydrateTextView;
        Button selectButton;

        public FoodViewHolder(@NonNull View itemView) {
            super(itemView);
            foodButton = itemView.findViewById(R.id.foodButton);
            nutritionInfoLayout = itemView.findViewById(R.id.nutritionInfo);
            energyTextView = itemView.findViewById(R.id.energy);
            proteinTextView = itemView.findViewById(R.id.protein);
            fatTextView = itemView.findViewById(R.id.fat);
            carbohydrateTextView = itemView.findViewById(R.id.carbohydrate);
            selectButton = itemView.findViewById(R.id.selectButton);
        }
    }
}

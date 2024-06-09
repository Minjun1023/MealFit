package com.example.mealfit;

import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MidnightReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        DatabaseHelper dbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        SharedPreferences sharedPreferences = context.getSharedPreferences("calories", Context.MODE_PRIVATE);
        int currentIntake = sharedPreferences.getInt("current_intake", 0);
        int currentBurned = sharedPreferences.getInt("current_burned", 0);

        String currentDate = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(new Date());

        ContentValues values = new ContentValues();
        values.put("date", currentDate);
        values.put("current_calories", currentIntake);
        values.put("burned_calories", currentBurned);

        db.insert("calorie_data", null, values);

        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("current_intake", 0);
        editor.putInt("current_burned", 0);
        editor.apply();

        db.close();
    }
}
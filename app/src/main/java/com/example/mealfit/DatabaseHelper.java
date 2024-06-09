// DatabaseHelper.java
package com.example.mealfit;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "mealfit.db";
    private static final int DATABASE_VERSION = 2;
    private static final String TABLE_NAME = "calorie_data";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_CURRENT_CALORIES = "current_calories";
    private static final String COLUMN_BURNED_CALORIES = "burned_calories";

    private static final String TABLE_SELECTED_FOODS = "selected_foods";
    private static final String COLUMN_FOOD_NAME = "food_name";
    private static final String COLUMN_FOOD_CALORIES = "food_calories";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableQuery = "CREATE TABLE " + TABLE_NAME + " (" +
                COLUMN_DATE + " TEXT PRIMARY KEY, " +
                COLUMN_CURRENT_CALORIES + " INTEGER, " +
                COLUMN_BURNED_CALORIES + " INTEGER)";
        db.execSQL(createTableQuery);

        String createSelectedFoodsTableQuery = "CREATE TABLE " + TABLE_SELECTED_FOODS + " (" +
                COLUMN_FOOD_NAME + " TEXT PRIMARY KEY, " +
                COLUMN_FOOD_CALORIES + " REAL)";
        db.execSQL(createSelectedFoodsTableQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SELECTED_FOODS);
        onCreate(db);
    }

    public void saveCalorieData(String date, int currentCalories, int burnedCalories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DATE, date);
        values.put(COLUMN_CURRENT_CALORIES, currentCalories);
        values.put(COLUMN_BURNED_CALORIES, burnedCalories);
        db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public int getCurrentCalories() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_CURRENT_CALORIES + ") FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int currentCalories = 0;
        if (cursor.moveToFirst()) {
            currentCalories = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return currentCalories;
    }

    public int getBurnedCalories() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT SUM(" + COLUMN_BURNED_CALORIES + ") FROM " + TABLE_NAME;
        Cursor cursor = db.rawQuery(query, null);
        int burnedCalories = 0;
        if (cursor.moveToFirst()) {
            burnedCalories = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return burnedCalories;
    }

    public void resetCurrentCalories() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_CURRENT_CALORIES, 0);
        db.update(TABLE_NAME, values, null, null);
        db.close();
    }

    public void resetBurnedCalories() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_BURNED_CALORIES, 0);
        db.update(TABLE_NAME, values, null, null);
        db.close();
    }

    public int getCurrentCalories(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_CURRENT_CALORIES + " FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date});
        int currentCalories = 0;
        if (cursor.moveToFirst()) {
            currentCalories = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return currentCalories;
    }

    public int getBurnedCalories(String date) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT " + COLUMN_BURNED_CALORIES + " FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = ?";
        Cursor cursor = db.rawQuery(query, new String[]{date});
        int burnedCalories = 0;
        if (cursor.moveToFirst()) {
            burnedCalories = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return burnedCalories;
    }

    public List<String> getDatesWithData() {
        List<String> dates = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT DISTINCT date FROM calorie_data", null);
        if (cursor.moveToFirst()) {
            do {
                String date = cursor.getString(0);
                dates.add(date);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dates;
    }

    public void removeSelectedFood(String foodName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_SELECTED_FOODS, COLUMN_FOOD_NAME + " = ?", new String[]{foodName});
        db.close();
    }
    public void addSelectedFood(String foodName, double foodCalories) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_FOOD_NAME, foodName);
        values.put(COLUMN_FOOD_CALORIES, foodCalories);
        db.insertWithOnConflict(TABLE_SELECTED_FOODS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public void addCalories(String date, int additionalCalories) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();

            cursor = db.rawQuery("SELECT " + COLUMN_CURRENT_CALORIES + " FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = ?", new String[]{date});
            if (cursor.moveToFirst()) {
                int currentCalories = cursor.getInt(0);
                currentCalories += additionalCalories;
                ContentValues values = new ContentValues();
                values.put(COLUMN_CURRENT_CALORIES, currentCalories);

                db.update(TABLE_NAME, values, COLUMN_DATE + " = ?", new String[]{date});
            } else {
                ContentValues values = new ContentValues();
                values.put(COLUMN_DATE, date);
                values.put(COLUMN_CURRENT_CALORIES, additionalCalories);
                db.insert(TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "칼로리 더하는 과정에 에러발생", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public void addBurnedCalories(String date, int additionalBurnedCalories) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = this.getWritableDatabase();

            cursor = db.rawQuery("SELECT " + COLUMN_BURNED_CALORIES + " FROM " + TABLE_NAME + " WHERE " + COLUMN_DATE + " = ?", new String[]{date});
            if (cursor.moveToFirst()) {
                int burnedCalories = cursor.getInt(0);
                burnedCalories += additionalBurnedCalories;
                ContentValues values = new ContentValues();
                values.put(COLUMN_BURNED_CALORIES, burnedCalories);

                db.update(TABLE_NAME, values, COLUMN_DATE + " = ?", new String[]{date});
            } else {
                ContentValues values = new ContentValues();
                values.put(COLUMN_DATE, date);
                values.put(COLUMN_BURNED_CALORIES, additionalBurnedCalories);
                values.put(COLUMN_CURRENT_CALORIES, 0); // 현재 칼로리에 대한 초기값을 설정
                db.insert(TABLE_NAME, null, values);
            }
        } catch (Exception e) {
            Log.e("DatabaseHelper", "칼로리 소모 추가 과정에 에러발생", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null && db.isOpen()) {
                db.close();
            }
        }
    }

    public List<Food> getAllSelectedFoods() {
        List<Food> selectedFoods = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_SELECTED_FOODS, null);
        if (cursor.moveToFirst()) {
            do {
                String foodName = cursor.getString(cursor.getColumnIndex("food_name"));
                double foodCalories = cursor.getDouble(cursor.getColumnIndex("food_calories"));
                selectedFoods.add(new Food(foodName, foodCalories, 0, 0, 0)); // 단순 예시
            } while (cursor.moveToNext());
        } else {
            Log.d("DatabaseHelper", "선택된 음식을 장바구니에서 찾을 수 없음");
        }
        cursor.close();
        return selectedFoods;
    }

}

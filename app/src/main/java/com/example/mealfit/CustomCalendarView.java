package com.example.mealfit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.CalendarView;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CustomCalendarView extends CalendarView {
    private Map<Date, Boolean> dateMap = new HashMap<>();
    private Paint circlePaint;
    private int circleRadius = 20; // 동그라미 반지름

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        circlePaint = new Paint();
        circlePaint.setColor(0xFFB55D76);
        circlePaint.setStyle(Paint.Style.FILL);
    }

    public void markDate(Date date, boolean marked) {
        dateMap.put(date, marked);
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 날짜 정보가 저장된 날짜에 동그라미 그리기
        for (Date date : dateMap.keySet()) {
            if (dateMap.get(date)) {
                int x = getDayOfWeekFromCalendar(date) * getWidth() / 7 + getWidth() / 14;
                int y = getWeekFromCalendar(date) * getHeight() / 6 + getHeight() / 12;
                canvas.drawCircle(x, y, circleRadius, circlePaint);
            }
        }
    }

    // 해당 날짜의 x 좌표 계산 메서드
    private int getDayOfWeekFromCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK) - 1; // 0부터 시작하도록 1을 뺌
        return dayOfWeek;
    }

    // 해당 날짜의 y 좌표 계산 메서드
    private int getWeekFromCalendar(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.WEEK_OF_YEAR) - getFirstWeekOfYear(calendar.get(Calendar.YEAR));
        return week;
    }

    // 해당 년도의 첫 주 계산 메서드
    private int getFirstWeekOfYear(int year) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, Calendar.JANUARY);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
}
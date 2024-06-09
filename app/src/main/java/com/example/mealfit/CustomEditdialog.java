package com.example.mealfit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.util.Objects;

public class CustomEditdialog extends Dialog {

    public CustomEditdialog(FragmentActivity activity, UserInfo userInfo) {
        super(activity);
        this.userInfo = userInfo;
    }
    UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_edit_info);

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        RadioGroup genderRadioGroup = findViewById(R.id.genderRadioGroup);
        EditText ageEditText = findViewById(R.id.ageEditText);
        EditText heightEditText = findViewById(R.id.heightEditText);
        EditText weightEditText = findViewById(R.id.weightEditText);

        // userInfo 객체가 null이 아닌 경우에만 초기화
        if (userInfo != null) {
            genderRadioGroup.check(userInfo.getGender().equals("남성") ? R.id.maleRadioButton : R.id.femaleRadioButton);
            ageEditText.setText(String.valueOf(userInfo.getAge()));
            heightEditText.setText(String.valueOf(userInfo.getHeight()));
            weightEditText.setText(String.valueOf(userInfo.getWeight()));
        }
        //저장버튼,취소버튼
        Button positiveButton = findViewById(R.id.positiveB);
        positiveButton.setOnClickListener(v -> {
            int selectedGenderId = genderRadioGroup.getCheckedRadioButtonId();
            RadioButton selectedGenderRadioButton = findViewById(selectedGenderId);
            String gender = selectedGenderRadioButton.getText().toString();

            int age = Integer.parseInt(ageEditText.getText().toString());
            int height = Integer.parseInt(heightEditText.getText().toString());
            int weight = Integer.parseInt(weightEditText.getText().toString());

            userInfo.setGender(gender);
            userInfo.setAge(age);
            userInfo.setHeight(height);
            userInfo.setWeight(weight);

            dismiss();
        });

        Button negativeButton = findViewById(R.id.negativeB);
        negativeButton.setOnClickListener(v -> dismiss());
    }
}

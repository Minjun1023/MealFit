package com.example.mealfit;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import java.util.Objects;

public class CustomDialog extends Dialog {

    private Context mContext;

    public CustomDialog(@NonNull Context context) {
        super(context);
    }

    public CustomDialog(FragmentActivity activity) {
        super(activity);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.custom_dialog);

        // 다이얼로그의 배경을 투명으로 만든다.
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        // 커스텀 다이얼로그의 각 위젯들을 정의한다.
        Button okayButton = findViewById(R.id.positiveB);

        // 버튼 리스너 설정
        okayButton.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Custom Dialog 종료
                dismiss();
            }
        });

    }


}
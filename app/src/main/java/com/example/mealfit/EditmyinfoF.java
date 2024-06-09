package com.example.mealfit;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class EditmyinfoF extends Fragment {
    TextView genderTextView, ageTextView, heightTextView, weightTextView, EditinfoTextView;
    UserInfo userInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_editmyinfo, container, false);

        userInfo = UserInfo.getInstance();

        genderTextView = view.findViewById(R.id.gendertext);
        ageTextView = view.findViewById(R.id.agetext);
        heightTextView = view.findViewById(R.id.heighttext);
        weightTextView = view.findViewById(R.id.weighttext);
        EditinfoTextView = view.findViewById(R.id.editInfoTextView);

        EditinfoTextView.setOnClickListener(v -> showEditDialog());

        setUserInfo();

        return view;
    }

    private void setUserInfo() {
        genderTextView.setText(userInfo.getGender());
        ageTextView.setText(String.valueOf(userInfo.getAge()) + "세");
        heightTextView.setText(String.valueOf(userInfo.getHeight()) + "cm");
        weightTextView.setText(String.valueOf(userInfo.getWeight()) + "kg");
    }

    private void showEditDialog() {
        UserInfo userInfo = UserInfo.getInstance();
        CustomEditdialog dialog = new CustomEditdialog(getActivity(), userInfo);
        dialog.show();
        dialog.setOnDismissListener(dialog1 -> {
            setUserInfo();
        });
    }
}
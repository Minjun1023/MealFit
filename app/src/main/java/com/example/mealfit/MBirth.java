package com.example.mealfit;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class MBirth extends Fragment {
    private EditText age, height, weight;
    private Button saveM;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_m_birth, container, false);
        age = view.findViewById(R.id.age);
        height = view.findViewById(R.id.height);
        weight = view.findViewById(R.id.weight);
        saveM = view.findViewById(R.id.saveM);
        saveM.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });
        return view;
    }

    private void saveUserInfo() {
        UserInfo userInfo = UserInfo.getInstance();
        String ageString = age.getText().toString();
        String heightString = height.getText().toString();
        String weightString = weight.getText().toString();

        if (!ageString.isEmpty() && !heightString.isEmpty() && !weightString.isEmpty()) {
            int age = Integer.parseInt(ageString);
            double height = Double.parseDouble(heightString);
            double weight = Double.parseDouble(weightString);

            userInfo.setAge(age);
            userInfo.setHeight(height);
            userInfo.setWeight(weight);

            Intent intent = new Intent(getActivity(), Home.class);
            startActivity(intent);
        } else {
            CustomDialog dlg = new CustomDialog(getActivity());
            dlg.show();
        }
    }
}
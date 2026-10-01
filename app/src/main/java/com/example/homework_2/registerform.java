package com.example.homework_2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class registerform extends Activity {

    private TextInputEditText etUsername, etPassword, etRetype, etBirthdate;
    private RadioGroup rgGender;
    private RadioButton rbMale, rbFemale;
    private CheckBox cbTennis, cbFutbal, cbOthers;
    private Button btnSignUp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        Button btnSignUp = findViewById(R.id.btnSignUp);

        initViews();

        
        btnSignUp.setOnClickListener(v -> {
            sendDataToResult();
        });
    }


    private void initViews() {
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRetype = findViewById(R.id.etRetype);
        etBirthdate = findViewById(R.id.etBirthdate);

        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        cbTennis = findViewById(R.id.cbTennis);
        cbFutbal = findViewById(R.id.cbFutbal);
        cbOthers = findViewById(R.id.cbOthers);

        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void sendDataToResult() {
        
        String username = etUsername.getText() != null ? etUsername.getText().toString().trim() : "";
        String password = etPassword.getText() != null ? etPassword.getText().toString().trim() : "";
        String birthdate = etBirthdate.getText() != null ? etBirthdate.getText().toString().trim() : "";

        
        String gender = rbFemale.isChecked() ? "Female" : "Male";

        
        ArrayList<String> selectedHobbies = new ArrayList<>();
        if (cbTennis.isChecked()) {
            selectedHobbies.add("Tennis");
        }
        if (cbFutbal.isChecked()) {
            selectedHobbies.add("Futbal");
        }
        if (cbOthers.isChecked()) {
            selectedHobbies.add("Others");
        }

        
        Bundle bundle = new Bundle();
        bundle.putString(resultform.KEY_USERNAME, username);
        bundle.putString(resultform.KEY_PASSWORD, password);
        bundle.putString(resultform.KEY_BIRTHDATE, birthdate);
        bundle.putString(resultform.KEY_GENDER, gender);
        bundle.putStringArrayList(resultform.KEY_HOBBIES, selectedHobbies);

    
        Intent intent = new Intent(registerform.this, resultform.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}

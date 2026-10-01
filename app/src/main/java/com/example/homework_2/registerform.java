package com.example.homework_2;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * Activity 1: registerform (Khung khởi chạy tạm thời để test Màn hình ResultForm của Thành viên 3)
 */
public class registerform extends AppCompatActivity {

    private Button btnOpenResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        btnOpenResult = findViewById(R.id.btnOpenResult);

        // Bấm nút để mở Activity 2: resultform (Thành viên 3) với dữ liệu mẫu
        btnOpenResult.setOnClickListener(v -> {
            Intent intent = new Intent(registerform.this, resultform.class);
            Bundle bundle = new Bundle();
            bundle.putString(resultform.KEY_USERNAME, "NguyenVanA");
            bundle.putString(resultform.KEY_PASSWORD, "1234567890");
            bundle.putString(resultform.KEY_BIRTHDATE, "20/10/1989");
            bundle.putString(resultform.KEY_GENDER, "Male");

            ArrayList<String> hobbies = new ArrayList<>();
            hobbies.add("Tennis");
            hobbies.add("Futbal");
            bundle.putStringArrayList(resultform.KEY_HOBBIES, hobbies);

            intent.putExtras(bundle);
            startActivity(intent);
        });
    }
}
package com.example.homework_2;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

/**
 * BÀI TẬP 03 - MÔN PHÁT TRIỂN PHẦN MỀM CHO THIẾT BỊ DI ĐỘNG
 * -------------------------------------------------------
 * THÀNH VIÊN 3: Giao diện UI & Logic Activity 2 (resultform)
 * - Hiển thị dữ liệu từ Form đăng ký (Username, Password, Birthdate, Gender, Hobbies)
 * - Mật khẩu được che bằng chuỗi ký tự sao (*)
 * - Xử lý thoát ứng dụng khi nhấn nút Exit
 */
public class resultform extends Activity {

    public static final String KEY_USERNAME = "KEY_USERNAME";
    public static final String KEY_PASSWORD = "KEY_PASSWORD";
    public static final String KEY_BIRTHDATE = "KEY_BIRTHDATE";
    public static final String KEY_GENDER = "KEY_GENDER";
    public static final String KEY_HOBBIES = "KEY_HOBBIES";

    private TextView tvUsername;
    private TextView tvPassword;
    private TextView tvBirthdate;
    private TextView tvGender;
    private TextView tvHobbies;
    private Button btnExit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_resultform);

        initViews();
        displayResultData();
        setupListeners();
    }

    private void initViews() {
        tvUsername = findViewById(R.id.tvUsername);
        tvPassword = findViewById(R.id.tvPassword);
        tvBirthdate = findViewById(R.id.tvBirthdate);
        tvGender = findViewById(R.id.tvGender);
        tvHobbies = findViewById(R.id.tvHobbies);
        btnExit = findViewById(R.id.btnExit);
    }

    private void displayResultData() {
        Bundle extras = getIntent() != null ? getIntent().getExtras() : null;
        if (extras != null) {
            // 1. Username
            String username = extras.getString(KEY_USERNAME, "");
            tvUsername.setText(username);

            // 2. Password (dạng chuỗi sao **********)
            String password = extras.getString(KEY_PASSWORD, "");
            tvPassword.setText(maskPassword(password));

            // 3. Birthdate
            String birthdate = extras.getString(KEY_BIRTHDATE, "");
            tvBirthdate.setText(birthdate);

            // 4. Gender (Male / Female)
            String gender = extras.getString(KEY_GENDER, "");
            tvGender.setText(gender);

            // 5. Hobbies (Danh sách phân cách dấu phẩy)
            String hobbiesStr = parseHobbies(extras);
            tvHobbies.setText(hobbiesStr);
        }
    }

    private String maskPassword(String rawPassword) {
        if (TextUtils.isEmpty(rawPassword)) {
            return "**********";
        }
        StringBuilder masked = new StringBuilder();
        for (int i = 0; i < rawPassword.length(); i++) {
            masked.append("*");
        }
        return masked.toString();
    }

    private String parseHobbies(Bundle extras) {
        if (extras == null || !extras.containsKey(KEY_HOBBIES)) {
            return getString(R.string.none_selected);
        }

        try {
            ArrayList<String> hobbiesList = extras.getStringArrayList(KEY_HOBBIES);
            if (hobbiesList != null && !hobbiesList.isEmpty()) {
                return TextUtils.join(", ", hobbiesList);
            }
        } catch (ClassCastException ignored) {
            // Hỗ trợ trường hợp truyền dạng chuỗi đơn
            String singleStr = extras.getString(KEY_HOBBIES, "");
            if (!TextUtils.isEmpty(singleStr)) {
                return singleStr;
            }
        }

        return getString(R.string.none_selected);
    }

    private void setupListeners() {
        btnExit.setOnClickListener(v -> finishAffinity());
    }
}
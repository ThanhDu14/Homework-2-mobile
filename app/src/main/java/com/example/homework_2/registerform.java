package com.example.homework_2;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;
import java.util.Calendar;

import android.text.TextUtils;
import android.widget.Toast;
import java.util.ArrayList;


public class registerform extends AppCompatActivity {
    // Khai báo biến
    // Ô văn bản
    private EditText etUsername;
    private EditText etPassword;
    private EditText etRetype;
    private EditText etBirthdate;

    // Nút chọn ngày sinh
    private Button btnSelect;

    // Nhóm chọn giới tính
    private RadioGroup rgGender;
    private RadioButton rbMale;
    private RadioButton rbFemale;

    // 3 ô checkbox sở thích
    private CheckBox cbTennis;
    private CheckBox cbFutbal;
    private CheckBox cbOthers;

    // 2 nút bấm thao tác chính
    private Button btnReset;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerform);

        initViews();
        setupListeners();
    }

    private void setupListeners() {
        // Khi nhấn nút Select -> Mở hộp thoại chọn ngày
        btnSelect.setOnClickListener(v -> showDatePickerDialog());
        btnReset.setOnClickListener(v -> resetForm());
        btnSignUp.setOnClickListener(v -> handleSignUp());
    }

    private void initViews() {
        // Ánh xạ các EditText
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etRetype = findViewById(R.id.etRetype);
        etBirthdate = findViewById(R.id.etBirthdate);

        // Ánh xạ nút chọn ngày
        btnSelect = findViewById(R.id.btnSelect);

        // Ánh xạ nhóm RadioButton giới tính
        rgGender = findViewById(R.id.rgGender);
        rbMale = findViewById(R.id.rbMale);
        rbFemale = findViewById(R.id.rbFemale);

        // Ánh xạ các CheckBox sở thích
        cbTennis = findViewById(R.id.cbTennis);
        cbFutbal = findViewById(R.id.cbFutbal);
        cbOthers = findViewById(R.id.cbOthers);

        // Ánh xạ 2 nút Reset và Sign-up
        btnReset = findViewById(R.id.btnReset);
        btnSignUp = findViewById(R.id.btnSignUp);
    }

    private void showDatePickerDialog() {
        // Ngày mặc định
        Calendar calender = Calendar.getInstance();
        int year = calender.get(Calendar.YEAR);
        int month = calender.get(Calendar.MONTH);
        int day = calender.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                registerform.this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d",
                            selectedDay, selectedMonth + 1, selectedYear);

                    // Điền ngày đã chọn vào ô etBirthdate
                    etBirthdate.setText(formattedDate);
                }, year, month, day
        );
        datePickerDialog.show();
    }

    private void resetForm() {
        etUsername.setText("");
        etPassword.setText("");
        etRetype.setText("");
        etBirthdate.setText("");

        rgGender.clearCheck();

        cbTennis.setChecked(false);
        cbFutbal.setChecked(false);
        cbOthers.setChecked(false);

        etUsername.requestFocus();
    }

    private void handleSignUp() {
        String username = etUsername.getText().toString();
        String password = etPassword.getText().toString();
        String retype = etRetype.getText().toString();
        String birthdate = etBirthdate.getText().toString();

        // Kiểm tra rỗng
        if (TextUtils.isEmpty(username) || TextUtils.isEmpty(password) || TextUtils.isEmpty(retype) || TextUtils.isEmpty(birthdate)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ các thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra chọn giới tính
        int checkedGenderId = rgGender.getCheckedRadioButtonId();
        if (checkedGenderId == -1) {
            Toast.makeText(this, "Vui lòng chọn giới tính!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Kiểm tra mật khẩu và Retype có trùng khớp không
        if (!password.equals(retype)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp!", Toast.LENGTH_SHORT).show();
            etRetype.requestFocus();
            return;
        }

        // Kiểm tra mật khẩu dưới 8 kí tự
        if (password.length() < 8) {
            Toast.makeText(this, "Mật khẩu phải có tối thiểu 8 ký tự!", Toast.LENGTH_SHORT).show();
            etPassword.requestFocus();
            return;
        }

        // Kiểm tra định dạng ngày sinh dd/mm/yyyy
        String birthError = validateBirthDate(birthdate);
        if (birthError != null) {
            Toast.makeText(this, birthError, Toast.LENGTH_SHORT).show();
            etBirthdate.requestFocus();
            return;
        }

        String gender = (checkedGenderId == R.id.rbMale) ? "Male" : "Female";
        ArrayList<String> hobbiesList = new ArrayList<>();
        if (cbTennis.isChecked()) {
            hobbiesList.add("Tennis");
        }
        if (cbFutbal.isChecked()) {
            hobbiesList.add("Futbal");
        }
        if (cbOthers.isChecked()) {
            hobbiesList.add("Others");
        }

        Intent intent = new Intent(registerform.this, resultform.class);
        Bundle bundle = new Bundle();
        bundle.putString(resultform.KEY_USERNAME, username);
        bundle.putString(resultform.KEY_PASSWORD, password);
        bundle.putString(resultform.KEY_BIRTHDATE, birthdate);
        bundle.putString(resultform.KEY_GENDER, gender);
        bundle.putStringArrayList(resultform.KEY_HOBBIES, hobbiesList);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    private boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }

    private String validateBirthDate(String birthdate){
        // Kiểm tra định dạng cơ bản dd/mm/yyyy
        String dateRegex = "^\\d{2}/\\d{2}/\\d{4}$";
        if (!birthdate.matches(dateRegex)) {
            return "Ngày sinh phải đúng định dạng dd/mm/yyyy!";
        }

        String[] parts = birthdate.split("/");
        int day = Integer.parseInt(parts[0]);
        int month = Integer.parseInt(parts[1]);
        int year = Integer.parseInt(parts[2]);

        // Kiểm tra tháng (chỉ từ 1 đến 12)
        if (month < 1 || month > 12) {
            return "Tháng sinh không hợp lệ (phải từ 1 đến 12)!";
        }

        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int currentMonth = now.get(Calendar.MONTH) + 1; // Calendar.MONTH từ 0 - 11 nên + 1
        int currentDay = now.get(Calendar.DAY_OF_MONTH);

        // Kiểm tra năm sinh hợp lý: không quá 200 tuổi và không ở tương lai
        if (year < currentYear - 200) {
            return "Năm sinh không hợp lệ (người dùng không thể quá 200 tuổi)!";
        }
        if (year > currentYear) {
            return "Năm sinh không thể ở tương lai!";
        }
        if (year == currentYear) {
            if (month > currentMonth) {
                return "Ngày sinh không thể ở tương lai!";
            }
            if (month == currentMonth && day > currentDay) {
                return "Ngày sinh không thể ở tương lai!";
            }
        }

        // Xác định số ngày tối đa của từng tháng
        int maxDays;
        switch (month) {
            case 4: case 6: case 9: case 11:
                maxDays = 30;
                break;
            case 2:
                // Nếu là năm nhuận thì 29 ngày, ngược lại 28 ngày
                maxDays = isLeapYear(year) ? 29 : 28;
                break;
            default:
                maxDays = 31;
                break;
        }

        // Kiểm tra số ngày có vượt quá số ngày của tháng đó không
        if (day < 1 || day > maxDays) {
            if (month == 2) {
                return isLeapYear(year)
                        ? "Năm " + year + " là năm nhuận, tháng 2 chỉ có tối đa 29 ngày!"
                        : "Năm " + year + " không phải năm nhuận, tháng 2 chỉ có 28 ngày!";
            }
            return "Tháng " + month + " chỉ có tối đa " + maxDays + " ngày!";
        }

        return null; // Ngày sinh hợp lệ
    }
}

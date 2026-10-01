# HƯỚNG DẪN TÍCH HỢP VÀ PHÂN CHIA CÔNG VIỆC DỰ ÁN (BÀI TẬP 03)

---

## 📌 TỔNG QUAN KIẾN TRÚC & QUY CHUẨN DESIGN
Dự án đã được dựng sẵn khung code hoàn chỉnh chuẩn **Material 3 (Modern UI)**.
- **Tông màu chủ đạo**:
  - `Primary`: `@color/primary` (`#4F46E5` - Indigo Modern)
  - `Background`: `@color/bg_main` (`#F8FAFC` - Slate Light)
  - `Card Background`: `@color/surface_card` (`#FFFFFF`)
  - `Text Colors`: `@color/text_primary` (`#0F172A`) & `@color/text_secondary` (`#475569`)
- **Tệp quy định màu sắc & Theme**: 
  - [`app/src/main/res/values/colors.xml`](file:///D:/HK1-26-27/Mobile/HW/HW2/Homework-2-mobile/app/src/main/res/values/colors.xml)
  - [`app/src/main/res/values/themes.xml`](file:///D:/HK1-26-27/Mobile/HW/HW2/Homework-2-mobile/app/src/main/res/values/themes.xml)

---

## 👤 NHIỆM VỤ CHI TIẾT DÀNH CHO TỪNG THÀNH VIÊN

### 1️⃣ THÀNH VIÊN 1: Thiết kế Giao diện XML Activity 1 (`registerform`)
* **Tệp phụ trách**: [`app/src/main/res/layout/activity_registerform.xml`](file:///D:/HK1-26-27/Mobile/HW/HW2/Homework-2-mobile/app/src/main/res/layout/activity_registerform.xml)
* **Yêu cầu & Quy chuẩn Theme**:
  - Dùng đúng hệ thống màu sắc đã được cấu hình sẵn trong `colors.xml` để đồng bộ giao diện với `resultform` (Thành viên 3).
  - Bố trí đầy đủ các widget với ID chuẩn:
    - 4 `EditText`: `@+id/etUsername`, `@+id/etPassword`, `@+id/etRetype`, `@+id/etBirthdate`
    - Cấu hình ẩn ký tự mật khẩu dạng sao/chấm đậm: `android:inputType="textPassword"` cho cả `etPassword` và `etRetype`.
    - 1 `Button` Select: `@+id/btnSelect` (đặt cạnh `etBirthdate`).
    - 1 `RadioGroup` (`@+id/rgGender`) chứa 2 `RadioButton`: `@+id/rbMale` (Male) và `@+id/rbFemale` (Female).
    - 3 `CheckBox`: `@+id/cbTennis`, `@+id/cbFutbal`, `@+id/cbOthers`.
    - 2 `Button` chính: `@+id/btnReset` (Reset) và `@+id/btnSignUp` (Sign-up).

---

### 2️⃣ THÀNH VIÊN 2: Xử lý Logic Form & Validation (`registerform.java`)
* **Tệp phụ trách**: [`app/src/main/java/com/example/homework_2/registerform.java`](file:///D:/HK1-26-27/Mobile/HW/HW2/Homework-2-mobile/app/src/main/java/com/example/homework_2/registerform.java)
* **Đầu việc cần thực hiện**:
  1. **Nút `Select`**: Sự kiện `btnSelect.setOnClickListener` gọi `DatePickerDialog` cho chọn ngày/tháng/năm và tự động định dạng `dd/MM/yyyy` điền vào `etBirthdate`.
  2. **Nút `Reset`**: Sự kiện `btnReset.setOnClickListener` xóa sạch nội dung trong các `EditText`, clear lựa chọn của `RadioGroup` và bỏ chọn toàn bộ `CheckBox`.
  3. **Ràng buộc Validation (khi bấm `Sign-up`)**:
     - Kiểm tra không để trống Username, Password, Retype, Birthdate.
     - So sánh `etRetype` với `etPassword`: Nếu không trùng khớp, hiển thị `Toast` thông báo.
     - Kiểm tra `etBirthdate` theo regex `^\\d{2}/\\d{2}/\\d{4}$` (đúng định dạng `dd/MM/yyyy`). Nếu sai, hiển thị `Toast` yêu cầu nhập lại.

---

### 3️⃣ THÀNH VIÊN 3: Giao diện & Logic Activity 2 (`resultform`) — *[ĐÃ HOÀN THÀNH]*
* **Tệp phụ trách**: 
  - Layout: [`app/src/main/res/layout/activity_resultform.xml`](file:///D:/HK1-26-27/Mobile/HW/HW2/Homework-2-mobile/app/src/main/res/layout/activity_resultform.xml)
  - Java: [`app/src/main/java/com/example/homework_2/resultform.java`](file:///D:/HK1-26-27/Mobile/HW/HW2/Homework-2-mobile/app/src/main/java/com/example/homework_2/resultform.java)
* **Kết quả**: Đã thiết kế xong màn hình hiển thị kết quả theo phong cách Modern Card UI, nhận dữ liệu qua Intent/Bundle, che mật khẩu thành dạng `*`, hiển thị sở thích nối chuỗi dấu phẩy và xử lý nút `Exit` gọi `finishAffinity()`.

---

### 4️⃣ THÀNH VIÊN 4: Tích hợp, Truyền dữ liệu (Leader)
* **Tệp phụ trách**: [`app/src/main/AndroidManifest.xml`](file:///D:/HK1-26-27/Mobile/HW/HW2/Homework-2-mobile/app/src/main/AndroidManifest.xml)
* **Quy ước Key trong Intent Bundle**:

| Dữ liệu | Kiểu dữ liệu | Tên Key quy ước (`Bundle`) |
| :--- | :--- | :--- |
| Tên đăng nhập | `String` | `KEY_USERNAME` |
| Mật khẩu | `String` | `KEY_PASSWORD` |
| Ngày sinh | `String` | `KEY_BIRTHDATE` |
| Giới tính | `String` | `KEY_GENDER` (`"Male"` / `"Female"`) |
| Sở thích | `ArrayList<String>` | `KEY_HOBBIES` |

* **Code mẫu đóng gói và gửi Intent từ Activity 1**:
```java
Intent intent = new Intent(registerform.this, resultform.class);
Bundle bundle = new Bundle();
bundle.putString(resultform.KEY_USERNAME, username);
bundle.putString(resultform.KEY_PASSWORD, password);
bundle.putString(resultform.KEY_BIRTHDATE, birthdate);
bundle.putString(resultform.KEY_GENDER, gender);
bundle.putStringArrayList(resultform.KEY_HOBBIES, selectedHobbiesList);
intent.putExtras(bundle);
startActivity(intent);
```
* **Đóng gói nộp bài**: Sau khi test xong toàn bộ luồng, dọn dẹp thư mục `.gradle`, `build` thừa và nén toàn bộ dự án thành `MSSV.zip` hoặc `MSSV.rar`.

---

## 🚀 CÁCH CHẠY THỬ VÀ TEST TRÊN MÁY ẢO
1. Mở dự án trong **Android Studio**.
2. Nhấn nút **Run 'app' (▶)** hoặc phím tắt **`Shift + F10`**.
3. Màn hình đầu tiên xuất hiện sẽ là **`registerform`**. Nhập thông tin, chọn ngày sinh, giới tính, sở thích và bấm **Sign-up** để kiểm tra chuyển sang màn hình **`resultform`**.

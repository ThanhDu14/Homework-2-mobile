package com.example.homework_2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.homework_2.databinding.FragmentFirstBinding;

import java.util.ArrayList;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.buttonFirst.setText("Xem Màn hình ResultForm (Thành viên 3)");
        binding.buttonFirst.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), resultform.class);
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

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
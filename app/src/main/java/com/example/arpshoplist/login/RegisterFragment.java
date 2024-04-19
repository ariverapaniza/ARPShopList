package com.example.arpshoplist.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.RegisterFragmentBinding;
import com.example.arpshoplist.ui.main.MainViewModel;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.text.SimpleDateFormat;


public class RegisterFragment extends Fragment {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private RegisterFragmentBinding binding;

    private MainViewModel mViewModel;

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RegisterFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.registerRegisterButton.setOnClickListener(v -> {
            String username = binding.usernameRegisterEditTextText.getText().toString();
            String fullName = binding.fullNameRegisterEditTextText.getText().toString();
            String email = binding.emailRegisterEditTextText.getText().toString();
            String password = binding.passwordRegisterEditTextTextPassword.getText().toString();

            User newUser = new User(username, fullName, email, encryptPassword(password));
            mViewModel.insert(newUser);

            NavController navController = Navigation.findNavController(view);
            NavDirections navDirections = RegisterFragmentDirections.actionRegisterFragmentToMainFragment();
            navController.navigate(navDirections);
        });

        binding.goToLoginInRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = RegisterFragmentDirections.actionRegisterFragmentToMainFragment();
                navController.navigate(navDirections);
            }
        });
    }

    private String encryptPassword(String passwordToHash) {
        String generatedPassword = null;
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(passwordToHash.getBytes());
            byte[] bytes = md.digest();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < bytes.length; i++) {
                sb.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));
            }
            generatedPassword = sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return generatedPassword;
    }
}
package com.example.arpshoplist.ui.main;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.util.Base64;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.MainFragmentBinding;
import com.example.arpshoplist.login.SessionManager;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;

public class MainFragment extends Fragment {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private MainViewModel mViewModel;
    private MainFragmentBinding binding;

    public static MainFragment newInstance() {
        return new MainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = MainFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        binding.loginLoginButton.setOnClickListener(v -> {
            String username = binding.usernameLoginEditTextText.getText().toString();
            String password = binding.passwordLoginEditTextTextPassword.getText().toString();

            mViewModel.findByUsername(username).observe(getViewLifecycleOwner(), user -> {
                if (user != null && user.getPassword().equals(encryptPassword(password))) {
                    int userId = user.getUserid(); // Assuming getUserid() returns the user's ID
                    SessionManager sessionManager = new SessionManager(getContext());
                    sessionManager.createLoginSession(username, userId);

                    NavController navController = Navigation.findNavController(v);
                    NavDirections navDirections = MainFragmentDirections.actionMainFragmentToHomePageFragment();
                    navController.navigate(navDirections);
                } else {
                    String message;
                    if (user == null) {
                        message = "Username not found";
                    } else {
                        message = "Incorrect password";
                    }
                    Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
                }
            });
        });

        binding.goToRegisterFromLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = Navigation.findNavController(view);
                NavDirections navDirections = MainFragmentDirections.actionMainFragmentToRegisterFragment();
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
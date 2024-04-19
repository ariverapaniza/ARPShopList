package com.example.arpshoplist.settings;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.SettingsFragmentBinding;
import com.example.arpshoplist.login.SessionManager;
import com.example.arpshoplist.login.User;
import com.example.arpshoplist.login.UserRepository;
import com.example.arpshoplist.ui.main.MainViewModel;


public class SettingsFragment extends Fragment {

    private SettingsFragmentBinding binding;
    private User user;

    private MainViewModel mViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = SettingsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        getUserInformation();

        binding.logOutSettingsButton.setOnClickListener(v -> {

            SessionManager sessionManager = new SessionManager(getContext());
            sessionManager.logoutUser();

            Navigation.findNavController(view).navigate(R.id.action_homePageFragment_to_mainFragment);
        });

    }

    public void getUserInformation() {
        SessionManager sessionManager = new SessionManager(getContext());
        Integer currentUserId = sessionManager.getUserId();
        UserRepository userRepository = new UserRepository(getActivity().getApplication());
        User user = userRepository.findById(currentUserId);

        if (user != null) {
            binding.usernameTextView.setText(user.getUsername());
            binding.nameTextView.setText(user.getName());
            binding.emailTextView.setText(user.getEmail());
        } else {
            Log.e("SettingsFragment", "User not found");
        }
    }
}
package com.example.arpshoplist.shoplist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.GroceryItemDetailsFragmentBinding;
import com.example.arpshoplist.login.SessionManager;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.ui.main.MainViewModel;


public class GroceryItemDetailsFragment extends Fragment {

    private GroceryItemDetailsFragmentBinding binding;
    private MainViewModel mViewModel;
    private ShoppingList shoppingList;

    public static GroceryItemDetailsFragment newInstance() {
        return new GroceryItemDetailsFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = GroceryItemDetailsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        SessionManager sessionManager = new SessionManager(getContext());
    }
}
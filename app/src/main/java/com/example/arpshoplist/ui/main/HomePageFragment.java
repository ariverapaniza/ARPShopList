package com.example.arpshoplist.ui.main;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.HomepageFragmentBinding;
import com.example.arpshoplist.recipe.NewRecipeFragment;
import com.example.arpshoplist.recipe.RecipeFragment;
import com.example.arpshoplist.settings.SettingsFragment;
import com.example.arpshoplist.shoplist.NewShopListFragment;
import com.example.arpshoplist.shoplist.ShoppingFragment;


public class HomePageFragment extends Fragment {

    private HomepageFragmentBinding binding;
    private MainViewModel mViewModel;
    private Animation rotateOpen;
    private Animation rotateClose;
    private Animation fromBottom;
    private Animation toBottom;
    private boolean isFabOpen = false;

    public static HomePageFragment newInstance() {
        return new HomePageFragment();
    }
    private void setHomePageTextViewVisibility(boolean visible) {
        binding.homePageTextView.setVisibility(visible ? View.VISIBLE : View.GONE);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = HomepageFragmentBinding.inflate(inflater, container, false);
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            Fragment fragment = null;
            int itemId = item.getItemId();
            if (itemId == R.id.shoplist) {
                fragment = new ShoppingFragment();
            } else if (itemId == R.id.recipe) {
                fragment = new RecipeFragment();
            } else if (itemId == R.id.settings) {
                fragment = new SettingsFragment();
            }

            if (fragment != null) {
                replaceFragment(fragment);
            }
            return true;
        });

        rotateOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_open_animation);
        rotateClose = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate_close_animation);
        fromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.from_button_animation);
        toBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.to_button_animation);

        setupFloatingActionButtonListeners();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        setHomePageTextViewVisibility(true);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragment);
        fragmentTransaction.commit();

        setHomePageTextViewVisibility(false);
    }

    private void setupFloatingActionButtonListeners() {
        binding.plusFloatingActionButton.setOnClickListener(v -> toggleFab());

        binding.shopListFloatingActionButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Create a new Shopping List", Toast.LENGTH_SHORT).show();
            replaceFragment(new NewShopListFragment());
            closeFab();
        });

        binding.recipeFloatingActionButton.setOnClickListener(v -> {
            Toast.makeText(getActivity(), "Create a new Recipe", Toast.LENGTH_SHORT).show();
            replaceFragment(new NewRecipeFragment());
            closeFab();
        });
    }

    private void toggleFab() {
        if (isFabOpen) {
            closeFab();
        } else {
            openFab();
        }
    }

    private void openFab() {
        isFabOpen = true;
        binding.shopListFloatingActionButton.setVisibility(View.VISIBLE);
        binding.recipeFloatingActionButton.setVisibility(View.VISIBLE);
        binding.shopListFloatingActionButton.startAnimation(fromBottom);
        binding.recipeFloatingActionButton.startAnimation(fromBottom);
        binding.plusFloatingActionButton.startAnimation(rotateOpen);
    }

    private void closeFab() {
        isFabOpen = false;
        binding.shopListFloatingActionButton.startAnimation(toBottom);
        binding.recipeFloatingActionButton.startAnimation(toBottom);
        binding.plusFloatingActionButton.startAnimation(rotateClose);

        binding.shopListFloatingActionButton.postDelayed(
                () -> binding.shopListFloatingActionButton.setVisibility(View.INVISIBLE), 300);
        binding.recipeFloatingActionButton.postDelayed(
                () -> binding.recipeFloatingActionButton.setVisibility(View.INVISIBLE), 300);
    }
}
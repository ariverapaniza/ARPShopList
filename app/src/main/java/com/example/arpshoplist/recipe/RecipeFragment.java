package com.example.arpshoplist.recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.RecipeFragmentBinding;
import com.example.arpshoplist.ingredients.Ingredients;
import com.example.arpshoplist.login.SessionManager;
import com.example.arpshoplist.ui.main.MainViewModel;

public class RecipeFragment extends Fragment {

    private RecipeFragmentBinding binding;
    private MainViewModel mViewModel;
    private Ingredients ingredients;
    private RecipeIngredient recipeIngredient;

    public static RecipeFragment newInstance() {
        return new RecipeFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = RecipeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager sessionManager = new SessionManager(getContext());
        Integer currentUserId = sessionManager.getUserId();
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupRecyclerView(currentUserId);
    }

    private void setupRecyclerView(Integer currentUserId) {
        final RecipeRecyclerViewAdapter adapter = new RecipeRecyclerViewAdapter();
        adapter.setOnItemClickListener(recipe -> openRecipeDetailsFragment(recipe));
        binding.savedRecipeRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.savedRecipeRecyclerView.setAdapter(adapter);

        mViewModel.getRecipesByUserId(currentUserId).observe(getViewLifecycleOwner(), adapter::submitList);
    }

    private void openRecipeDetailsFragment(Recipe recipe) {

        RecipeDetailsFragment detailsFragment = RecipeDetailsFragment.newInstance(recipe, ingredients, recipeIngredient);
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_layout, detailsFragment)
                .addToBackStack(null)
                .commit();
    }
}

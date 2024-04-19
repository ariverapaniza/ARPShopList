package com.example.arpshoplist.recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.RecipeDetailsFragmentBinding;
import com.example.arpshoplist.ui.main.MainViewModel;
import com.example.arpshoplist.ingredients.Ingredients;

import java.util.List;

public class RecipeDetailsFragment extends Fragment {

    private RecipeDetailsFragmentBinding binding;
    private static final String ARG_RECIPE = "recipe";
    private Recipe recipe;
    private Ingredients ingredients;
    private RecipeIngredient recipeIngredient;
    private List<Ingredients> ingredientsList;
    private List<RecipeIngredient> recipeIngredientList;
    private MainViewModel mViewModel;

    public static RecipeDetailsFragment newInstance(Recipe recipe, Ingredients ingredients, RecipeIngredient recipeIngredient) {
        RecipeDetailsFragment fragment = new RecipeDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        args.putSerializable("ARG_INGREDIENTS", ingredients);
        args.putSerializable("ARG_RECIPE_INGREDIENT", recipeIngredient);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            recipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
            ingredients = (Ingredients) getArguments().getSerializable("ARG_INGREDIENTS");
            recipeIngredient = (RecipeIngredient) getArguments().getSerializable("ARG_RECIPE_INGREDIENT");
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = RecipeDetailsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (recipe != null) {
            displayDetailsRecipe(recipe);
            mViewModel.getQuantityForRecipe(recipe.getRecipeid()).observe(getViewLifecycleOwner(), this::displayRecipeIngredients);
            mViewModel.getIngredientsForRecipe(recipe.getRecipeid()).observe(getViewLifecycleOwner(), this::displayIngredients);
        }

        if (recipe != null) {
            displayDetailsRecipe(recipe);

            mViewModel.getIngredientsForRecipe(recipe.getRecipeid()).observe(getViewLifecycleOwner(), ingredients -> {
                this.ingredientsList = ingredients;
            });

            mViewModel.getQuantityForRecipe(recipe.getRecipeid()).observe(getViewLifecycleOwner(), recipeIngredients -> {
                this.recipeIngredientList = recipeIngredients;
            });
        }

        binding.deleteRecipeButton.setOnClickListener(v -> {
            if (recipe != null) {
                deleteRecipe(recipe);
            } else {
                Toast.makeText(getActivity(), "Error: Recipe not found", Toast.LENGTH_SHORT).show();
            }
        });

        binding.updateRecipeButton.setOnClickListener(v -> {
            if (recipe != null && ingredientsList != null && recipeIngredientList != null) {
                UpdateRecipeFragment updateRecipeFragment = UpdateRecipeFragment.newInstance(recipe, ingredientsList, recipeIngredientList);
                FragmentManager fragmentManager = getParentFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.frame_layout, updateRecipeFragment)
                        .addToBackStack(null)
                        .commit();
            } else {
                Toast.makeText(getContext(), "Data is not available for update", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void deleteRecipe(Recipe recipe){
        mViewModel.delete(recipe);
        Toast.makeText(getActivity(), "Recipe and associated data deleted", Toast.LENGTH_SHORT).show();

        if (getActivity() != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.frame_layout, RecipeFragment.newInstance())
                    .commit();
        }
    }

    private void displayRecipeIngredients(List<RecipeIngredient> recipeIngredients) {
        TextView[] quantityViews = {binding.qty1RecipeDescriptionTextView, binding.qty2RecipeDescriptionTextView, binding.qty3RecipeDescriptionTextView, binding.qty4RecipeDescriptionTextView};
        for (int i = 0; i < recipeIngredients.size(); i++) {
            RecipeIngredient recipeIngredient = recipeIngredients.get(i);
            if(i < quantityViews.length){
                quantityViews[i].setText(recipeIngredient.getQuantity());
            }
        }
    }

    private void displayIngredients(List<Ingredients> ingredient){
        TextView[] ingredientViews = {binding.ingredient1RecipeDescriptionTextView, binding.ingredient2RecipeDescriptionTextView, binding.ingredient3RecipeDescriptionTextView, binding.ingredient4RecipeDescriptionTextView};
        for (int i = 0; i < ingredient.size(); i++) {
            Ingredients ingredients = ingredient.get(i);
            if (i < ingredientViews.length){
                ingredientViews[i].setText(ingredients.getIngredientname());
            }
        }
    }
    public void displayDetailsRecipe(Recipe recipe) {
        binding.titleRecipeDetailsTextView.setText(recipe.getTitle());
        binding.descriptionRecipeDescriptionTextView.setText(recipe.getDescription());
    }
}
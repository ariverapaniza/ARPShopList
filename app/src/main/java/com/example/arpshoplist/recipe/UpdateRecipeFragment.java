package com.example.arpshoplist.recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.UpdateRecipeFragmentBinding;
import com.example.arpshoplist.ingredients.Ingredients;
import com.example.arpshoplist.login.SessionManager;
import com.example.arpshoplist.ui.main.MainViewModel;

import java.io.Serializable;
import java.util.List;


public class UpdateRecipeFragment extends Fragment {

    private UpdateRecipeFragmentBinding binding;
    private MainViewModel mViewModel;
    private Recipe currentRecipe;
    private List<Ingredients> currentIngredientsList;
    private List<RecipeIngredient> currentRecipeIngredientList;

    private static final String ARG_RECIPE = "recipe";
    private static final String ARG_INGREDIENTS = "ingredients";
    private static final String ARG_RECIPE_INGREDIENTS = "recipeIngredients";

    public static UpdateRecipeFragment newInstance(Recipe recipe, List<Ingredients> ingredientsList, List<RecipeIngredient> recipeIngredientList) {
        UpdateRecipeFragment fragment = new UpdateRecipeFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_RECIPE, recipe);
        args.putSerializable(ARG_INGREDIENTS, (Serializable) ingredientsList);
        args.putSerializable(ARG_RECIPE_INGREDIENTS, (Serializable) recipeIngredientList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            currentRecipe = (Recipe) getArguments().getSerializable(ARG_RECIPE);
            currentIngredientsList = (List<Ingredients>) getArguments().getSerializable(ARG_INGREDIENTS);
            currentRecipeIngredientList = (List<RecipeIngredient>) getArguments().getSerializable(ARG_RECIPE_INGREDIENTS);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = UpdateRecipeFragmentBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        if (currentRecipe != null) {
            populateFields();
        }

        binding.submitUpdateRecipeButton.setOnClickListener(v -> updateRecipeDetails());

    }

    private void populateFields() {
        binding.titleUpdateRecipeEditText.setText(currentRecipe.getTitle());
        binding.descriptionUpdateRecipeEditTextMultiLine.setText(currentRecipe.getDescription());

        for (int i = 0; i < currentRecipeIngredientList.size(); i++) {
            RecipeIngredient ingredient = currentRecipeIngredientList.get(i);
            Ingredients details = currentIngredientsList.get(i);
            switch (i) {
                case 0:
                    binding.quantityUpdateRecipeEditText.setText(ingredient.getQuantity());
                    binding.ingredientUpdateNameRecipeEditText.setText(details.getIngredientname());
                    break;
                case 1:
                    binding.quantity2UpdateRecipeEditText.setText(ingredient.getQuantity());
                    binding.ingredient2UpdateNameRecipeEditText.setText(details.getIngredientname());
                    break;
                case 2:
                    binding.quantity3UpdateRecipeEditText.setText(ingredient.getQuantity());
                    binding.ingredient3UpdateNameRecipeEditText.setText(details.getIngredientname());
                    break;
                case 3:
                    binding.quantity4UpdateRecipeEditText.setText(ingredient.getQuantity());
                    binding.ingredient4UpdateNameRecipeEditText.setText(details.getIngredientname());
                    break;
            }
        }
    }

    private void updateRecipeDetails () {
        String updatedTitle = binding.titleUpdateRecipeEditText.getText().toString();
        String updatedDescription = binding.descriptionUpdateRecipeEditTextMultiLine.getText().toString();

        currentRecipe.setTitle(updatedTitle);
        currentRecipe.setDescription(updatedDescription);

        updateRecipeIngredientsAndQuantities();

        mViewModel.updateRecipe(currentRecipe, currentIngredientsList, currentRecipeIngredientList);

        Toast.makeText(getActivity(), "Recipe Updated Successfully", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            RecipeFragment recipeFragment = new RecipeFragment();
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, recipeFragment)
                    .commit();
        }, 2000);
    }


    private void updateRecipeIngredientsAndQuantities () {
        EditText[] quantityEditTexts = {
                binding.quantityUpdateRecipeEditText,
                binding.quantity2UpdateRecipeEditText,
                binding.quantity3UpdateRecipeEditText,
                binding.quantity4UpdateRecipeEditText
        };

        EditText[] ingredientEditTexts = {
                binding.ingredientUpdateNameRecipeEditText,
                binding.ingredient2UpdateNameRecipeEditText,
                binding.ingredient3UpdateNameRecipeEditText,
                binding.ingredient4UpdateNameRecipeEditText
        };

        for (int i = 0; i < currentRecipeIngredientList.size(); i++) {
            if (i < quantityEditTexts.length && i < ingredientEditTexts.length) {
                String quantity = quantityEditTexts[i].getText().toString();
                String ingredientName = ingredientEditTexts[i].getText().toString();

                RecipeIngredient recipeIngredient = currentRecipeIngredientList.get(i);
                recipeIngredient.setQuantity(quantity);

                Ingredients ingredient = currentIngredientsList.get(i);
                ingredient.setIngredientname(ingredientName);
            }
        }
    }
}

package com.example.arpshoplist.recipe;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arpshoplist.R;
import com.example.arpshoplist.UserRoomDatabase;
import com.example.arpshoplist.databinding.NewRecipeFragmentBinding;
import com.example.arpshoplist.ingredients.Ingredients;
import com.example.arpshoplist.login.SessionManager;
import com.example.arpshoplist.login.User;
import com.example.arpshoplist.ui.main.MainViewModel;


public class NewRecipeFragment extends Fragment {


    private NewRecipeFragmentBinding binding;

    private MainViewModel mViewModel;

    public static NewRecipeFragment newInstance(){ return new NewRecipeFragment();}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = NewRecipeFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        SessionManager sessionManager = new SessionManager(getContext());

        binding.submitRecipeButton.setOnClickListener(v -> {
            int currentUserId = sessionManager.getUserId();
            if (currentUserId != -1) {
                User currentUser = mViewModel.findById(currentUserId);
                if (currentUser != null) {
                    String title = binding.titleRecipeEditText.getText().toString();
                    String description = binding.descriptionRecipeEditTextMultiLine.getText().toString();

                    Recipe newRecipe = new Recipe(currentUserId, title, description);
                    long recipeId = mViewModel.insert(newRecipe);

                    boolean allIngredientsAdded = true;

                    if (recipeId != -1) {
                        String[] ingredientNames = {
                                binding.ingredientNameRecipeEditText.getText().toString(),
                                binding.ingredient2NameRecipeEditText.getText().toString(),
                                binding.ingredient3NameRecipeEditText.getText().toString(),
                                binding.ingredient4NameRecipeEditText.getText().toString()
                        };

                        String[] quantities = {
                                binding.quantityRecipeEditText.getText().toString(),
                                binding.quantity2RecipeEditText.getText().toString(),
                                binding.quantity3RecipeEditText.getText().toString(),
                                binding.quantity4RecipeEditText.getText().toString()
                        };

                        for (int i = 0; i < ingredientNames.length; i++) {
                            if (!ingredientNames[i].isEmpty() && !quantities[i].isEmpty()) {
                                Ingredients newIngredient = new Ingredients(ingredientNames[i]);
                                long ingredientId = mViewModel.insert(newIngredient);

                                if (ingredientId != -1) {
                                    RecipeIngredient newRecipeIngredient = new RecipeIngredient((int) recipeId, (int) ingredientId, quantities[i]);
                                    mViewModel.insert(newRecipeIngredient);
                                } else {
                                    allIngredientsAdded = false;
                                    break;
                                }
                            }
                        }

                        if (allIngredientsAdded) {
                            Toast.makeText(getActivity(), "Recipe Created Successfully", Toast.LENGTH_SHORT).show();
                            clearFormFields();
                        } else {
                            Toast.makeText(getActivity(), "Error adding ingredients", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(), "Error creating recipe", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getActivity(), "User Not Found", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "User ID Not Found", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void clearFormFields() {
        binding.titleRecipeEditText.setText("");
        binding.descriptionRecipeEditTextMultiLine.setText("");
        binding.ingredientNameRecipeEditText.setText("");
        binding.ingredient2NameRecipeEditText.setText("");
        binding.ingredient3NameRecipeEditText.setText("");
        binding.ingredient4NameRecipeEditText.setText("");
        binding.quantityRecipeEditText.setText("");
        binding.quantity2RecipeEditText.setText("");
        binding.quantity3RecipeEditText.setText("");
        binding.quantity4RecipeEditText.setText("");
    }

}
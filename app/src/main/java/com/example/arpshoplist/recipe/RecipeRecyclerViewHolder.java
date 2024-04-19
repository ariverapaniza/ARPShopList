package com.example.arpshoplist.recipe;

import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arpshoplist.databinding.RecipeDetailsFragmentBinding;
import com.example.arpshoplist.databinding.SavedrecipeRecyclerItemViewBinding;

public class RecipeRecyclerViewHolder extends RecyclerView.ViewHolder {

    private RecipeRecyclerViewAdapter.OnItemClickListener listener;
    private SavedrecipeRecyclerItemViewBinding binding;
    private final Button openRecipeCardButton;
    private Recipe currentRecipe;
    private OnItemClickListener onCLickListener;

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public RecipeRecyclerViewHolder(@NonNull View itemView, RecipeRecyclerViewAdapter.OnItemClickListener listener) {
        super(itemView);
        binding = SavedrecipeRecyclerItemViewBinding.bind(itemView);
        this.listener = listener;
        openRecipeCardButton = binding.openRecipeCardButton;

        openRecipeCardButton.setOnClickListener(v -> {
            if (this.listener != null && currentRecipe != null) {
                this.listener.onItemClick(currentRecipe);
            }
        });
    }

    public void bind(Recipe recipe) {
        this.currentRecipe = recipe;
        binding.cardTitleRecipeTextView.setText(recipe.getTitle());
        itemView.setOnClickListener(v -> {
            if (this.listener != null) {
                this.listener.onItemClick(currentRecipe);
            }
        });
    }
}

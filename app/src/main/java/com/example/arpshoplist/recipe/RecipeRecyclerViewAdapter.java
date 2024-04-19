package com.example.arpshoplist.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.RecipeDetailsFragmentBinding;
import com.example.arpshoplist.databinding.SavedrecipeRecyclerItemViewBinding;
import com.example.arpshoplist.ingredients.Ingredients;

public class RecipeRecyclerViewAdapter extends ListAdapter<Recipe, RecipeRecyclerViewHolder> {

    private RecipeRecyclerViewHolder.OnItemClickListener listener;
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(Recipe recipe);
    }

    public RecipeRecyclerViewAdapter(){
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Recipe> DIFF_CALLBACK = new DiffUtil.ItemCallback<Recipe>() {
        @Override
        public boolean areItemsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getRecipeid().equals(newItem.getRecipeid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Recipe oldItem, @NonNull Recipe newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) && oldItem.getDescription().equals(newItem.getDescription());
        }
    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public RecipeRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.savedrecipe_recycler_item_view, parent, false);
        return new RecipeRecyclerViewHolder(itemView, onItemClickListener);
    }



    @Override
    public void onBindViewHolder(@NonNull RecipeRecyclerViewHolder holder, int position) {
        Recipe currentRecipe = getItem(position);
        holder.bind(currentRecipe);
    }
}

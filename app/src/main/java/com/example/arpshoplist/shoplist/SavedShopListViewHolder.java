package com.example.arpshoplist.shoplist;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.SavedshoplistRecylcerItemViewBinding;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;

public class SavedShopListViewHolder extends RecyclerView.ViewHolder {
    private SavedshoplistRecylcerItemViewBinding binding;
    private ShoppingList currentShoppingList;
    private final Button savedShoplistOpenButton;
    public interface OnItemClickListener {
        void onShopListClickListener(ShoppingList shoppingList);
    }
    private SavedShopListViewAdapter.OnItemClickListener onShopListClickListener;
    public SavedShopListViewHolder(@NonNull View itemView, SavedShopListViewAdapter.OnItemClickListener onShopListClickListener) {
        super(itemView);
        binding = SavedshoplistRecylcerItemViewBinding.bind(itemView);
        this.onShopListClickListener = onShopListClickListener;
        savedShoplistOpenButton = binding.savedShoplistOpenButton;
        savedShoplistOpenButton.setOnClickListener(v -> {
            if (this.onShopListClickListener != null && currentShoppingList != null) {
                this.onShopListClickListener.onShopListClickListener(currentShoppingList);
            }
        });
    }

    public void bind(ShoppingList shoppingList) {
        this.currentShoppingList = shoppingList;
        binding.savedShoplistTitleTextView.setText(shoppingList.getTitle());
        itemView.setOnClickListener(v -> {
            if (this.onShopListClickListener != null) {
                this.onShopListClickListener.onShopListClickListener(currentShoppingList);
            }
        });
    }
}

package com.example.arpshoplist.shoplist;

import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItem;
import com.example.arpshoplist.databinding.ShoplistRecyclerItemViewBinding;

public class ShopListRecyclerViewHolder extends RecyclerView.ViewHolder {

    private ShopListRecyclerViewAdapter.OnItemClickListener groceryItemListener;
    private ShoplistRecyclerItemViewBinding binding;
    private ShoppingListItem shoppingListItem;

    private OnItemClickListener onShopListClickListener;


    public interface OnItemClickListener {
        void onGroceryItemClick(ShoppingListItem shoppingListItem);
    }
    public ShopListRecyclerViewHolder(@NonNull View itemView, ShopListRecyclerViewAdapter.OnItemClickListener groceryItemListener) {
        super(itemView);
        binding = ShoplistRecyclerItemViewBinding.bind(itemView);
        this.groceryItemListener = groceryItemListener;
        binding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                groceryItemListener.onGroceryItemClick(shoppingListItem);
            }
        });
    }

    public void bind(ShoppingListItem shoppingListItem) {
        this.shoppingListItem = shoppingListItem;
        binding.groceryItemTextView.setText(shoppingListItem.getItemname());
        int drawableResourceId = itemView.getResources().getIdentifier(shoppingListItem.getIconimage(), "drawable", itemView.getContext().getPackageName());
        binding.groceryItemImageView.setImageResource(drawableResourceId);
        itemView.setOnClickListener(v -> {
            if (this.groceryItemListener != null) {
                this.groceryItemListener.onGroceryItemClick(shoppingListItem);
            }
        });
    }


}

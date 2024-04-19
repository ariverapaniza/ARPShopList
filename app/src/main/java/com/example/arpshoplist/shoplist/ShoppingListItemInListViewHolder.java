package com.example.arpshoplist.shoplist;

import android.view.View;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.RecyclerView;
import com.example.arpshoplist.databinding.ShoplistRecyclerItemViewBinding;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItem;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;
import com.example.arpshoplist.ui.main.MainViewModel;

public class ShoppingListItemInListViewHolder extends RecyclerView.ViewHolder {
    private ShoplistRecyclerItemViewBinding binding;
    private MainViewModel mViewModel;
    private LifecycleOwner lifecycleOwner;
    private ShoppingListItemInList currentShoppingListItemInList;
    private ShoppingListItem shoppingListItem;

    public ShoppingListItemInListViewHolder(ShoplistRecyclerItemViewBinding binding, MainViewModel mViewModel, LifecycleOwner lifecycleOwner) {
        super(binding.getRoot());
        this.binding = binding;
        this.mViewModel = mViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    public void bind(ShoppingListItemInList shoppingListItemInList) {
        this.currentShoppingListItemInList = shoppingListItemInList;
        mViewModel.getThisLiveShoppingListItemByItemId(shoppingListItemInList.getShoplistitemtableid()).observe(lifecycleOwner, item -> {
            if (item != null) {
                binding.groceryItemTextView.setText(item.getItemname());
                int resourceId = itemView.getResources().getIdentifier(item.getIconimage(), "drawable", itemView.getContext().getPackageName());
                binding.groceryItemImageView.setImageResource(resourceId);
            }
        });
    }
}
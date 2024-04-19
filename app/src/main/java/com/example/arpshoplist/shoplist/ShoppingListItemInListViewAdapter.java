package com.example.arpshoplist.shoplist;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.ShoplistRecyclerItemViewBinding;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;
import com.example.arpshoplist.ui.main.MainViewModel;

public class ShoppingListItemInListViewAdapter extends ListAdapter<ShoppingListItemInList, ShoppingListItemInListViewHolder> {

    private MainViewModel mViewModel;
    private LifecycleOwner lifecycleOwner;

    public ShoppingListItemInListViewAdapter(MainViewModel mViewModel, LifecycleOwner lifecycleOwner) {
        super(new DiffUtil.ItemCallback<ShoppingListItemInList>() {
            @Override
            public boolean areItemsTheSame(@NonNull ShoppingListItemInList oldItem, @NonNull ShoppingListItemInList newItem) {
                return oldItem.getIteminlistid().equals(newItem.getIteminlistid());
            }

            @Override
            public boolean areContentsTheSame(@NonNull ShoppingListItemInList oldItem, @NonNull ShoppingListItemInList newItem) {
                return oldItem.equals(newItem);
            }
        });
        this.mViewModel = mViewModel;
        this.lifecycleOwner = lifecycleOwner;
    }

    @NonNull
    @Override
    public ShoppingListItemInListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ShoplistRecyclerItemViewBinding binding = ShoplistRecyclerItemViewBinding.inflate(inflater, parent, false);
        return new ShoppingListItemInListViewHolder(binding, mViewModel, lifecycleOwner);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingListItemInListViewHolder holder, int position) {
        ShoppingListItemInList item = getItem(position);
        holder.bind(item);
    }
}
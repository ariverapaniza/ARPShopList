package com.example.arpshoplist.shoplist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;

import com.example.arpshoplist.R;
import com.example.arpshoplist.shoppinglist.ShoppingListItem;

public class ShopListRecyclerViewAdapter extends ListAdapter<ShoppingListItem, ShopListRecyclerViewHolder> {

    private OnItemClickListener onItemClickListener;
    private OnAddToSavedListListener addToSavedListListener;
    private OnItemRemoveListener onItemRemoveListener;

    public interface OnItemRemoveListener {
        void onItemRemove(ShoppingListItem item);
    }

    public interface OnItemClickListener {
        void onGroceryItemClick(ShoppingListItem shoppingListItem);
    }

    public interface OnAddToSavedListListener {
        void onAddToSavedList(ShoppingListItem item);
    }

    public ShopListRecyclerViewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<ShoppingListItem> DIFF_CALLBACK = new DiffUtil.ItemCallback<ShoppingListItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ShoppingListItem oldItem, @NonNull ShoppingListItem newItem) {
            return oldItem.getItemid().equals(newItem.getItemid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShoppingListItem oldItem, @NonNull ShoppingListItem newItem) {
            return oldItem.getItemname().equals(newItem.getItemname()) && oldItem.getIconimage().equals(newItem.getIconimage());
        }
    };

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnAddToSavedListListener(OnAddToSavedListListener listener) {
        this.addToSavedListListener = listener;
    }

    @NonNull
    @Override
    public ShopListRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.shoplist_recycler_item_view, parent, false);
        return new ShopListRecyclerViewHolder(itemView, onItemClickListener);
    }

    public void setOnItemRemoveListener(OnItemRemoveListener listener) {
        this.onItemRemoveListener = listener;
    }

    @Override
    public void onBindViewHolder(@NonNull ShopListRecyclerViewHolder holder, int position) {
        ShoppingListItem currentShoppingListItem = getItem(position);
        holder.bind(currentShoppingListItem);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onGroceryItemClick(currentShoppingListItem);
            }
            if (addToSavedListListener != null) {
                addToSavedListListener.onAddToSavedList(currentShoppingListItem);
            }
            if (onItemRemoveListener != null) {
                onItemRemoveListener.onItemRemove(currentShoppingListItem);
            }
        });
    }
}

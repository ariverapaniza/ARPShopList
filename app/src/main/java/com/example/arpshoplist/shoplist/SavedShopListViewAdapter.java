package com.example.arpshoplist.shoplist;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.arpshoplist.R;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;

import java.util.List;

public class SavedShopListViewAdapter extends ListAdapter<ShoppingList, SavedShopListViewHolder> {
    private OnItemClickListener onItemClickListener;
    private SavedShopListViewHolder.OnItemClickListener onShopListClickListener;
    private List<ShoppingListItemInList> items;
    private Context context;
    public interface OnItemClickListener {
        void onShopListClickListener(ShoppingList shoppingList);
    }
    public SavedShopListViewAdapter() {
        super(DIFF_CALLBACK);
    }

    public static final DiffUtil.ItemCallback<ShoppingList> DIFF_CALLBACK = new DiffUtil.ItemCallback<ShoppingList>() {
        @Override
        public boolean areItemsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem.getShoppinglistid().equals(newItem.getShoppinglistid());
        }

        @Override
        public boolean areContentsTheSame(@NonNull ShoppingList oldItem, @NonNull ShoppingList newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }
    };
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public SavedShopListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.savedshoplist_recylcer_item_view, parent, false);
        return new SavedShopListViewHolder(itemView, onItemClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedShopListViewHolder holder, int position) {
        ShoppingList currentShoppingList = getItem(position);
        holder.bind(currentShoppingList);
    }
}

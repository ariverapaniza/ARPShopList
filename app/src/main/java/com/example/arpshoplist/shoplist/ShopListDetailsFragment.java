package com.example.arpshoplist.shoplist;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.ShopListDetailsFragmentBinding;
import com.example.arpshoplist.databinding.ShoplistRecyclerItemViewBinding;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItem;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;
import com.example.arpshoplist.ui.main.MainViewModel;

import java.util.ArrayList;
import java.util.List;

public class ShopListDetailsFragment extends Fragment {

    private ShopListDetailsFragmentBinding binding;
    private ShoplistRecyclerItemViewBinding shoplistRecyclerItemViewBinding;
    private ShoppingListItemInList currentShoppingListItemInList;
    private ShoppingListItem shoppingListItem;
    private ArrayList<ShoppingListItem> savedItems = new ArrayList<>();
    private static final String ARG_SHOPPINGLIST = "shoppinglist";
    private ShoppingList currentShoppingList;
    private ShopListRecyclerViewAdapter allItemsAdapter;
    private MainViewModel mViewModel;

    public static ShopListDetailsFragment newInstance(ShoppingList shoppingList, List<ShoppingListItemInList> items) {
        ShopListDetailsFragment fragment = new ShopListDetailsFragment();
        Bundle args = new Bundle();
        args.putSerializable("shoppinglist", shoppingList);
        args.putSerializable("items", new ArrayList<>(items));
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ShopListDetailsFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (getArguments() != null) {
            ShoppingList shoppingList = (ShoppingList) getArguments().getSerializable("shoppinglist");
            binding.savedShopListTitleEditText.setText(shoppingList.getTitle());

            ShoppingListItemInListViewAdapter adapter = new ShoppingListItemInListViewAdapter(mViewModel, getViewLifecycleOwner());
            binding.savedGroceryItemsInSavedShopListReyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            binding.savedGroceryItemsInSavedShopListReyclerView.setAdapter(adapter);
            mViewModel.getShopListItemsInListByShopListTableId(shoppingList.getShoppinglistid()).observe(getViewLifecycleOwner(), items -> {
                adapter.submitList(items);
            });

            allItemsAdapter = new ShopListRecyclerViewAdapter();
            binding.listGroceryItemsInSavedShopListReyclerView.setAdapter(allItemsAdapter);
            binding.listGroceryItemsInSavedShopListReyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

            mViewModel.getShopListItemsInListByShopListTableId(shoppingList.getShoppinglistid()).observe(getViewLifecycleOwner(), adapter::submitList);
            mViewModel.getAllShoppingListItems().observe(getViewLifecycleOwner(), allItemsAdapter::submitList);
        }
        binding.savedShoppingListDeleteButton.setOnClickListener(v -> {
            ShoppingList shoppingList = (ShoppingList) getArguments().getSerializable("shoppinglist");
            if (shoppingList != null) {
                mViewModel.delete(shoppingList);
                Toast.makeText(getActivity(), "Shopping List and associated data deleted", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    ShoppingFragment shoppingListFragment = new ShoppingFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction().replace(R.id.frame_layout, shoppingListFragment).commit();
                }, 2000);
            } else {
                Toast.makeText(getContext(), "Error: No shopping list to delete.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

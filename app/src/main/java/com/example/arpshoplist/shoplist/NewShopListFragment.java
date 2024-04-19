package com.example.arpshoplist.shoplist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.GroceryItemDetailsFragmentBinding;
import com.example.arpshoplist.databinding.NewShopListFragmentBinding;
import com.example.arpshoplist.login.SessionManager;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItem;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;
import com.example.arpshoplist.ui.main.MainViewModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class NewShopListFragment extends Fragment {

    private NewShopListFragmentBinding binding;
    private GroceryItemDetailsFragmentBinding groceryItemDetailsFragmentBinding;
    private MainViewModel mViewModel;
    private ShopListRecyclerViewAdapter allItemsAdapter;
    private ShopListRecyclerViewAdapter savedItemsAdapter;
    private ArrayList<ShoppingListItem> savedItems = new ArrayList<>();
    private ArrayList<ShoppingListItemInList> savedItemDetails = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    public static NewShopListFragment newInstance() {
        return new NewShopListFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = NewShopListFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);
        SessionManager sessionManager = new SessionManager(getContext());
        Integer currentUserId = sessionManager.getUserId();
        setupRecyclerViews();
        binding.newShoppingListSaveButton.setOnClickListener(v -> {
            if (currentUserId != null) {
                Date timestamp = new Date();
                String title = binding.newShopListTitleEditText.getText().toString();
                ShoppingList newShoppingList = new ShoppingList(currentUserId, timestamp, title);
                Long shoppingListId = mViewModel.insert(newShoppingList);

                if (shoppingListId != -1) {
                    for (ShoppingListItemInList itemDetail : savedItemDetails) {
                        itemDetail.setShoplisttableid(Math.toIntExact(shoppingListId));
                        mViewModel.insert(itemDetail);
                    }
                }
                Toast.makeText(getActivity(), "Shopping List Added Successfully", Toast.LENGTH_SHORT).show();
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    ShoppingFragment shoppingFragment = new ShoppingFragment();
                    FragmentManager fragmentManager = getParentFragmentManager();
                    fragmentManager.beginTransaction()
                            .replace(R.id.frame_layout, shoppingFragment)
                            .commit();
                }, 2000);
            }
        });

    }

    private void setupRecyclerViews() {
        allItemsAdapter = new ShopListRecyclerViewAdapter();
        allItemsAdapter.setOnAddToSavedListListener(this::addToSavedList);
        binding.listGroceryItemsInNewShopListReyclerView.setAdapter(allItemsAdapter);
        binding.listGroceryItemsInNewShopListReyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        savedItemsAdapter = new ShopListRecyclerViewAdapter();
        savedItemsAdapter.setOnItemRemoveListener(this::removeFromSavedList);
        binding.savedGroceryItemsInNewShopListReyclerView.setAdapter(savedItemsAdapter);
        binding.savedGroceryItemsInNewShopListReyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));

        binding.newShopListTitleEditText.getText().toString();

        mViewModel.getAllShoppingListItems().observe(getViewLifecycleOwner(), allItemsAdapter::submitList);
    }

    private void addToSavedList(ShoppingListItem item) {
        ShoppingListItemInList itemDetail = new ShoppingListItemInList(1, item.getItemid());
        savedItemDetails.add(itemDetail);
        if (!savedItems.contains(item)) {
            savedItems.add(item);
            savedItemsAdapter.submitList(new ArrayList<>(savedItems));
            savedItemsAdapter.notifyDataSetChanged();
        }
    }

    private void removeFromSavedList(ShoppingListItem item) {
        savedItems.remove(item);
        savedItemsAdapter.submitList(new ArrayList<>(savedItems));
        savedItemsAdapter.notifyDataSetChanged();

        savedItemDetails.removeIf(itemDetail -> itemDetail.getShoplistitemtableid().equals(item.getItemid()));
    }
    private void openShopListDetailsFragment(ShoppingListItem shoppingListItem) {
        GroceryItemDetailsFragment groceryItemDetailsFragment = GroceryItemDetailsFragment.newInstance();
        FragmentManager fragmentManager = getParentFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.shopListFloatingActionButton, groceryItemDetailsFragment)
                .addToBackStack(null)
                .commit();
    }
}
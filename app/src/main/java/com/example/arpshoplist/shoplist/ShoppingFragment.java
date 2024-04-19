package com.example.arpshoplist.shoplist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.arpshoplist.R;
import com.example.arpshoplist.databinding.ShoppingFragmentBinding;
import com.example.arpshoplist.login.SessionManager;
import com.example.arpshoplist.shoppinglist.ShoppingList;
import com.example.arpshoplist.shoppinglist.ShoppingListItemInList;
import com.example.arpshoplist.ui.main.MainViewModel;

import java.util.List;


public class ShoppingFragment extends Fragment {

    private ShoppingFragmentBinding binding;
    private MainViewModel mViewModel;
    private List<ShoppingListItemInList> currentShoppingListItemInList;

    public static ShoppingFragment newInstance() { return new ShoppingFragment(); }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = ShoppingFragmentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SessionManager sessionManager = new SessionManager(getContext());
        Integer currentUserId = sessionManager.getUserId();
        mViewModel = new ViewModelProvider(this).get(MainViewModel.class);

        setupRecyclerView(currentUserId);
    }

    private void setupRecyclerView(Integer currentUserId) {
        final SavedShopListViewAdapter adapter = new SavedShopListViewAdapter();
        adapter.setOnItemClickListener(shoppinglist -> openShoplistDetailsFragment(shoppinglist));
        binding.savedShopListRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.savedShopListRecyclerView.setAdapter(adapter);
        mViewModel.getListByUserId(currentUserId).observe(getViewLifecycleOwner(), adapter::submitList);
    }
    private void openShoplistDetailsFragment(ShoppingList shoppinglist){
        mViewModel.getShopListItemsInListByShopListTableId(shoppinglist.getShoppinglistid()).observe(getViewLifecycleOwner(), items -> {
            ShopListDetailsFragment shopListDetailsFragment = ShopListDetailsFragment.newInstance(shoppinglist, items);
            FragmentManager fragmentManager = getParentFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.frame_layout, shopListDetailsFragment)
                    .addToBackStack(null)
                    .commit();
        });
    }

}
package com.example.arpshoplist;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;

import com.example.arpshoplist.recipe.NewRecipeFragment;
import com.example.arpshoplist.recipe.RecipeDetailsFragment;
import com.example.arpshoplist.recipe.RecipeFragment;
import com.example.arpshoplist.settings.SettingsFragment;
import com.example.arpshoplist.shoplist.ShoppingFragment;
import com.example.arpshoplist.ui.main.HomePageFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void onBackPressed() {
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.frame_layout);
        if (currentFragment instanceof HomePageFragment ||
                currentFragment instanceof NewRecipeFragment ||
                currentFragment instanceof RecipeFragment ||
                currentFragment instanceof SettingsFragment ||
                currentFragment instanceof ShoppingFragment ||
                currentFragment instanceof RecipeDetailsFragment) {
            finish();
        } else {
            super.onBackPressed();
        }
    }
}
package net.myerichsen.toiletpaper;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.preference.PreferenceManager;

import com.google.android.material.navigation.NavigationView;

import net.myerichsen.toiletpaper.ui.compare.CompareListFragment;
import net.myerichsen.toiletpaper.ui.compare.CompareListFragmentDirections;
import net.myerichsen.toiletpaper.ui.compare.CompareModel;
import net.myerichsen.toiletpaper.ui.home.BrandListFragment;
import net.myerichsen.toiletpaper.ui.home.BrandModel;
import net.myerichsen.toiletpaper.ui.home.ItemNoListFragment;
import net.myerichsen.toiletpaper.ui.home.ItemNoModel;
import net.myerichsen.toiletpaper.ui.prices.PriceListFragment;
import net.myerichsen.toiletpaper.ui.prices.PriceListFragmentDirections;
import net.myerichsen.toiletpaper.ui.prices.PriceModel;
import net.myerichsen.toiletpaper.ui.settings.SettingsActivity;

import java.util.Deque;
import java.util.List;
import java.util.Objects;

import static androidx.navigation.Navigation.findNavController;
import static net.myerichsen.toiletpaper.ui.home.HomeFragment.BRAND;
import static net.myerichsen.toiletpaper.ui.home.HomeFragment.ITEM_NO;

/*
 * Copyright (c) 2020. Michael Erichsen.
 *
 * The program is distributed under the terms of the GNU Affero General Public License v3.0
 */

public class MainActivity extends AppCompatActivity implements PriceListFragment.OnListFragmentInteractionListener,
        ItemNoListFragment.OnListFragmentInteractionListener, BrandListFragment.OnListFragmentInteractionListener,
        CompareListFragment.OnListFragmentInteractionListener {
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home,
                R.id.nav_compare,
                R.id.nav_price_select,
                R.id.nav_products,
                R.id.nav_suppliers,
                R.id.nav_about)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.activity_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        if (id == R.id.fragment_help) {
            showHelpDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showHelpDialog() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        float fontSize = Float.parseFloat(preferences.getString("fontsize", "24"));

        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_help);
        dialog.setTitle("@string/help");

        TextView text = dialog.findViewById(R.id.HelpDialogTextView);
        text.setTextSize(fontSize);

        String s = getCallingFragmentLabel();
        switch (Objects.requireNonNull(s)) {
            case "Butikker":
                text.setText(getString(R.string.suppliers_help_text));
                break;
            case "Butiksdetaljer":
                text.setText(getString(R.string.supplier_details_home_help_text));
                break;
            case "Fundne varemærker":
                text.setText(getString(R.string.brand_list_help_text));
                break;
            case "Fundne varenumre":
                text.setText(getString(R.string.item_no_list_help_text));
                break;
            case "Om":
                text.setText(getString(R.string.about_help_text));
                break;
            case "Prisudvikling, grafisk":
                text.setText(getString(R.string.prices_graph_help_text));
                break;
            case "Prisudvikling, valg vare":
                text.setText(getString(R.string.prices_select_help_text));
                break;
            case "Produktdetaljer":
                text.setText(getString(R.string.product_details_help_text));
                break;
            case "Produkter":
                text.setText(getString(R.string.products_help_text));
                break;
            case "Prisudvikling":
                text.setText(getString(R.string.price_list_help_text));
                break;
            case "Sammenlign":
                text.setText(getString(R.string.compare_help_text));
                break;
            case "Scan":
                text.setText(getString(R.string.scan_help_text));
                break;
            case "Sorteret produktliste":
            case "Sorterede priser":
                text.setText(getString(R.string.sorted_product_list_help_text));
                break;
            case "Toiletpapir":
                text.setText(getString(R.string.home_help_text));
                break;
            default:
                text.setText(getString(R.string.default_help_text, s));
                break;
        }

        Button dialogButton = dialog.findViewById(R.id.helpDialogButtonOK);
        // if button is clicked, close the custom dialog
        dialogButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private String getCallingFragmentLabel() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        for (Fragment fragment : fragments) {
            NavController navController;
            if (fragment != null && fragment.isVisible()) {

                try {
                    navController = Navigation.findNavController(fragment.requireView());
                    @SuppressLint("RestrictedApi") Deque<NavBackStackEntry> backStack = navController.getBackStack();
                    NavBackStackEntry f = backStack.getLast();
                    NavDestination d = f.getDestination();
                    return Objects.requireNonNull(d.getLabel()).toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    @Override
    public void onListFragmentInteraction(ItemNoModel.ItemNoItem item) {
        Bundle result = new Bundle();
        result.putString(ITEM_NO, item.itemNo);
        getSupportFragmentManager().setFragmentResult("itemNoRequestKey", result);
        onBackPressed();
    }

    @Override
    public void onListFragmentInteraction(BrandModel.BrandItem item) {
        Bundle result = new Bundle();
        result.putString(BRAND, item.brand);
        getSupportFragmentManager().setFragmentResult("brandRequestKey", result);
        onBackPressed();
    }

    @Override
    public void onListFragmentInteraction(PriceModel.PriceItem item) {
        PriceListFragmentDirections.ActionNavPricesListToNavProductDetails action =
                PriceListFragmentDirections.actionNavPricesListToNavProductDetails(item.uid);
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(action);
    }

    @Override
    public void onListFragmentInteraction(CompareModel.CompareItem item) {
        CompareListFragmentDirections.ActionNavCompareListToNavProductDetails action =
                CompareListFragmentDirections.actionNavCompareListToNavProductDetails(item.uid);
        Navigation.findNavController(this, R.id.nav_host_fragment).navigate(action);
    }
}

package com.example.projectjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNav;
    private MaterialToolbar topAppBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FirebaseApp.initializeApp(this);

        bottomNav = findViewById(R.id.bottom_navigation);
        topAppBar = findViewById(R.id.topAppBar);

        // Set toolbar as ActionBar
        setSupportActionBar(topAppBar);

        // Default fragment
        replaceFragment(new HomeFragment());

        // Bottom navigation listener
        bottomNav.setOnItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int id = item.getItemId();

            if (id == R.id.nav_home) selectedFragment = new HomeFragment();
            else if (id == R.id.nav_add) selectedFragment = new AddExpenseFragment();
            else if (id == R.id.nav_detail) selectedFragment = new Expenselist();

            if (selectedFragment != null) replaceFragment(selectedFragment);
            return true;
        });

        // Handle navigation if started with Intent
        handleNavigationIntent(getIntent());
    }

    // Inflate the Sign Out menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.top_app_bar_menu, menu);
        return true;
    }

    // Handle Sign Out click
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_sign_out) {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(MainActivity.this, log_in.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        handleNavigationIntent(intent);
    }

    private void handleNavigationIntent(Intent intent) {
        if (intent == null) return;
        String navigateTo = intent.getStringExtra("navigateTo");
        if (navigateTo == null) return;

        Fragment targetFragment = null;
        switch (navigateTo) {
            case "home":
                targetFragment = new HomeFragment();
                bottomNav.setSelectedItemId(R.id.nav_home);
                break;
            case "addExpense":
                targetFragment = new AddExpenseFragment();
                bottomNav.setSelectedItemId(R.id.nav_add);
                break;
            case "detailList":
                targetFragment = new Expenselist();
                bottomNav.setSelectedItemId(R.id.nav_detail);
                break;
        }

        if (targetFragment != null) replaceFragment(targetFragment);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_container, fragment);
        ft.commit();
    }
}

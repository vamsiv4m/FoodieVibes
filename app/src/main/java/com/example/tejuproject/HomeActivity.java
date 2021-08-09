package com.example.tejuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import fragments.Burger_fragment;
import fragments.CartFragment;
import fragments.OrderFragment;
import fragments.Fries_fragment;
import fragments.Icecream_fragment;
import fragments.Pizza_fragment;
import fragments.Rice_fragment;
import fragments.Shakes_fragment;
import owner.FoodieVibes;

public class HomeActivity extends AppCompatActivity {

    ImageView burger, pizza, rice, shakes, icecream, fries, userinfo;
    ChipNavigationBar chipNavigationBar;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user==null) {
            Intent i = new Intent(HomeActivity.this, Login.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
        else if(Objects.equals(user.getEmail(), "foodievibesowner@gmail.com")){
            Intent i = new Intent(HomeActivity.this, FoodieVibes.class);
            i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(i);
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Handler handler = new Handler();
        handler.postDelayed(() -> {
        }, 700);

        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        sharedPreferences=getSharedPreferences("filename",0);
        editor=sharedPreferences.edit();
        burger = findViewById(R.id.burger);
        pizza = findViewById(R.id.pizza);
        rice = findViewById(R.id.rice);
        icecream = findViewById(R.id.icecream);
        shakes = findViewById(R.id.shakes);
        fries = findViewById(R.id.fries);
        userinfo = findViewById(R.id.imageView3);

        bottomnav();
        Fragment currentfragment = getSupportFragmentManager().findFragmentByTag("burger");
        if (currentfragment instanceof Burger_fragment) {
            burger.setEnabled(false);
        }
        userinfo.setOnClickListener(view -> {
            DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
            reference.child(FirebaseAuth.getInstance().getUid() + "").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                    Log.d("myusername",snapshot.child("username").getValue()+"");
                    editor.putString("uname", snapshot.child("username").getValue()+"");
                    editor.apply();
                }
                @Override
                public void onCancelled(@NonNull @NotNull DatabaseError error) {

                }
            });

            Intent i = new Intent(HomeActivity.this,UserSettings.class);
            i.putExtra("name",sharedPreferences.getString("uname",""));
            startActivity(i);

        });

        burger.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Burger_fragment(), "burger").addToBackStack("").commit());
        pizza.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Pizza_fragment()).addToBackStack("").commit());
        rice.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Rice_fragment()).addToBackStack("").commit());
        icecream.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Icecream_fragment()).addToBackStack("").commit());
        fries.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Fries_fragment()).addToBackStack("").commit());
        shakes.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Shakes_fragment()).addToBackStack("").commit());

    }

    @SuppressLint("NonConstantResourceId")
    private void bottomnav() {
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setOnItemSelectedListener(i -> {
            Fragment fragment;
            FrameLayout frameLayout;
            switch (i) {
                case R.id.cart:
                    frameLayout = findViewById(R.id.cartframe);
                    frameLayout.setVisibility(View.VISIBLE);
                    fragment = new CartFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.cartframe, fragment).addToBackStack(null).commit();
                    break;
                case R.id.orders:
                    frameLayout = findViewById(R.id.cartframe);
                    frameLayout.setVisibility(View.VISIBLE);
                    fragment = new OrderFragment();
                    getSupportFragmentManager().beginTransaction().replace(R.id.cartframe, fragment).addToBackStack(null).commit();
                    break;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FrameLayout frameLayout = findViewById(R.id.cartframe);
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        if (chipNavigationBar.getSelectedItemId() == R.id.cart) {
            getSupportFragmentManager().beginTransaction().remove(new CartFragment()).commit();
            chipNavigationBar.setItemSelected(R.id.cart, false);
            frameLayout.setVisibility(View.GONE);
        } else if (chipNavigationBar.getSelectedItemId() == R.id.orders) {
            getSupportFragmentManager().beginTransaction().remove(new OrderFragment()).commit();
            chipNavigationBar.setItemSelected(R.id.orders, false);
            frameLayout.setVisibility(View.GONE);
        }
    }
}
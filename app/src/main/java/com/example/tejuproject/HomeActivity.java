package com.example.tejuproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.ismaeldivita.chipnavigation.ChipNavigationBar;
import fragments.Burger_fragment;
import fragments.CartFragment;
import fragments.OrderFragment;
import fragments.Fries_fragment;
import fragments.Icecream_fragment;
import fragments.Pizza_fragment;
import fragments.Rice_fragment;
import fragments.Shakes_fragment;

public class HomeActivity extends AppCompatActivity {
    ImageView burger, pizza, rice, shakes, icecream, fries,userinfo;
    ChipNavigationBar chipNavigationBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        burger = findViewById(R.id.burger);
        pizza = findViewById(R.id.pizza);
        rice = findViewById(R.id.rice);
        icecream = findViewById(R.id.icecream);
        shakes = findViewById(R.id.shakes);
        fries = findViewById(R.id.fries);
        userinfo=findViewById(R.id.imageView3);
        bottomnav();
        Fragment currentfragment=getSupportFragmentManager().findFragmentByTag("burger");
        if (currentfragment instanceof Burger_fragment){
            burger.setEnabled(false);
        }
        userinfo.setOnClickListener(view -> {
            Intent i=new Intent(HomeActivity.this,UserSettings.class);
            startActivity(i);
        });
        burger.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Burger_fragment(),"burger").addToBackStack("").commit());
        pizza.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Pizza_fragment()).addToBackStack("").commit());
        rice.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Rice_fragment()).addToBackStack("").commit());
        icecream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Icecream_fragment()).addToBackStack("").commit();
            }
        });
        fries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Fries_fragment()).addToBackStack("").commit();
            }
        });
        shakes.setOnClickListener(view -> getSupportFragmentManager().beginTransaction().replace(R.id.framelayout, new Shakes_fragment()).addToBackStack("").commit());

    }

    private void bottomnav() {
        chipNavigationBar = findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public void onItemSelected(int i) {
                Fragment fragment = null;
                switch (i) {
                    case R.id.cart:
                        FrameLayout frameLayout=findViewById(R.id.cartframe);
                        frameLayout.setVisibility(View.VISIBLE);
                        fragment = new CartFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.cartframe, fragment).addToBackStack(null).commit();
                        break;
                    case R.id.orders:
                        fragment = new OrderFragment();
                        getSupportFragmentManager().beginTransaction().replace(R.id.cartframe, fragment).addToBackStack(null).commit();
                        break;
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        FrameLayout frameLayout=findViewById(R.id.cartframe);
        getSupportFragmentManager().beginTransaction().remove(new CartFragment()).commit();
        chipNavigationBar=findViewById(R.id.chipNavigationBar);
        chipNavigationBar.setItemSelected(R.id.cart,false);
        frameLayout.setVisibility(View.GONE);
    }
}
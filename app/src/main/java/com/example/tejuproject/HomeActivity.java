package com.example.tejuproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import fragments.Burger_fragment;
import fragments.Fries_fragment;
import fragments.Icecream_fragment;
import fragments.Pizza_fragment;
import fragments.Rice_fragment;
import fragments.Shakes_fragment;

public class HomeActivity extends AppCompatActivity {
    ImageView burger,pizza,rice,shakes,icecream,fries;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        burger=findViewById(R.id.burger);
        pizza=findViewById(R.id.pizza);
        rice=findViewById(R.id.rice);
        icecream=findViewById(R.id.icecream);
        shakes=findViewById(R.id.shakes);
        fries=findViewById(R.id.fries);

        burger.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Burger_fragment()).addToBackStack("").commit();
            }
        });
        pizza.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Pizza_fragment()).addToBackStack("").commit();
            }
        });
        rice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Rice_fragment()).addToBackStack("").commit();
            }
        });
        icecream.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Icecream_fragment()).addToBackStack("").commit();
            }
        });
        fries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Fries_fragment()).addToBackStack("").commit();
            }
        });
        shakes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getSupportFragmentManager().beginTransaction().replace(R.id.framelayout,new Shakes_fragment()).addToBackStack("").commit();
            }
        });

    }

}
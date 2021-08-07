package com.example.tejuproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DoneActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);
        String uid= FirebaseAuth.getInstance().getUid();
        Intent i=getIntent();
        String itemname=i.getStringExtra("itemname");
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        reference.child(uid).child("mycart").child(itemname).removeValue();
        TextView textView=findViewById(R.id.successful);
        LottieAnimationView lottieAnimationView=findViewById(R.id.lottieAnimationView);
        lottieAnimationView.playAnimation();
        textView.animate().alpha(1).setDuration(1500).start();
    }
    public void back(View view) {
        onBackPressed();
    }
}
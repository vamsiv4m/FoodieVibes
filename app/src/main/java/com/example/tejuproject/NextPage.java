package com.example.tejuproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.DatabaseRegistrar;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import java.lang.ref.Reference;

import models.CartModel;

public class NextPage extends AppCompatActivity {
    ImageView nextback, nextImage;
    String favstatus = "0";
    Button cart;
    EditText billname,address;
    TextView nextItemname, nextprice, smalllabel, totalprice, format, success;
    SharedPreferences sharedPreferences;
    private static final String filename = "filename";
    private static final String username = "username";
    ImageView smallminus;
    ImageView smallplus;
    int spc = 1;
    int p1,p2=0;
    LottieAnimationView lottieAnimationView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(filename, 0);
        String uname = sharedPreferences.getString(username, "");
        setContentView(R.layout.activity_next_page);
        billname=findViewById(R.id.billname);
        address=findViewById(R.id.address);
        lottieAnimationView = findViewById(R.id.lottiefav);
        nextback = findViewById(R.id.nextback);
        nextprice = findViewById(R.id.nextprice);
        nextImage = findViewById(R.id.nextImage);
        cart=findViewById(R.id.cart);
        format = findViewById(R.id.format);
        nextItemname = findViewById(R.id.nextitemname);
        smallminus = findViewById(R.id.smallminus);
        smallplus = findViewById(R.id.smallplus);
        smalllabel = findViewById(R.id.smallLabel);
        totalprice = findViewById(R.id.totalprice);
        nextback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        Intent i = getIntent();
        String imgurl = i.getStringExtra("image");
        String price = i.getStringExtra("price");
        String size = i.getStringExtra("size");
        String name = i.getStringExtra("name");
        String type = i.getStringExtra("type");
//        int f = i.getIntExtra("fav", 0);
        nextItemname.setText(name);
        nextprice.setText(price);
        Glide.with(this)
                .asBitmap()
                .load(imgurl)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        nextImage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });


        nextprice.setText(price);
        totalprice.setText("Total Price : ₹ "+price);
        smallplus.setOnClickListener(view -> {
            spc += 1;
            p1 = Integer.parseInt(price);
            if (spc > 0 && spc <= 5) {
                smalllabel.setText("0" + spc);
                p2 = p1 * spc;
                totalprice.setText("Total Price : ₹ " + p2);
                format.setText(spc+" "+size);
            }
            else{
                spc=5;
            }
        });
        smallminus.setOnClickListener(view -> {
            spc -= 1;
            p1 = Integer.parseInt(price);
            if (spc >= 1) {
                if (spc <= 5) {
                    smalllabel.setText("0" + spc);
                    p2 = p1 * spc;
                    totalprice.setText("Total Price : ₹ " + p2);
                    format.setText(spc+" "+size);
                }
            } else {
                spc=1;
            }
        });


        lottieAnimationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottieAnimationView.getAnimation();
                int a=1;
                DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users");
                reference.child(uname).child("favourites").child(name).child(a+"").setValue(a+"");
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String bn=billname.getText().toString();
                String add=address.getText().toString();
                if (bn!=null && add!=null){
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("users");
                    CartModel cartModel=new CartModel(imgurl,name,price,bn,add,spc,p2);
                    reference.child(uname).child("mycart").child(name).setValue(cartModel);
                    Toast.makeText(NextPage.this, "Successfully Added.", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(NextPage.this, "Fields Cannot Empty", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
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
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.airbnb.lottie.LottieAnimationView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

public class NextPage extends AppCompatActivity {
    ImageView nextback, nextImage;
    String favstatus = "0";
    TextView nextItemname, nextprice, smalllabel, totalprice, format;
    SharedPreferences sharedPreferences;
    private static final String filename = "filename";
    private static final String username = "username";
    ImageView smallminus;
    ImageView smallplus;
    int spc = 1;
    int p1;
    LottieAnimationView lottieAnimationView;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences(filename, 0);
        String uname = sharedPreferences.getString(username, "");
        setContentView(R.layout.activity_next_page);
        lottieAnimationView = findViewById(R.id.lottiefav);
        nextback = findViewById(R.id.nextback);
        nextprice = findViewById(R.id.nextprice);
        nextImage = findViewById(R.id.nextImage);
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
        String name = i.getStringExtra("name");
        String type = i.getStringExtra("type");
        favstatus = i.getStringExtra("fav");
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
//        lottieAnimationView.setOnClickListener(view -> {
//            lottieAnimationView.playAnimation();
//            int favstatus = 1;
//            reference.child(uname).child("favourites").child(name).child("itemname").setValue(name);
//            reference.child(uname).child("favourites").child(name).child("price").setValue(price);
//            reference.child(uname).child("favourites").child(name).child("imgurl").setValue(imgurl);
//            reference.child(uname).child("favourites").child(name).child("favstatus").setValue(favstatus);
//            reference.child(uname).child("favourites").child(name).child("itemtype").setValue(type);
//        });
        totalprice.setText(price);
        smallplus.setOnClickListener(view -> {
            spc += 1;
            p1 = Integer.parseInt(price);
            if (spc > 0 && spc <= 5) {
                smalllabel.setText("0" + spc);
                int p2 = p1 * spc;
                totalprice.setText("Total Price : ₹ " + p2);
                format.setText("0" + spc + " x " + p1 + " = " + p2);
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
                    int p2 = p1 * spc;
                    totalprice.setText("Total Price : ₹ " + p2);
                    format.setText("0" + spc + " x " + p1 + " = " + p2);
                }
            } else {
                spc=1;
            }
        });
    }
}
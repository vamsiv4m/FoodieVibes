package fragments;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.tejuproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import adapters.CartAdapter;
import models.CartModel2;


public class CartFragment extends Fragment {
    TextView carttext;
    ImageView cartImg;
    SharedPreferences sharedPreferences;
    private final static String filename="filename";
    private final static String username="username";
    FrameLayout frameLayout;
    List<CartModel2> list=new ArrayList<>();
    public CartFragment() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences=getContext().getSharedPreferences(filename,0);
        String uname=sharedPreferences.getString(username,"");
        String uid= FirebaseAuth.getInstance().getUid();
        Log.d("myuname",uname+"");
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
        cartImg=v.findViewById(R.id.cartimg);
        carttext=v.findViewById(R.id.carttext);
        carttext.setVisibility(View.GONE);
        frameLayout=v.findViewById(R.id.framelayout);
        ProgressBar progressBar=v.findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView=v.findViewById(R.id.cartrecycler);
        CartAdapter cartAdapter=new CartAdapter(getContext(),list);
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(cartAdapter);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                assert uid != null;
                for (DataSnapshot dataSnapshot:snapshot.child(uid).child("mycart").getChildren()) {
                    if (dataSnapshot!=null) {cartImg.setVisibility(View.VISIBLE);
                        frameLayout.setBackgroundColor(Color.parseColor("#F7F3FF"));
                        progressBar.setVisibility(View.GONE);
                        cartImg.setVisibility(View.GONE);
                        carttext.setVisibility(View.GONE);
                        CartModel2 cartModel = dataSnapshot.getValue(CartModel2.class);
                        list.add(cartModel);
                    }
                }
                cartAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                cartImg.setVisibility(View.VISIBLE);
                carttext.setVisibility(View.VISIBLE);

            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        return v;
    }
}
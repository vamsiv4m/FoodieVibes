package fragments;

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

import adapters.OrdersAdapter;
import models.OrdersModel;

public class OrderFragment extends Fragment {
    List<OrdersModel> list=new ArrayList<>();
    ImageView orderimg;
    TextView ordertext;
    public OrderFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_fav, container, false);
        String uid= FirebaseAuth.getInstance().getUid();
        orderimg=v.findViewById(R.id.orderimg);
        ordertext=v.findViewById(R.id.ordertext);
        ordertext.setVisibility(View.GONE);
        ProgressBar progressBar=v.findViewById(R.id.progressBar2);
        progressBar.setVisibility(View.VISIBLE);
        RecyclerView recyclerView=v.findViewById(R.id.orderrecycler);
        OrdersAdapter ordersAdapter=new OrdersAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(ordersAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
        assert uid != null;
        reference.child(uid).child("myorders").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.getChildren()){
                    if (dataSnapshot!=null){
                        FrameLayout layout=v.findViewById(R.id.framelayoutfav);
                        layout.setBackgroundColor(Color.parseColor("#F7F3FF"));
                        ordertext.setVisibility(View.GONE);
                        orderimg.setVisibility(View.GONE);
                        Log.d("mydatasnapshot",dataSnapshot+"");
                        OrdersModel ordersModel=dataSnapshot.getValue(OrdersModel.class);
                        list.add(ordersModel);
                    }
                }
                ordersAdapter.notifyDataSetChanged();
                progressBar.setVisibility(View.GONE);
                orderimg.setVisibility(View.VISIBLE);
                ordertext.setVisibility(View.VISIBLE);
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {
            }
        });
        return v;
    }
}
package fragments;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tejuproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import adapters.CartAdapter;
import models.CartModel;
import models.CartModel2;


public class CartFragment extends Fragment {
    SharedPreferences sharedPreferences;
    private final static String filename="filename";
    private final static String username="username";
    List<CartModel2> list=new ArrayList<>();
    public CartFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        sharedPreferences=getContext().getSharedPreferences(filename,0);
        String uname=sharedPreferences.getString(username,"");
        View v = inflater.inflate(R.layout.fragment_cart, container, false);
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
                for (DataSnapshot dataSnapshot:snapshot.child(uname).child("mycart").getChildren()){
                    CartModel2 cartModel=dataSnapshot.getValue(CartModel2.class);
                    list.add(cartModel);
                }
                cartAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return v;
    }
}
package fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
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

import adapters.BurgerItemsAdapter;
import models.BurgerModel;

public class Burger_fragment extends Fragment {
    RecyclerView recyclerView;
    List<BurgerModel> list=new ArrayList<>();

    public Burger_fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_burger_fragment, container, false);
        recyclerView=v.findViewById(R.id.ricerecyclerview);
        BurgerItemsAdapter burgerItemsAdapter=new BurgerItemsAdapter(list,getContext());
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext(),RecyclerView.VERTICAL,false);
        recyclerView.setAdapter(burgerItemsAdapter);
        recyclerView.setLayoutManager(linearLayoutManager);
        DatabaseReference reference= FirebaseDatabase.getInstance().getReference("items");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot:snapshot.child("burger").getChildren()){
                    Log.d("mydatasnapshot",dataSnapshot+"");
                    BurgerModel burgerModel=dataSnapshot.getValue(BurgerModel.class);
                    list.add(burgerModel);
                }
                burgerItemsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
        return v;
    }
}
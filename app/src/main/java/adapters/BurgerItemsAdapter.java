package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.tejuproject.NextPage;
import com.example.tejuproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import models.BurgerModel;
import models.Favmodel;

public class BurgerItemsAdapter extends RecyclerView.Adapter<BurgerItemsAdapter.MyHolder> {
    List<BurgerModel> list;
    Context context;
    String favstatus;
    private static final String filename="filename";
    private static final String username="username";

    public BurgerItemsAdapter(List<BurgerModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.itemslayout,parent,false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        final int[] a = {0};
        SharedPreferences sharedPreferences=context.getSharedPreferences(filename,0);
        String uname=sharedPreferences.getString(username,"");
        holder.price.setText(list.get(position).getPrice()+"");
        holder.itemname.setText(list.get(position).getImageName()+"");
        Glide.with(context).load(list.get(position).getImagetype()).into(holder.itemtype);
        Glide.with(context).load(list.get(position).getItemImage()).into(holder.itemImage);
        holder.size.setText(list.get(position).getSize()+"");
        holder.small.setOnClickListener(view -> {
                    Intent i=new Intent(context, NextPage.class);
                    i.putExtra("image",list.get(position).getItemImage());
                    i.putExtra("price",list.get(position).getPrice().substring(2));
                    i.putExtra("name",list.get(position).getImageName());
                    i.putExtra("size","small");
                    i.putExtra("type",list.get(position).getImagetype());
//                  i.putExtra("fav",a[0]);
                    context.startActivity(i);
        });

        holder.medium.setOnClickListener(view -> {
            Intent i=new Intent(context, NextPage.class);
            i.putExtra("image",list.get(position).getItemImage());
            i.putExtra("price",Integer.parseInt(list.get(position).getPrice().substring(2))+35+"");
            i.putExtra("name",list.get(position).getImageName());
            i.putExtra("size","medium");
            i.putExtra("type",list.get(position).getImagetype());
            i.putExtra("fav",favstatus);
            context.startActivity(i);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        Button small,medium;
        TextView price,size,itemname;
        ImageView itemtype,itemImage;
        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            small=itemView.findViewById(R.id.small);
            medium=itemView.findViewById(R.id.medium);
            itemImage=itemView.findViewById(R.id.itemImage);
            itemtype=itemView.findViewById(R.id.itemtype);
            itemname=itemView.findViewById(R.id.itemname);
            price=itemView.findViewById(R.id.price);
            size=itemView.findViewById(R.id.size);
        }
    }
}

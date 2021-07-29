package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tejuproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import models.BurgerModel;

public class BurgerItemsAdapter extends RecyclerView.Adapter<BurgerItemsAdapter.MyHolder> {
    List<BurgerModel> list;
    Context context;

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
        holder.price.setText(list.get(position).getPrice()+"");
        holder.itemname.setText(list.get(position).getImageName()+"");
        Glide.with(context).load(list.get(position).getImagetype()).into(holder.itemtype);
        Glide.with(context).load(list.get(position).getItemImage()).into(holder.itemImage);
        holder.size.setText(list.get(position).getSize()+"");

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder{
        Button add;
        TextView price,size,itemname;
        ImageView itemtype,itemImage;
        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            add=itemView.findViewById(R.id.add);
            itemImage=itemView.findViewById(R.id.itemImage);
            itemtype=itemView.findViewById(R.id.itemtype);
            itemname=itemView.findViewById(R.id.itemname);
            price=itemView.findViewById(R.id.price);
            size=itemView.findViewById(R.id.size);
        }
    }
}

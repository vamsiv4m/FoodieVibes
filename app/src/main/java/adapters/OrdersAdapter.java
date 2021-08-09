package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.tejuproject.R;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import models.OrderModel2;
import models.OrdersModel;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.MyHolder> {
    List<OrdersModel> list;
    Context context;

    public OrdersAdapter(List<OrdersModel> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.orderslayout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        holder.itemname.setText(list.get(position).getOrdername());
        holder.count.setText(list.get(position).getCount()+" "+list.get(position).getSize());
        holder.price.setText("₹ "+list.get(position).getTotal());
        Glide.with(context)
                .asBitmap()
                .load(list.get(position).getImg()).centerCrop()
                .into(new CustomTarget<Bitmap>(){
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                        holder.itemimage.setImageBitmap(resource);
                    }

                    @Override
                    public void onLoadCleared(@Nullable Drawable placeholder) {
                    }
                });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder{
        ImageView itemimage;
        TextView itemname,price;
        TextView count;
        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            itemimage=itemView.findViewById(R.id.orderitemimg);
            itemname=itemView.findViewById(R.id.ordereditemname);
            count=itemView.findViewById(R.id.ordercount);
            price=itemView.findViewById(R.id.orderprice);
        }
    }
}

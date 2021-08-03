package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tejuproject.R;
import org.jetbrains.annotations.NotNull;
import java.util.List;
import models.CartModel;
import models.CartModel2;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.MyViewHolder> {
    Context context;
    List<CartModel2> list;

    public CartAdapter(Context context, List<CartModel2> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.cartdesign,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        holder.cartprice.setText(String.valueOf("₹ "+list.get(position).getTotal()));
        holder.cartItemName.setText(list.get(position).getItemname());
        holder.cartcount.setText(String.valueOf(list.get(position).getCount())+" Items");
        Glide.with(context).load(list.get(position).getImageurl()).into(holder.cartItemImage);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView cartItemImage;
        TextView cartItemName,cartprice,cartcount;
        @SuppressLint("CutPasteId")
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cartcount=itemView.findViewById(R.id.cartcount);
            cartItemImage=itemView.findViewById(R.id.cartdesignimg);
            cartItemName=itemView.findViewById(R.id.cartitemname);
            cartprice=itemView.findViewById(R.id.cartprice);
        }
    }
}

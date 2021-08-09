package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
import models.OrdersModel;
import owner.Order_Details;

public class ViewOrdersAdapter extends RecyclerView.Adapter<ViewOrdersAdapter.MyHolder> {

    Context context;
    List<OrdersModel> list;

    public ViewOrdersAdapter(Context context, List<OrdersModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new MyHolder(LayoutInflater.from(context).inflate(R.layout.vieworderslayout, parent, false));
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyHolder holder, int position) {
        holder.itemname.setText(list.get(position).getOrdername()+"");
        holder.count.setText(list.get(position).getCount()+" "+list.get(position).getSize());
        holder.price.setText("₹ "+list.get(position).getTotal());
        holder.orderedby.setText("Ordered By "+list.get(position).getBuyername());
        Glide.with(context).load(list.get(position).getImg()).centerCrop().into(holder.img);
        Glide.with(context).load(list.get(position).getType()).into(holder.type);
        holder.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, Order_Details.class);
                i.putExtra("name",list.get(position).getOrdername());
                i.putExtra("orderid",list.get(position).getOrderid());
                i.putExtra("count",list.get(position).getCount()+"");
                i.putExtra("size",list.get(position).getSize());
                i.putExtra("address",list.get(position).getAddress());
                i.putExtra("price",list.get(position).getTotal()+"");
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyHolder extends RecyclerView.ViewHolder {
        ImageView img,type;
        TextView itemname, price, count,orderedby;
        Button button;

        public MyHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.viewordersimg);
            itemname = itemView.findViewById(R.id.viewordersitemname);
            price = itemView.findViewById(R.id.viewordersprice);
            count = itemView.findViewById(R.id.vieworderscount);
            button = itemView.findViewById(R.id.viewordersbtn);
            type=itemView.findViewById(R.id.viewordertype);
            orderedby = itemView.findViewById(R.id.orderedby);
        }
    }
}

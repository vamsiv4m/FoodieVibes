package adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.tejuproject.DoneActivity;
import com.example.tejuproject.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.jetbrains.annotations.NotNull;
import java.util.List;
import java.util.UUID;

import models.CartModel2;
import models.OrderModel2;

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

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull @NotNull MyViewHolder holder, int position) {
        String uid= FirebaseAuth.getInstance().getUid();
        holder.cartprice.setText(String.valueOf("₹ "+list.get(position).getTotal()));
        holder.cartItemName.setText(list.get(position).getItemname());
        holder.cartcount.setText(String.valueOf(list.get(position).getCount())+" Items");
        holder.checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String orderid= UUID.randomUUID().toString();
                DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
                OrderModel2 ordersModel=new OrderModel2(list.get(position).getBillname(),orderid,list.get(position).getItemname(),list.get(position).getAddress(),"",list.get(position).getImageurl(),list.get(position).getCount(),list.get(position).getTotal(),list.get(position).getSize());
                assert uid != null;
                reference.child(uid).child("myorders").child(orderid).setValue(ordersModel).addOnCompleteListener(unused -> {
                    Intent i=new Intent(context,DoneActivity.class);
                    i.putExtra("itemname",list.get(position).getItemname());
                    context.startActivity(i);
                });
//                if (isWhatsappInstalled()){
//                    Intent i=new Intent(Intent.ACTION_VIEW, Uri.parse(Uri.parse("http://api.whatsapp.com/send?phone=+919100242315"+
//                            "&text=Hi Iam "+list.get(position).getBillname())+"\nItemName :"+list.get(position).getItemname()+"\nItem Count : "+list.get(position).getCount()+"\nAddress : "+list.get(position).getAddress()));
//                    context.startActivity(i);
//                }
//                else {
//                    Toast.makeText(context,"whatsapp is not installed",Toast.LENGTH_LONG).show();
//
//                }
            }
        });
        Glide.with(context).load(list.get(position).getImageurl()).into(holder.cartItemImage);
    }

    private boolean isWhatsappInstalled() {
            PackageManager packageManager= context.getPackageManager();
            boolean whatsappInstalled;
            try {
                packageManager.getPackageInfo("com.whatsapp",PackageManager.GET_ACTIVITIES);
                whatsappInstalled=true;
            } catch (PackageManager.NameNotFoundException e){
                whatsappInstalled=false;
            }
            return  whatsappInstalled;
        }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{
        ImageView cartItemImage;
        Button checkout;
        TextView cartItemName,cartprice,cartcount;
        @SuppressLint("CutPasteId")
        public MyViewHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            cartcount=itemView.findViewById(R.id.cartcount);
            checkout=itemView.findViewById(R.id.checkoutbtn);
            cartItemImage=itemView.findViewById(R.id.cartdesignimg);
            cartItemName=itemView.findViewById(R.id.cartitemname);
            cartprice=itemView.findViewById(R.id.cartprice);
        }
    }
}

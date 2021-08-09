package owner;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.example.tejuproject.R;

public class Order_Details extends AppCompatActivity {
    TextView name,orderid,size,count,address,price;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        name=findViewById(R.id.detailname);
        orderid=findViewById(R.id.detailorderid);
        size=findViewById(R.id.detailsize);
        count=findViewById(R.id.detailcount);
        address=findViewById(R.id.detailaddress);
        price=findViewById(R.id.detailprice);
        Intent i=getIntent();
        name.setText(i.getStringExtra("name")+"");
        orderid.setText(i.getStringExtra("orderid"));
        size.setText(i.getStringExtra("size"));
        count.setText(i.getStringExtra("count"));
        address.setText(i.getStringExtra("address"));
        price.setText(i.getStringExtra("price"));
    }
}
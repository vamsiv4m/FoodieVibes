package owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tejuproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

public class FoodieVibes extends AppCompatActivity {
    Button submit;
    RadioButton radioButton,radioButton1;
    EditText itemname,size,price;
    String veg="",nonveg="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodie_vibes2);
        RadioGroup radioGroup=findViewById(R.id.radiogroup);
        RadioGroup radioGroup1=findViewById(R.id.radiogroup2);
        itemname=findViewById(R.id.owneritemname);
        price=findViewById(R.id.ownerprice);
        size=findViewById(R.id.foodsize);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int radioid= radioGroup.getCheckedRadioButtonId();
                int radioid2=radioGroup1.getCheckedRadioButtonId();
                radioButton=findViewById(radioid);
                radioButton1=findViewById(radioid2);
                if (radioButton1.getText().equals("veg")){
                    veg="https://firebasestorage.googleapis.com/v0/b/foodie-vibes.appspot.com/o/veg.png?alt=media&token=920c61e8-adac-4a3f-89e1-3344a507ccad";
                    String name=itemname.getText().toString();
                    String itemprice=price.getText().toString();
                    String itemsize=size.getText().toString();
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("items");
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("imageName").setValue(name);
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("imagetype").setValue(veg);
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("price").setValue("₹ "+itemprice);
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("size").setValue(itemsize);
                }
                else {
                    nonveg="https://firebasestorage.googleapis.com/v0/b/foodie-vibes.appspot.com/o/nonveg.png?alt=media&token=5c834f1f-b8a9-4c7f-94a2-e7027e30f11c";
                    String name=itemname.getText().toString();
                    String itemprice=price.getText().toString();
                    String itemsize=size.getText().toString();
                    DatabaseReference reference=FirebaseDatabase.getInstance().getReference("items");
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("imageName").setValue(name);
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("imagetype").setValue(nonveg);
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("price").setValue("₹ "+itemprice);
                    reference.child(radioButton.getText().toString().toLowerCase()+"").child(name.toLowerCase()).child("size").setValue(itemsize);
                }
                Toast.makeText(FoodieVibes.this, radioButton.getText()+"   "+radioButton1.getText(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package owner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.tejuproject.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;


public class FoodieVibes extends AppCompatActivity {
    Button submit, picImage;
    RadioButton radioButton, radioButton1;
    EditText itemname, size, price;
    String veg = "", nonveg = "";
    int SELECT_PICTURE = 200;
    int radioid = 0, radioid2 = 0;
    ImageView imageView;
    Uri selectedImageUri;
    FirebaseAuth auth= FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        setContentView(R.layout.activity_foodie_vibes2);
        RadioGroup radioGroup = findViewById(R.id.radiogroup);
        RadioGroup radioGroup1 = findViewById(R.id.radiogroup2);
        imageView = findViewById(R.id.displayImage);
        itemname = findViewById(R.id.owneritemname);
        picImage = findViewById(R.id.picImage);
        price = findViewById(R.id.ownerprice);
        size = findViewById(R.id.foodsize);
        submit = findViewById(R.id.submit);
        picImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imageChooser();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    radioid = radioGroup.getCheckedRadioButtonId();
                    radioid2 = radioGroup1.getCheckedRadioButtonId();
                    radioButton = findViewById(radioid);
                    radioButton1 = findViewById(radioid2);
                    FirebaseUser user=auth.getCurrentUser();
                    if (user!=null) {
                        StorageReference storareference = FirebaseStorage.getInstance().getReference("uploads");
                        String filename = System.currentTimeMillis() + "";
                        if (radioButton1.getText().equals("veg")) {
                            veg = "https://firebasestorage.googleapis.com/v0/b/foodie-vibes.appspot.com/o/veg.png?alt=media&token=920c61e8-adac-4a3f-89e1-3344a507ccad";
                            if (selectedImageUri != null) {
                                storareference.child(filename + "").putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> result = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = uri.toString();
                                                String name = itemname.getText().toString();
                                                String itemprice = price.getText().toString();
                                                String itemsize = size.getText().toString();
                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items");
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("imageName").setValue(name);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("itemImage").setValue(url);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("imagetype").setValue(veg);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("price").setValue("₹ " + itemprice);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("size").setValue(itemsize);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(FoodieVibes.this, "Something went Wrong..", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                    }
                                });
                            } else {
                                Toast.makeText(FoodieVibes.this, "Select a file", Toast.LENGTH_SHORT).show();
                            }
                        } else if (radioButton1.getText().equals("non veg")) {
                            nonveg = "https://firebasestorage.googleapis.com/v0/b/foodie-vibes.appspot.com/o/nonveg.png?alt=media&token=5c834f1f-b8a9-4c7f-94a2-e7027e30f11c";
                            if (selectedImageUri != null) {

                                storareference.child(filename + "").putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = uri.toString();
                                                String name = itemname.getText().toString();
                                                String itemprice = price.getText().toString();
                                                String itemsize = size.getText().toString();
                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items");
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("imageName").setValue(name);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("itemImage").setValue(url);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("imagetype").setValue(nonveg);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("price").setValue("₹ " + itemprice);
                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("size").setValue(itemsize);
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(FoodieVibes.this, "Something went Wrong..", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        ;
                                    }
                                });

                            } else {
                                Toast.makeText(FoodieVibes.this, "Select a file", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (selectedImageUri != null) {
                                storareference.child(filename + "").putFile(selectedImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                    @Override
                                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                        Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                                        result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                            @Override
                                            public void onSuccess(Uri uri) {
                                                String url = uri.toString();
                                                String name = itemname.getText().toString();
                                                String itemprice = price.getText().toString();
                                                String itemsize = size.getText().toString();
                                                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("items");
//                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("imageName").setValue(name);
//                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("itemImage").setValue(url);
//                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("price").setValue("₹ " + itemprice);
//                                                reference.child(radioButton.getText().toString().toLowerCase() + "").child(name.toLowerCase()).child("size").setValue(itemsize);
                                                Toast.makeText(FoodieVibes.this, reference.getKey()+"", Toast.LENGTH_SHORT).show();
                                            }
                                        }).addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull @NotNull Exception e) {
                                                Toast.makeText(FoodieVibes.this, "Something went Wrong..", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                                        ;
                                    }
                                });

                            } else {
                                Toast.makeText(FoodieVibes.this, "Select a file", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } catch (Exception e) {
                    Toast.makeText(FoodieVibes.this, e+"", Toast.LENGTH_SHORT).show();
                    Log.d("errorbokka",e.getMessage()+"");
                }
            }
        });
    }

    private void imageChooser() {
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE && data != null) {
                // compare the resultCode with the
                // SELECT_PICTURE constant
                selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    imageView.setImageURI(selectedImageUri);
                    imageView.setVisibility(View.VISIBLE);
                }
            }
        }
    }
}
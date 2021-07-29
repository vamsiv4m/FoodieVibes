package com.example.tejuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import owner.FoodieVibes;

public class Login extends AppCompatActivity {
    TextView noaccount,forgotpasswd;
    EditText luname,lpassword;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    private static final String filename="filename";
    private static final String username="username";

    Button login;
    DatabaseReference reference;
    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences=getSharedPreferences(filename, Context.MODE_PRIVATE);
        editor=sharedPreferences.edit();
        setContentView(R.layout.activity_login);
        noaccount=findViewById(R.id.noaccount);
        forgotpasswd=findViewById(R.id.forgot_passwd);
        luname=findViewById(R.id.luname);
        lpassword=findViewById(R.id.lpasswd);

        forgotpasswd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname=luname.getText().toString();
                reference=FirebaseDatabase.getInstance().getReference("users");
                reference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        Intent i=new Intent(getApplicationContext(),ForgotPassword.class);
                        editor.putString(username,uname);
                        editor.apply();
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        startActivity(i);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });

        login=findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uname = luname.getText().toString();
                String pass = lpassword.getText().toString();
                if (uname.equals("owner") && pass.equals("foodievibes")) {
                    Intent i=new Intent(getApplicationContext(), FoodieVibes.class);
                    startActivity(i);
                } else {
                    reference = FirebaseDatabase.getInstance().getReference("users");
                    Query query = reference.orderByChild("username").equalTo(uname);
                    query.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String passwd = snapshot.child(uname).child("password").getValue(String.class);
                                if (pass.equals(passwd)) {
                                    String fullname = snapshot.child(uname).child("username").getValue(String.class);
                                    String email = snapshot.child(uname).child("email").getValue(String.class);
                                    Intent i = new Intent(getApplicationContext(), HomeActivity.class);
                                    startActivity(i);
                                } else {
                                    Toast.makeText(Login.this, "wrong password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(Login.this, "No User", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });
        noaccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent exit=new Intent(Intent.ACTION_MAIN);
        exit.addCategory(Intent.CATEGORY_HOME);
        exit.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(exit);
        finish();
    }
}
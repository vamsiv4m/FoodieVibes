package com.example.tejuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ForgotPassword extends AppCompatActivity {
    EditText newpasswd, confirmpasswd;
    Button proceed;
    SharedPreferences sharedPreferences;
    private static final String filename = "filename";
    private static final String username = "username";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        sharedPreferences = getSharedPreferences(filename, 0);
        newpasswd = findViewById(R.id.newpasswd);
        confirmpasswd = findViewById(R.id.confirmpasswd);
        String uname = sharedPreferences.getString(username, "");
        proceed = findViewById(R.id.proceed);
        proceed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String npass = newpasswd.getText().toString();
                String cpass = confirmpasswd.getText().toString();
                DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");
                Query query=reference.orderByChild("username").equalTo(uname);
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (npass != null && cpass != null) {
                                if (npass.equals(cpass)) {
                                    reference.child(uname).child("password").setValue(cpass);
                                } else {
                                    Toast.makeText(ForgotPassword.this, "Confirm password is not same as New password", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(ForgotPassword.this, "Fields are empty", Toast.LENGTH_SHORT).show();
                            }
                        }
                        else{
                            Toast.makeText(ForgotPassword.this, "No user", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
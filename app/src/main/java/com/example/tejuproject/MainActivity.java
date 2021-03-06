package com.example.tejuproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {
    EditText runame,rpasswd,remail,rphno;
    Button rsignup;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        auth = FirebaseAuth.getInstance();
        setContentView(R.layout.activity_main);
        runame=findViewById(R.id.runame);
        rpasswd=findViewById(R.id.rpasswd);
        remail=findViewById(R.id.remail);
        rphno=findViewById(R.id.rphno);
        rsignup=findViewById(R.id.rsignup);
        rsignup.setOnClickListener(view -> {
            String email=remail.getText().toString();
            String uname=runame.getText().toString();
            String phone=rphno.getText().toString();
            String pass=rpasswd.getText().toString();

            auth.createUserWithEmailAndPassword(email,pass).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    String user=auth.getUid();
                    DatabaseReference reference= FirebaseDatabase.getInstance().getReference("users");
                    assert user != null;
                    reference.child(user).child("username").setValue(uname);
                    reference.child(user).child("userid").setValue(auth.getUid());
                    reference.child(user).child("password").setValue(pass);
                    reference.child(user).child("emailid").setValue(email);
                    reference.child(user).child("phoneno").setValue(phone);
                    Intent i=new Intent(getApplicationContext(),Login.class);
                    startActivity(i);
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(MainActivity.this, "SignUp Failed. Please Try Again. "+e, Toast.LENGTH_SHORT).show();
                    Log.d("mydata",e+"");
                }
            });

        });

    }
}
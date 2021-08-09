package com.example.tejuproject;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;

public class UserSettings extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    Button logout;
    TextView uname, email;
    private final static String filename = "filename";
    private final static String username = "username";

    @SuppressLint("CommitPrefEdits")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        sharedPreferences = getSharedPreferences(filename, 0);
        logout = findViewById(R.id.logout);
        uname = findViewById(R.id.settingusername);
        email = findViewById(R.id.settingsemail);
        String u = sharedPreferences.getString(username, "");
        uname.setText(getIntent().getStringExtra("name"));
        email.setText(u);
        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent i = new Intent(getApplicationContext(), Login.class);
            startActivity(i);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}
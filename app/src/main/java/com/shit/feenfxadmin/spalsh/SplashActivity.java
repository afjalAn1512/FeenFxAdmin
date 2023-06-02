package com.shit.feenfxadmin.spalsh;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shit.feenfxadmin.MainActivity;
import com.shit.feenfxadmin.R;

@SuppressLint("CustomSplashScreen")
public class SplashActivity extends AppCompatActivity {

    String userId;

    private FirebaseAuth mAuth;
    FirebaseFirestore db;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        db = FirebaseFirestore.getInstance();

        new Handler().postDelayed(() -> {
            if (FirebaseAuth.getInstance().getCurrentUser()!=null){
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }else{
                Intent intent = new Intent(SplashActivity.this, LogInActivity.class);
                startActivity(intent);
            }
        },100);

    }
}
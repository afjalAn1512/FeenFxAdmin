package com.shit.feenfxadmin.spalsh;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shit.feenfxadmin.MainActivity;
import com.shit.feenfxadmin.R;

import java.util.Objects;

public class LogInActivity extends AppCompatActivity {

    private CardView login;
    private LinearLayout back;
    private EditText email,pass;
    private TextView signUP,forgot;

    FirebaseAuth mAuth;
    FirebaseFirestore db;

    //Popup Dialog
    Dialog popup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);



        login = findViewById(R.id.login);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);



        //Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //Popup
        popup = new Dialog(this);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String mEmail = email.getText().toString().trim();
                String mPassword = pass.getText().toString().trim();

                if (TextUtils.isEmpty(mEmail)) {

                    email.setError("email address requirement");
                    email.requestFocus();

                } else if (mPassword.isEmpty()) {

                    pass.setError("password requirement");
                    pass.requestFocus();

                }else if (mPassword.length() < 6) {
                    pass.setError("Password should have minimum 6 characters requirement");
                    pass.requestFocus();

                }else {
                    mAuth.signInWithEmailAndPassword(mEmail,mPassword)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        popup.setContentView(R.layout.popup_loadnig);
                                        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                                        popup.show();
                                        popup.setCancelable(false);

                                        FirebaseFirestore.getInstance().collection("userDetails")
                                                .document(Objects.requireNonNull(mAuth.getCurrentUser()).getUid())
                                                .get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                                    @Override
                                                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                                                        if (documentSnapshot.exists()){

                                                            Intent intent = new Intent(LogInActivity.this, MainActivity.class);
                                                            startActivity(intent);
                                                            finish();
                                                            popup.dismiss();

                                                        }else {
                                                            popup.dismiss();
                                                            Toast.makeText(LogInActivity.this, "User doesn't exist", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });

                                    }else {
                                        Toast.makeText(LogInActivity.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                }

            }
        });

    }
}
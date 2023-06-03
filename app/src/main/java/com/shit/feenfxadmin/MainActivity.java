package com.shit.feenfxadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private TextView userName, name,userCount;
    private CardView add_timeSlot,add_Trading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userName = findViewById(R.id.textView2);
        name = findViewById(R.id.textView7);
        userCount = findViewById(R.id.textView4);
        add_timeSlot = findViewById(R.id.add_timeSlot);
        add_Trading = findViewById(R.id.add_Trading);


        FirebaseFirestore.getInstance().collection("userDetails").whereEqualTo("user_id", FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot documentSnapshot : Objects.requireNonNull(value)) {
                            String nameA = Objects.requireNonNull(documentSnapshot.get("username")).toString();
                            String email = Objects.requireNonNull(documentSnapshot.get("email")).toString();
                            userName.setText(nameA);
                            name.setText(email);
                        }
                    }
                });
        FirebaseFirestore.getInstance().collection("userDetails")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        double count = 0.0;
                        for (QueryDocumentSnapshot documentSnapshot : value) {
                            count++;
                        }
                        userCount.setText(String.valueOf(count));
                    }
                });


        add_timeSlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,TimeSlotActivity.class));
                finish();
            }
        });

    }
}
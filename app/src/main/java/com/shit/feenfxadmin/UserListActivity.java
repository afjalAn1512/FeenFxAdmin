package com.shit.feenfxadmin;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.shit.feenfxadmin.adapter.DepositAdapter;
import com.shit.feenfxadmin.adapter.UserAdapter;
import com.shit.feenfxadmin.model.DepositStatusModel;
import com.shit.feenfxadmin.model.UserModel;

public class UserListActivity extends AppCompatActivity {

    private LinearLayout back;

    Dialog popup;

    private RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseFirestore db;
    private CollectionReference item;
    private FirebaseAuth mAuth;
    private UserAdapter buyerPostAdapter;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_list);

        popup = new Dialog(this);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        back = findViewById(R.id.linearLayout2);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new PendingDecoration(topPadding, bottomPadding));



        FirebaseFirestore.getInstance().collection("userDetails")
                .whereEqualTo("user_id",FirebaseAuth.getInstance().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot documentSnapshot : value){

                            String nameA = documentSnapshot.getString("fullName");
                            String emailA = documentSnapshot.getString("email");
//
//                            full_name.setText(nameA);
//                            email.setText(emailA);

                        }

                    }
                });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserListActivity.this,MainActivity.class));
                finish();
            }
        });


        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();;

        item = db.collection("userDetails");

        Query query = item.orderBy("status", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<UserModel> options = new FirestorePagingOptions.Builder<UserModel>()
                .setQuery(query, config, UserModel.class)
                .build();

        buyerPostAdapter = new UserAdapter(options, swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(UserListActivity.this));
        recyclerView.setAdapter(buyerPostAdapter);
        buyerPostAdapter.startListening();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buyerPostAdapter.refresh();
            }
        });

        buyerPostAdapter.setOnItemClickListener(new UserAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {

            }
        });

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(UserListActivity.this,MainActivity.class));
        finish();
    }
}
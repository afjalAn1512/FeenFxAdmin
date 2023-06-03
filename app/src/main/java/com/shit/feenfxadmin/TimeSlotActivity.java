package com.shit.feenfxadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shit.feenfxadmin.adapter.SlotAdapter;
import com.shit.feenfxadmin.model.SlotModel;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class TimeSlotActivity extends AppCompatActivity {

    private LinearLayout back;

    Dialog popup;

    private RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseFirestore db;
    private CollectionReference item;
    private FirebaseAuth mAuth;
    private SlotAdapter buyerPostAdapter;

    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_slot);

        back = findViewById(R.id.linearLayout2);

        popup = new Dialog(this);

        recyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new PendingDecoration(topPadding, bottomPadding));


        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();;

        item = db.collection("TimeSlot");

        Query query = item.orderBy("timestamp", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<SlotModel> options = new FirestorePagingOptions.Builder<SlotModel>()
                .setQuery(query, config, SlotModel.class)
                .build();

        buyerPostAdapter = new SlotAdapter(options, swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(TimeSlotActivity.this));
        recyclerView.setAdapter(buyerPostAdapter);
        buyerPostAdapter.startListening();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buyerPostAdapter.refresh();
            }
        });

        buyerPostAdapter.setOnItemClickListener(new SlotAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {

            }
        });


    }

    public void floatActionButton(View view) {

        popup.setContentView(R.layout.slot_popup);
        popup.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        EditText slotTime = popup.findViewById(R.id.editTextTextPersonName);
        Button add = popup.findViewById(R.id.button);
        popup.show();
        popup.setCancelable(false);



        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String slot = slotTime.getText().toString().trim();
                long startTime = System.currentTimeMillis();

                long milliseconds = startTime + TimeUnit.MINUTES.toMillis(Long.parseLong(slotTime.getText().toString()));


                if (TextUtils.isEmpty(slot)){
                    slotTime.setError("Enter Minute");
                    slotTime.requestFocus();
                }else if (slotTime.equals("0")){
                    slotTime.setError("Enter Minute");
                    slotTime.requestFocus();
                }else {

                    final SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(TimeSlotActivity.this, SweetAlertDialog.PROGRESS_TYPE);
                    sweetAlertDialog.setTitleText("Please Wait.........");
                    sweetAlertDialog.setCancelable(true);
                    sweetAlertDialog.show();
                    UUID uuid = UUID.randomUUID();
                    String id = String.valueOf(uuid);


                    Map<String, Object> map = new HashMap<>();
                    map.put("slot_id", id);
                    map.put("current_slot",startTime);
                    map.put("last_slot",milliseconds);
                    map.put("slot_time",slot);
                    map.put("timestamp", FieldValue.serverTimestamp());
                    map.put("date", getCalculatedDate("dd.MM.yyyy, HH:mm:ss", 0));

                    FirebaseFirestore.getInstance().collection("TimeSlot")
                            .document(id)
                            .set(map)
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void unused) {

                                    FirebaseFirestore.getInstance().collection("Timer")
                                            .document("time")
                                            .set(map)
                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if (task.isSuccessful()){
                                                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                                        sweetAlertDialog.setTitle("Success");
                                                        sweetAlertDialog.setContentText("Claim Success");
                                                        sweetAlertDialog.setConfirmButton("Dismiss", new SweetAlertDialog.OnSweetClickListener() {
                                                            @Override
                                                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                                                sweetAlertDialog.dismissWithAnimation();
                                                                startActivity(new Intent(TimeSlotActivity.this,TimeSlotActivity.class));
                                                            }
                                                        });

                                                    }
                                                }
                                            });
                                }
                            });

                }

            }
        });

    }

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(TimeSlotActivity.this,MainActivity.class));
        finish();
    }
}
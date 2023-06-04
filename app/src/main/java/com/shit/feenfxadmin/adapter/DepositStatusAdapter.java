package com.shit.feenfxadmin.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shit.feenfxadmin.R;
import com.shit.feenfxadmin.model.DepositStatusModel;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class DepositStatusAdapter extends FirestorePagingAdapter<DepositStatusModel,DepositStatusAdapter.ViewHolder> {


    private OnItemClickListener listener;
    private Context mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String userID;
    FirebaseAuth mAuth;
    private FirebaseFirestore db;

    Dialog popup;

    long startTime,currentTime,newFeatureTime;
    long milliseconds;

    String Previous, PreviousCommission;
    String Referral;


    public DepositStatusAdapter(@NonNull FirestorePagingOptions<DepositStatusModel> options, SwipeRefreshLayout mSwipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull DepositStatusModel model) {

        final String post_id = getItem(position).getId();
        userID = model.getUser_id();

        holder.status.setText("Status: " + model.getStatus());
        holder.amount.setText("Amount: " + model.getAmount());
        holder.senderTRX.setText("Sender TRX: " + model.getSender_trx());
        holder.username.setText("Username : "+model.getUsername());

        holder.senderTRX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(mContext.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", model.getSender_trx());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "TRX copied: " + model.getSender_trx(), Toast.LENGTH_SHORT).show();

            }
        });



        holder.accept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                FirebaseFirestore.getInstance().collection("Deposit").document(model.getDeposit_id()).get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                Map<String, Object> status = new HashMap<>();
                                status.put("status", "Approved");

                                FirebaseFirestore.getInstance().collection("Deposit").document(model.getDeposit_id()).update(status);
                                refresh();
                            }
                        });

                FirebaseFirestore.getInstance().collection("userDetails").document(model.getUsername()).get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                //Referral = documentSnapshot.getString("referral");
                                Previous = documentSnapshot.getString("deposit_balance");

                                // PreviousCommission = documentSnapshot.getLong("commission");

                                Long Total = Long.parseLong(model.getAmount()) + Long.parseLong(Previous);

//                                double y;
//                                int value2;
//                                Long x = Long.valueOf(model.getAmount());
//
//                                if (Previous!=0){
//                                    y = (Previous * 0.05) + PreviousCommission;
//                                    value2 = (int) Math.round(y);
//                                }else{
//                                    y = (x*.05)+PreviousCommission;
//                                    value2 = (int) Math.round(y);
//                                }


                                Map<String, Object> status = new HashMap<>();
                                status.put("deposit_balance", String.valueOf(Total));
//                                status.put("commission", value2);

                                FirebaseFirestore.getInstance().collection("userDetails").document(model.getUsername()).update(status);

//                                try {
//                                    //Referral...................
//
//                                    db.collection("userDetails").document(Referral).get()
//                                            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                                                    Previous = documentSnapshot.getLong("total");
//
//
//                                                    double x = (Previous * .05) + Previous;
//
//
//                                                    int value1 = (int) Math.round(x);
//
//
//                                                    Map<String, Object> status = new HashMap<>();
//                                                    status.put("total", value1);
//
//                                                    db.collection("userDetails").document(Referral).update(status);
//                                                }
//                                            });
//                                } catch (Exception e) {
//                                    Log.d("PendingAdapterBuy", "onSuccess: " + e);
//                                }

                                refresh();
                            }
                        });

            }
        });


    }

    @Override
    protected void onLoadingStateChanged(@NonNull LoadingState state) {
        super.onLoadingStateChanged(state);
        switch (state) {

            case LOADING_INITIAL:
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d("Paging Log", "Loading Initial data");
                break;
            case LOADING_MORE:
                mSwipeRefreshLayout.setRefreshing(true);
                Log.d("Paging Log", "Loading next page");
                break;
            case FINISHED:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "All data loaded");
                break;
            case LOADED:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "Total data loaded "+getItemCount());
                break;
            case ERROR:
                mSwipeRefreshLayout.setRefreshing(false);
                Log.d("Paging Log", "Error loading data");
                break;
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pending_deposit_layout,
                parent, false);

        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView status, amount, senderTRX, username, sentTo;
        CardView accept;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
            senderTRX = itemView.findViewById(R.id.sender_trx);
            accept = itemView.findViewById(R.id.accept);
            username = itemView.findViewById(R.id.username);

            mContext = itemView.getContext();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });

        }
    }

    public interface OnItemClickListener {
        void onItemClick(DocumentSnapshot documentSnapshot);
    }

    public void setOnItemClickListener(DepositStatusAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }

}

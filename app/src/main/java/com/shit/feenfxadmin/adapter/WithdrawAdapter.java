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
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shit.feenfxadmin.R;
import com.shit.feenfxadmin.model.WithdrawModel;

public class WithdrawAdapter extends FirestorePagingAdapter<WithdrawModel,WithdrawAdapter.ViewHolder> {


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


    public WithdrawAdapter(@NonNull FirestorePagingOptions<WithdrawModel> options, SwipeRefreshLayout mSwipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull WithdrawModel model) {

        final String post_id = getItem(position).getId();
        userID = model.getUser_id();

        holder.status.setText("Date: " + model.getTimestamp());
        holder.amount.setText("Amount: " + model.getAmount());
        holder.senderTRX.setText("TRX: " + model.getSender_trx());
        holder.username.setText("Username : "+model.getUsername());
        holder.sent_date.setText("Date : "+model.getEmail());

        holder.senderTRX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ClipboardManager clipboard = (ClipboardManager) mContext.getSystemService(mContext.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText("", model.getSender_trx());
                clipboard.setPrimaryClip(clip);
                Toast.makeText(mContext, "TRX copied: " + model.getSender_trx(), Toast.LENGTH_SHORT).show();

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_report_layout,
                parent, false);

        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView status, amount, senderTRX, username, sent_date;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
            senderTRX = itemView.findViewById(R.id.sender);
            username = itemView.findViewById(R.id.sent_to);
            sent_date = itemView.findViewById(R.id.sent_date);

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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

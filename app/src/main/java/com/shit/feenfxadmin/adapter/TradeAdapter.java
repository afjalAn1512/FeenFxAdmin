package com.shit.feenfxadmin.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.firebase.ui.firestore.paging.FirestorePagingAdapter;
import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.firebase.ui.firestore.paging.LoadingState;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.shit.feenfxadmin.R;
import com.shit.feenfxadmin.model.TradeModel;

public class TradeAdapter extends FirestorePagingAdapter<TradeModel,TradeAdapter.ViewHolder> {

    private OnItemClickListener listener;
    private Context mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Dialog popup;

    long startTime,currentTime,newFeatureTime;
    long milliseconds;

    public TradeAdapter(@NonNull FirestorePagingOptions<TradeModel> options, SwipeRefreshLayout mSwipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull TradeModel model) {

        holder.sl.setText(String.valueOf(holder.getAdapterPosition() + 1));

        holder.username.setText("Username : "+model.getUsername());
        holder.currency.setText(model.getCurrency()+" -"+model.getCurrency_price());
        holder.updown.setText(model.getCurrency_percentage()+"   "+model.getCurrency_high());

        FirebaseFirestore.getInstance().collection("TimeSlot")
                        .document(model.getSlot_id())
                                .get()
                                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                                            @Override
                                            public void onSuccess(DocumentSnapshot documentSnapshot) {

                                                if (documentSnapshot.exists()){
                                                    String curr = String.valueOf(documentSnapshot.getLong("current_slot"));
                                                    String last = String.valueOf(documentSnapshot.getLong("last_slot"));
                                                    String time = String.valueOf(documentSnapshot.getString("slot_time"));

                                                    holder.currentSlot.setText("Current Slot : #"+curr);
                                                    holder.lastSLot.setText("Last Slot : #"+last);
                                                    holder.timeSlot.setText("Time Slot : "+time);

                                                }

                                            }
                                        });


        holder.date.setText(String.valueOf(model.getTimestamp()));

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trade_layout,
                parent, false);

        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView sl,username,currency,updown,currentSlot,lastSLot,date,timeSlot;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            timeSlot = itemView.findViewById(R.id.timeSlot);
            date = itemView.findViewById(R.id.date);
            lastSLot = itemView.findViewById(R.id.lastSLot);
            currentSlot = itemView.findViewById(R.id.currentSlot);
            updown = itemView.findViewById(R.id.updown);
            currency = itemView.findViewById(R.id.currency);
            username = itemView.findViewById(R.id.username);
            sl = itemView.findViewById(R.id.sl);


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

    public void setOnItemClickListener(TradeAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

package com.shit.feenfxadmin.adapter;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
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
import com.google.firebase.auth.internal.RecaptchaActivity;
import com.google.firebase.firestore.DocumentSnapshot;
import com.shit.feenfxadmin.R;
import com.shit.feenfxadmin.model.SlotModel;

import java.util.concurrent.TimeUnit;

public class SlotAdapter extends FirestorePagingAdapter<SlotModel, SlotAdapter.FeedHolder> {

    private OnItemClickListener listener;
    private Context mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    Dialog popup;

    long startTime,currentTime,newFeatureTime;
    long milliseconds;


    public SlotAdapter(@NonNull FirestorePagingOptions<SlotModel> options, SwipeRefreshLayout mSwipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull FeedHolder holder, int position, @NonNull SlotModel model) {

        holder.status.setText("Date : "+model.getTimestamp());
        holder.amount.setText("Current Slot : #"+model.getCurrent_slot());
        holder.sender.setText("Last Slot : #"+model.getLast_slot());

      //  holder.sentTo.setText("Timer: "+model.getUsername());



        startTime = System.currentTimeMillis();




        currentTime = System.currentTimeMillis();


        newFeatureTime = model.getLast_slot();

        if (currentTime >= newFeatureTime) {

            Log.i("juyti","-------------slot off----------");

            holder.sentTo.setText("Time slot is over");

        }else {

            startTime = System.currentTimeMillis();

            milliseconds = model.getLast_slot();

            final CountDownTimer mCountDownTimer;

            mCountDownTimer = new CountDownTimer(milliseconds, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                    startTime = startTime - 1;
                    Long serverUptimeSeconds =
                            (millisUntilFinished - startTime) / 1000;


                    String minutesLeft = String.format("%d", ((serverUptimeSeconds % 86400) % 3600) / 60);
                    //txtViewMinutes.setText(minutesLeft);
                    Log.d("minutesLeft", minutesLeft);

                    // minute.setText("0"+minutesLeft);

                    String secondsLeft = String.format("%d", ((serverUptimeSeconds % 86400) % 3600) % 60);
                    //txtViewSecond.setText(secondsLeft);
                    Log.d("secondsLeft", secondsLeft);

                    //second.setText(secondsLeft);

                    holder.sentTo.setText("Timer : " +"0"+minutesLeft+" : "+secondsLeft);

                }

                @Override
                public void onFinish() {

                }
            }.start();

        }

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
    public FeedHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.withdraw_layout,
                parent, false);

        return new FeedHolder(view);
    }

    public class FeedHolder extends RecyclerView.ViewHolder{

        TextView status, amount, sender, sent_email, sentTo;

        public FeedHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
            sender = itemView.findViewById(R.id.sender);
            sentTo = itemView.findViewById(R.id.sent_to);

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

    public void setOnItemClickListener(SlotAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

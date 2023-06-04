package com.shit.feenfxadmin.adapter;

import android.annotation.SuppressLint;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.shit.feenfxadmin.R;
import com.shit.feenfxadmin.model.UserModel;

public class UserAdapter extends FirestorePagingAdapter<UserModel,UserAdapter.ViewHolder> {


    private OnItemClickListener listener;
    private Context mContext;
    SwipeRefreshLayout mSwipeRefreshLayout;

    private String userID;

    public UserAdapter(@NonNull FirestorePagingOptions<UserModel> options, SwipeRefreshLayout mSwipeRefreshLayout) {
        super(options);
        this.mSwipeRefreshLayout = mSwipeRefreshLayout;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull UserModel model) {

        holder.sl.setText(String.valueOf(holder.getAdapterPosition() + 1));

        holder.status.setText("Email : "+model.getEmail());
        holder.amount.setText("Full Name : "+model.getFullName());
        holder.commission.setText("User Name  : "+model.getUsername());
        holder.form.setText("Phone : "+model.getPhone());
        holder.to.setText("Location : "+model.getLocation());

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.generation_layout,
                parent, false);

        return new ViewHolder(view);
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView status,amount,commission,form,to,sl;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            status = itemView.findViewById(R.id.status);
            amount = itemView.findViewById(R.id.amount);
            commission = itemView.findViewById(R.id.commission);
            form = itemView.findViewById(R.id.form);
            to = itemView.findViewById(R.id.to);
            sl = itemView.findViewById(R.id.textView11);

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

    public void setOnItemClickListener(UserAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}

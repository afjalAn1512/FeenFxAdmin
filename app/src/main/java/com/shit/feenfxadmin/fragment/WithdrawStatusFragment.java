package com.shit.feenfxadmin.fragment;

import android.app.Dialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.paging.PagedList;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.firestore.paging.FirestorePagingOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.shit.feenfxadmin.PendingDecoration;
import com.shit.feenfxadmin.R;
import com.shit.feenfxadmin.adapter.DepositStatusAdapter;
import com.shit.feenfxadmin.adapter.WithdrawStatusAdapter;
import com.shit.feenfxadmin.model.DepositStatusModel;
import com.shit.feenfxadmin.model.WithdrawModel;


public class WithdrawStatusFragment extends Fragment {


    Dialog popup;

    private RecyclerView recyclerView;

    SwipeRefreshLayout swipeRefreshLayout;

    private FirebaseFirestore db;
    private CollectionReference item;
    private FirebaseAuth mAuth;
    private WithdrawStatusAdapter buyerPostAdapter;

    String userID;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_withdraw_status, container, false);

        popup = new Dialog(requireContext());

        recyclerView = view.findViewById(R.id.recyclerView);
        swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
        int topPadding = getResources().getDimensionPixelSize(R.dimen.topPadding);
        int bottomPadding = getResources().getDimensionPixelSize(R.dimen.bottomPadding);
        int sidePadding = getResources().getDimensionPixelSize(R.dimen.sidePadding);
        recyclerView.addItemDecoration(new PendingDecoration(topPadding, bottomPadding));


        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();
        db = FirebaseFirestore.getInstance();;

        item = db.collection("Withdraw");

        Query query = item.whereEqualTo("status","Pending").orderBy("timestamp", Query.Direction.DESCENDING);

        PagedList.Config config = new PagedList.Config.Builder()
                .setInitialLoadSizeHint(10)
                .setPageSize(15)
                .build();

        FirestorePagingOptions<WithdrawModel> options = new FirestorePagingOptions.Builder<WithdrawModel>()
                .setQuery(query, config, WithdrawModel.class)
                .build();

        buyerPostAdapter = new WithdrawStatusAdapter(options, swipeRefreshLayout);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireActivity()));
        recyclerView.setAdapter(buyerPostAdapter);
        buyerPostAdapter.startListening();


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                buyerPostAdapter.refresh();
            }
        });

        buyerPostAdapter.setOnItemClickListener(new WithdrawStatusAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(DocumentSnapshot documentSnapshot) {

            }
        });

        return view;
    }
}
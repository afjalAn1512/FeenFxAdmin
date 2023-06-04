package com.shit.feenfxadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.shit.feenfxadmin.fragment.BuyFragment;
import com.shit.feenfxadmin.fragment.DepositStatusFragment;
import com.shit.feenfxadmin.fragment.SellFragment;
import com.shit.feenfxadmin.fragment.WithdrawStatusFragment;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PaymentRequestActivity extends AppCompatActivity {

    private PaymentRequestActivity activity;
    private ViewPagerAdapter adapterNew;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private LinearLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_request);

        viewPager2 = findViewById(R.id.viewPager2);
        tabLayout = findViewById(R.id.tabLayout);
        back = findViewById(R.id.linearLayout2);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PaymentRequestActivity.this,MainActivity.class));
                finish();
            }
        });

        activity = this;

        initView();


    }

    private void initView() {

        setupViewPager(viewPager2);
//        binding.tabLayout.setupWithViewPager(binding.viewPager);


        new TabLayoutMediator(tabLayout, viewPager2,
                (tab, position) -> {
                    tab.setText(adapterNew.mFragmentTitleList.get(position));
//                tab.setCustomView(R.layout.custom_tab);
                }).attach();

        for (int i = 0; i < tabLayout.getTabCount(); i++) {

            TextView tv = (TextView) LayoutInflater.from(PaymentRequestActivity.this)
                    .inflate(R.layout.custom_tab, null);

            tabLayout.getTabAt(i).setCustomView(tv);
        }
    }

    private void setupViewPager(ViewPager2 viewPager) {
        adapterNew = new ViewPagerAdapter(PaymentRequestActivity.this.getSupportFragmentManager(),
                activity.getLifecycle());
        adapterNew.addFragment(new DepositStatusFragment(), "Deposit Status");
        adapterNew.addFragment(new WithdrawStatusFragment(), "Withdraw Status");
        viewPager.setAdapter(adapterNew);
        viewPager.setOffscreenPageLimit(1);

    }

    static class ViewPagerAdapter extends FragmentStateAdapter {

        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @NonNull
        @NotNull
        @Override
        public Fragment createFragment(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getItemCount() {
            return mFragmentList.size();
        }

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(PaymentRequestActivity.this,MainActivity.class));
        finish();
    }

}
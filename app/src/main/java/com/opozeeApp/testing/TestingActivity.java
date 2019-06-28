package com.opozeeApp.testing;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.opozeeApp.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import ro.andreidobrescu.emojilike.ActivityWithEmoji;

public class TestingActivity extends ActivityWithEmoji {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testing);
        ButterKnife.bind(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapterSample(this));
    }
}

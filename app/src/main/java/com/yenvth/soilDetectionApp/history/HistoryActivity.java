package com.yenvth.soilDetectionApp.history;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.HistoryModel;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener, HistoryView, HistoryAdapter.OnHistoryItemClickListener {

    @BindView(R.id.recycler_view_history)
    protected RecyclerView recycler_view_history;
    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    private HistoryPresenterImpl<HistoryView> presenter;
    private HistoryAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        presenter = new HistoryPresenterImpl<>(this, this);
        init();
        action();
    }

    private void init() {
        tvToolbar.setText("Lịch sử tìm kiếm");
        presenter.getListHistories();
    }

    private void action() {
        btnBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
        }
    }

    @Override
    public void onGetListHistorySuccess(ArrayList<HistoryModel> historyModels) {
        mAdapter = new HistoryAdapter(this, historyModels, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view_history.setLayoutManager(layoutManager);
        recycler_view_history.setItemAnimator(new DefaultItemAnimator());
        recycler_view_history.setAdapter(mAdapter);
    }

    @Override
    public void onUpdateHistorySuccess() {

    }

    @Override
    public void onDeleteHistorySuccess() {

    }

    @Override
    public void onHistoryClickListener(HistoryModel historyModel) {

    }
}

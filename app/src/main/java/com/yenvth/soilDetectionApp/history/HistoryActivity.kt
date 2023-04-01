package com.yenvth.soilDetectionApp.history;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.HistoryModel;
import com.yenvth.soilDetectionApp.soilDetail.SoilDetailActivity;
import com.yenvth.soilDetectionApp.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class HistoryActivity extends AppCompatActivity implements View.OnClickListener, HistoryView, HistoryAdapter.OnHistoryItemClickListener {

    @BindView(R.id.recycler_view_history)
    protected RecyclerView recycler_view_history;
    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.btnDeleteAll)
    protected LinearLayout btnDeleteAll;

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
        btnDeleteAll.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnDeleteAll:
                final SweetAlertDialog dialog = new SweetAlertDialog(HistoryActivity.this, SweetAlertDialog.WARNING_TYPE);
                dialog.setContentText("Bạn chắc chắn xóa toàn bộ lịch sử chứ?")
                        .setConfirmText("Đồng ý")
                        .setCancelText("Hủy")
                        .showCancelButton(true)
                        .setConfirmClickListener(sweetAlertDialog -> {
                            dialog.cancel();
                            presenter.deleteAllHistories();
                        })
                        .setCancelClickListener(sweetAlertDialog -> dialog.cancel())
                        .show();
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
        if (mAdapter.getItemCount() - 1 >= 0) {
            recycler_view_history.smoothScrollToPosition(0);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onDeleteHistorySuccess() {
        CommonUtils.showMessage(HistoryActivity.this, "Xóa thành công");
        presenter.getListHistories();
    }

    @Override
    public void onDeleteAllHistoriesSuccess() {
        CommonUtils.showMessage(HistoryActivity.this, "Xóa toàn bộ thành công");
        presenter.getListHistories();
    }

    @Override
    public void onHistoryClick(HistoryModel historyModel) {
        Intent intent = new Intent(HistoryActivity.this, SoilDetailActivity.class);
        intent.putExtra("soil_id", historyModel.getSoilId());
        startActivity(intent);
    }

    @Override
    public void onItemDelete(int historiesId) {
        presenter.deleteHistory(historiesId);
    }
}

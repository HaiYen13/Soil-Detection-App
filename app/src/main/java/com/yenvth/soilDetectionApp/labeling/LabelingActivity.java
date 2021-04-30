package com.yenvth.soilDetectionApp.labeling;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.makeramen.roundedimageview.RoundedImageView;
import com.yenvth.soilDetectionApp.R;
import com.yenvth.soilDetectionApp.models.LabelingModel;
import com.yenvth.soilDetectionApp.utils.CommonUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class LabelingActivity extends AppCompatActivity implements View.OnClickListener, LabelingView, LabelingAdapter.OnLabelItemClickListener {

    @BindView(R.id.recycler_view_label)
    protected RecyclerView recycler_view_label;
    @BindView(R.id.btnBack)
    protected ImageView btnBack;
    @BindView(R.id.tvToolbar)
    protected TextView tvToolbar;
    @BindView(R.id.btnAdd)
    protected LinearLayout btnAdd;
    private ConstraintLayout imageLayout;
    private LinearLayout btnAddImage;
    private RoundedImageView imageView;
    private Uri uri;
    private ImageView btnDel;
    private LabelingPresenterImpl<LabelingView> presenter;
    private LabelingAdapter mAdapter;
    private static final int PICK_IMAGE = 1;
    private String LABEL_NAME;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_labeling);
        getSupportActionBar().hide();
        ButterKnife.bind(this);
        presenter = new LabelingPresenterImpl<>(this, this);
        init();
        action();
    }

    private void init() {
        tvToolbar.setText("Nhãn dán của tôi");
        presenter.getListLabels();
    }

    private void action() {
        btnBack.setOnClickListener(this);
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                onBackPressed();
                break;
            case R.id.btnAdd:
                final BottomSheetDialog dialog = new BottomSheetDialog(this);
                dialog.setContentView(R.layout.dialog_add_label);
                btnAddImage = dialog.findViewById(R.id.btnAddImage);
                imageLayout = dialog.findViewById(R.id.imageLayout);
                imageView = dialog.findViewById(R.id.image);
                btnDel = dialog.findViewById(R.id.btnDel);
                EditText edName = dialog.findViewById(R.id.edName);

                TextView btnContinue = dialog.findViewById(R.id.btnContinue);

                btnAddImage.setOnClickListener(v -> {
                    Intent intent = new Intent();
                    intent.setType("image/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Chọn ảnh"), PICK_IMAGE);
                });

                btnDel.setOnClickListener(v -> {
                    uri = null;
                    imageView.setImageURI(null);
                    btnAddImage.setVisibility(View.VISIBLE);
                    imageLayout.setVisibility(View.GONE);
                });

                btnContinue.setOnClickListener(v -> {
                    if (TextUtils.isEmpty(edName.getText().toString().trim())) {
//                        CommonUtils.showError((Activity) dialog.getContext(), "Vui lòng nhập tên nhãn");
                        return;
                    }
                    if (uri == null) {
//                        CommonUtils.showError((Activity) dialog.getContext(), "Vui lòng chọn ảnh đất");
                        return;
                    }
                    LABEL_NAME = edName.getText().toString().trim();
                    presenter.addImageLabelFirebase(uri);
                    dialog.cancel();
                });
                dialog.show();
                break;
        }

    }

    @Override
    public void onGetListLabelSuccess(ArrayList<LabelingModel> list) {
        mAdapter = new LabelingAdapter(this, list, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recycler_view_label.setLayoutManager(layoutManager);
        recycler_view_label.setItemAnimator(new DefaultItemAnimator());
        recycler_view_label.setAdapter(mAdapter);
        if (mAdapter.getItemCount() - 1 >= 0) {
            recycler_view_label.smoothScrollToPosition(mAdapter.getItemCount() - 1);
        }
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void addImageFirebaseSuccess(String url) {
        presenter.addLabel(LABEL_NAME, url);
    }


    @Override
    public void onAddLabelSuccess() {
        CommonUtils.showMessage(LabelingActivity.this, "Tải thành công");
        presenter.getListLabels();
    }

    @Override
    public void onDeleteLabelSuccess() {
        CommonUtils.showMessage(LabelingActivity.this, "Xóa thành công");
        presenter.getListLabels();
    }

    @Override
    public void onLabelClickListener(LabelingModel labelingModel) {

    }

    @Override
    public void onDeleteLabel(String labelId) {
        final SweetAlertDialog dialog = new SweetAlertDialog(LabelingActivity.this, SweetAlertDialog.WARNING_TYPE);
        dialog.setContentText("Bạn chắc chắn xóa nhãn dán này chứ?")
                .setConfirmText("Đồng ý")
                .setCancelText("Hủy")
                .showCancelButton(true)
                .setConfirmClickListener(sweetAlertDialog -> {
                    dialog.cancel();
                    presenter.deleteLabel(labelId);
                })
                .setCancelClickListener(sweetAlertDialog -> dialog.cancel())
                .show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK) {
            uri = data.getData();
            imageView.setImageURI(uri);
            btnAddImage.setVisibility(View.GONE);
            imageLayout.setVisibility(View.VISIBLE);
        }
    }
}

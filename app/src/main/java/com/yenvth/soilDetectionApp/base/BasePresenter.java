package com.yenvth.soilDetectionApp.base;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.yenvth.soilDetectionApp.R;

public class BasePresenter {
    private Dialog dialog;

    protected void showKeyboard(Context mContext) {
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    protected void hideKeyboard(Context mContext) {
        View view = new View(mContext);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    protected void showLoading(Context mContext) {
        if (dialog == null) {
            dialog = new Dialog(mContext);
        }
        dialog.setContentView(R.layout.dialog_loading);
        dialog.findViewById(R.id.progress_bar).setVisibility(View.VISIBLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

    protected void hideLoading() {
        if (dialog != null) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    dialog.dismiss();
                }
            }, 1000);
        }
    }
}

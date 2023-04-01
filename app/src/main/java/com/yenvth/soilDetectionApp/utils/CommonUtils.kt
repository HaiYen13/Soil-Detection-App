package com.yenvth.soilDetectionApp.utils;

import android.app.Activity;
import android.content.Context;

import com.google.android.material.snackbar.Snackbar;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CommonUtils {
    public static void showMessage(Context mContext, String message) {
        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.SUCCESS_TYPE);
        pDialog.setTitleText(message)
                .hideConfirmButton()
                .show();
    }

    public static void showError(Activity activity, String message) {
//        SweetAlertDialog pDialog = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE);
//        pDialog.setTitleText(message)
//                .hideConfirmButton()
//                .show();
        Snackbar.make(activity.findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT).show();
    }
}
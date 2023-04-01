package com.yenvth.soilDetectionApp.utils

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Handler
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.google.android.material.snackbar.Snackbar
import com.yenvth.soilDetectionApp.R

object CommonUtils {
    fun showLoadingDialog(context: Context?): ProgressDialog {
        val progressDialog = ProgressDialog(context)
        progressDialog.show()

        Handler().postDelayed({
            progressDialog.dismiss()
        }, Constant.DIALOG_TIMEOUT)
        if (progressDialog.window != null) {
            progressDialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        progressDialog.setContentView(R.layout.progress_dialog)
        progressDialog.isIndeterminate = true
        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)
        return progressDialog
    }


    fun showSnackBar(activity: Activity?, message: String, isSuccess: Boolean = true) {
        if (activity == null) return
        val snack = Snackbar.make(
            activity.findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        )
        val view = snack.view
        view.setBackgroundResource(if (isSuccess) R.drawable.bg_success_message else R.drawable.bg_fail_message)
        val tv = view.findViewById<TextView>(R.id.snackbar_text)
        tv.setTextColor(
            ContextCompat.getColor(
                activity,
                if (isSuccess) R.color.color_text_success else R.color.color_text_failed
            )
        )
        val typeface = ResourcesCompat.getFont(activity, R.font.prompt_medium)
        tv.typeface = typeface
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }

    fun showSnackBar(viewDialog: View?, message: String, isSuccess: Boolean = true) {
        if (viewDialog == null) return
        val snack = Snackbar.make(viewDialog, message, Snackbar.LENGTH_SHORT)
        val view = snack.view
        view.setBackgroundResource(if (isSuccess) R.drawable.bg_success_message else R.drawable.bg_fail_message)
        val tv = view.findViewById<TextView>(R.id.snackbar_text)
        tv.setTextColor(
            ContextCompat.getColor(
                viewDialog.context,
                if (isSuccess) R.color.color_text_success else R.color.color_text_failed
            )
        )
        val typeface = ResourcesCompat.getFont(viewDialog.context, R.font.prompt_medium)
        tv.typeface = typeface
        val params = view.layoutParams as FrameLayout.LayoutParams
        params.gravity = Gravity.TOP
        view.layoutParams = params
        snack.show()
    }
}
package com.yenvth.soilDetectionApp.utils

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

object ImageUtils {
    fun setImage(context: Context?, uri: Uri?, imgView: ImageView) {
        if (context == null || uri == null) return
        Glide.with(context)
            .load(uri)
            .fitCenter()
            .into(imgView)
    }

    fun setImage(context: Context?, uri: Uri?, imgView: ImageView, progressBar: ProgressBar?) {
        if (context == null || uri == null) return
        Glide.with(context)
            .load(uri)
            .fitCenter()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imgView)
    }

    fun setImage(
        context: Context?,
        uri: Uri?,
        imgView: ImageView,
        progressBar: ProgressBar?,
        havePlaceHolder: Boolean
    ) {
        if (context == null || uri == null) return
        Glide.with(context)
            .load(uri)
            .fitCenter()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imgView)
    }

    fun setImage(context: Context?, bitmap: Bitmap?, imgView: ImageView) {
        if (context == null || bitmap == null) return
        Glide.with(context)
            .load(bitmap)
            .fitCenter()
            .into(imgView)
    }

    fun setImage(
        context: Context?,
        bitmap: Bitmap?,
        imgView: ImageView,
        progressBar: ProgressBar?
    ) {
        if (context == null || bitmap == null) return
        Glide.with(context)
            .load(bitmap)
            .fitCenter()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imgView)
    }

    fun setImage(
        context: Context?,
        bitmap: Bitmap?,
        imgView: ImageView,
        progressBar: ProgressBar?,
        havePlaceHolder: Boolean
    ) {
        if (context == null || bitmap == null) return
        Glide.with(context)
            .load(bitmap)
            .fitCenter()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imgView)
    }

    fun setImage(context: Context?, url: String?, imgView: ImageView) {
        if (context == null || TextUtils.isEmpty(url)) return
        Glide.with(context)
            .load(url)
            .fitCenter()
            .into(imgView)
    }

    fun setImage(context: Context?, url: String?, imgView: ImageView, progressBar: ProgressBar?) {
        if (context == null || TextUtils.isEmpty(url)) {
            progressBar?.visibility = View.GONE
            return
        }
        Glide.with(context)
            .load(url)
            .fitCenter()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imgView)
    }

    fun setImage(
        context: Context?,
        url: String?,
        imgView: ImageView,
        progressBar: ProgressBar?,
        havePlaceHolder: Boolean
    ) {
        if (context == null || TextUtils.isEmpty(url)) return
        Glide.with(context)
            .load(url)
            .fitCenter()
            .listener(object : RequestListener<Drawable?> {
                override fun onLoadFailed(
                    e: GlideException?,
                    model: Any,
                    target: Target<Drawable?>,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }

                override fun onResourceReady(
                    resource: Drawable?,
                    model: Any,
                    target: Target<Drawable?>,
                    dataSource: DataSource,
                    isFirstResource: Boolean
                ): Boolean {
                    progressBar?.visibility = View.GONE
                    return false
                }
            })
            .into(imgView)
    }
}
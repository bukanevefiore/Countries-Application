package com.examp.countries.util

import android.content.Context
import android.widget.ImageView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.examp.countries.R

// resimleri her yerden çağırabilmek için
fun ImageView.downloadUrl(url: String?, progressDrawable: CircularProgressDrawable){

    // progres bar ın placeholder içinde çağrılması
    val options=RequestOptions().placeholder(progressDrawable)
            .error(R.mipmap.ic_launcher_round)

    Glide.with(context).setDefaultRequestOptions(options).load(url).into(this)
}

// resimler yüklenene kadar dönecek progressbar ın oluşturulması
fun placeholderProgressBar(context: Context) :CircularProgressDrawable {
    return CircularProgressDrawable(context).apply {
        strokeWidth=8f
        centerRadius=40f
        start()
    }
}
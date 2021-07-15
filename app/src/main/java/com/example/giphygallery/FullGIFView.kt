package com.example.giphygallery


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.nfc.Tag
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout


class FullGIFView : AppCompatActivity() {
    lateinit var gif: WebView
    lateinit var parentView: LinearLayout
    private val YOUR_ANDROID_SDK_KEY = "w3YspOAlqfiCmFrza3Ebijj5KLPm1Ryx"
    companion object{
        const val gif_url = "gif"
    }
    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_gifview)

        parentView = findViewById(R.id.parentView)
        gif = findViewById(R.id.gif_view)

        val result = intent.getStringExtra(gif_url)
        gif.webViewClient = MyWebViewClient()
        gif.settings.javaScriptEnabled = true
        gif.loadUrl(result.toString());

    }
}
private class MyWebViewClient : WebViewClient() {
    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return true
    }

    // Для старых устройств
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
    }
}


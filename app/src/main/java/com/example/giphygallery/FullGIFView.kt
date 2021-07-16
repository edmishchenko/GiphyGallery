package com.example.giphygallery


import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.os.Build
import android.os.Bundle
import android.view.ViewGroup
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity


class FullGIFView : AppCompatActivity() {
    lateinit var parentView: LinearLayout
    private val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
    companion object{
        const val gif_url = "gif"
        const val index_of_option = "index_of_option"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_gifview)

        parentView = findViewById(R.id.parentView)
        // gif = findViewById(R.id.gif_view)

        val result = intent.getStringExtra(gif_url)
        openInWebView(result)


    }

    @SuppressLint("SetJavaScriptEnabled")
    fun openInWebView(url: String?){
        val gif = WebView(this)
        parentView.addView(gif, params)
        gif.webViewClient = MyWebViewClient()
        gif.settings.javaScriptEnabled = true
        gif.loadUrl(url.toString());
    }

}

private class MyWebViewClient : WebViewClient() {
    @TargetApi(Build.VERSION_CODES.N)
    override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
        view.loadUrl(request.url.toString())
        return true
    }

    // for old devices
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        view.loadUrl(url)
        return true
    }

}


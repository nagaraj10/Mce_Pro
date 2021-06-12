package com.example.mce_pro


import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class Webview2 : AppCompatActivity() {
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview2)
        progressDialog = ProgressDialog(this)

        var webview=findViewById<WebView>(R.id.webview2)
        webview.webViewClient=MyBrower1()
        webview.settings.builtInZoomControls = true
        webview.settings.setSupportZoom(true)
        webview.settings.displayZoomControls = true
        webview.setInitialScale(1)
        webview.settings.loadsImagesAutomatically = true
        webview.settings.javaScriptEnabled = true
        webview.settings.useWideViewPort = true
        webview.settings.layoutAlgorithm = WebSettings.LayoutAlgorithm.NORMAL
        webview.scrollBarStyle = WebView.SCROLLBARS_OUTSIDE_OVERLAY
        webview.isScrollbarFadingEnabled = false
        webview.scrollBarStyle = View.SCROLLBARS_INSIDE_OVERLAY
        webview.loadUrl("https://mce.edu.in/")
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.show()
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        var back=findViewById<ImageView>(R.id.back_img)
        back.setOnClickListener {
            finish()
        }
    }
    inner class MyBrower1:WebViewClient(){
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            view!!.loadUrl(url)
            return true
        }
        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            progressDialog!!.dismiss()

        }

    }
}

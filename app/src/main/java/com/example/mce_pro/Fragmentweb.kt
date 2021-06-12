package com.example.mce_pro

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.webkit.WebResourceRequest
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout


class Fragmentweb:Fragment() {
    var progressDialog: ProgressDialog? = null
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v:View=inflater.inflate(R.layout.fragment_web,container,false)
        progressDialog = ProgressDialog(context)

        var webview=v.findViewById<WebView>(R.id.webview1)
        webview.webViewClient=MyBrower()
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
        webview.loadUrl("https://coe1.annauniv.edu/home/")
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.show()
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        var swiperefresh=v.findViewById<SwipeRefreshLayout>(R.id.refresh)
        swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                webview.loadUrl("https://coe1.annauniv.edu/home/")
                swiperefresh.isRefreshing=false
            }

        })




        return v
    }
    inner class MyBrower:WebViewClient(){
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
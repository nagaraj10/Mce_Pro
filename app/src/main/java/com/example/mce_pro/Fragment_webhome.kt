package com.example.mce_pro

import android.app.ProgressDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.fragment.app.Fragment

class Fragment_webhome : Fragment(){
    var progressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_webhome,container,false)
        progressDialog = ProgressDialog(context)
        var webview=v.findViewById<WebView>(R.id.home_web_view)
        webview.webViewClient=MyBrower_home()
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


   var links= arguments!!.getString("feedlinks")


        Log.d("homeurl",links)
        webview.loadUrl(links)
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.show()
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)


        return v
    }
    inner class MyBrower_home :WebViewClient(){
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
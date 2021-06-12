package com.example.mce_pro

import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

class FragmentWeb_Portal2:Fragment() {
    var progressDialog: ProgressDialog? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragmentweb_portal2, container, false)

        progressDialog = ProgressDialog(context)

        var webview = v.findViewById<WebView>(R.id.webview_portal2)
        webview.webViewClient = MyBrower2()
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
        webview.loadUrl("https://aucoe.annauniv.edu/regular_result/login")
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.show()
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)

        var swiperefresh=v.findViewById<SwipeRefreshLayout>(R.id.swipe_portal2)
        swiperefresh.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {
                webview.loadUrl("https://aucoe.annauniv.edu/regular_result/login")
                swiperefresh.isRefreshing=false
            }

        })

        return v
    }

    inner class MyBrower2 : WebViewClient() {
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
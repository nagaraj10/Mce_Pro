package com.example.mce_pro

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment

class Activity_readmore : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_readmore)
        var backimg=findViewById<ImageView>(R.id.back_img)
        backimg.setOnClickListener {
            finish()
        }

    }


}

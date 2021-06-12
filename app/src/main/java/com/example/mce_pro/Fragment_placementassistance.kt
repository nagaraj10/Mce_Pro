package com.example.mce_pro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide

class Fragment_placementassistance:Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragment_placementassistance,container,false)
        var imgplacement=v.findViewById<ImageView>(R.id.img_placement)

        Glide.with(context).load("https://mce.edu.in/wp-content/uploads/2020/02/placement.jpg").into(imgplacement)
        return v
    }

}
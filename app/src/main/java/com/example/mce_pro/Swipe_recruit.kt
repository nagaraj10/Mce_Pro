package com.example.mce_pro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

class Swipe_recruit(var context: Context?, var recruitimage: IntArray):RecyclerView.Adapter<Swipe_recruit.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Swipe_recruit.Viewholder {
        val v:View=LayoutInflater.from(parent.context).inflate(R.layout.swipeimages,parent,false)
        return Viewholder(v)
    }

    override fun getItemCount(): Int {
       return recruitimage.size
    }

    override fun onBindViewHolder(holder: Swipe_recruit.Viewholder, position: Int) {
        holder.imgswipe!!.setImageResource(recruitimage[position])

    }
    inner class Viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        var imgswipe:ImageView?=null
        init {
            imgswipe=itemview.findViewById(R.id.img_swipe)
        }

    }
}
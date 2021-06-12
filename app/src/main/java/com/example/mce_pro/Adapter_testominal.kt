package com.example.mce_pro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter_testominal(
    var context: Context?,
   var  upload_testo: ArrayList<Placement_Testominals>
) :RecyclerView.Adapter<Adapter_testominal.Viewholder>(){
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_testominal.Viewholder {
        val v:View=LayoutInflater.from(parent.context).inflate(R.layout.item_testominal,parent,false)

        return Viewholder(v)

    }

    override fun getItemCount(): Int {
        return upload_testo.size

    }

    override fun onBindViewHolder(holder: Adapter_testominal.Viewholder, position: Int) {
var uploads=upload_testo[position]
       Glide.with(context).load(uploads.links).into(holder.img)
    }

    inner class Viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        var img:ImageView?=null
        init {
            img=itemview.findViewById(R.id.img_testominal)
        }
    }
}
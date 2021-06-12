package com.example.mce_pro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class Adapter_Management(
    var context: Context,
    var images: Array<Int>,
    var messages: Array<String>,
    var quotes: Array<String>,
    var description: Array<String>,
    var sign: Array<String>
):RecyclerView.Adapter<Adapter_Management.Viewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_Management.Viewholder {
        val v:View=LayoutInflater.from(parent.context).inflate(R.layout.item_management,parent,false)
        return Viewholder(v)

    }

    override fun getItemCount(): Int {
       return images.size

    }

    override fun onBindViewHolder(holder: Adapter_Management.Viewholder, position: Int) {
holder.img_management!!.setImageResource(images[position])
        holder.tv_sign!!.text=sign[position]
        holder.tv_description!!.text=description[position]
        holder.tv_quotes!!.text=quotes[position]
        holder.tv_message!!.text=messages[position]

    }

     inner class Viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        var img_management:ImageView?=null
        var tv_message:TextView?=null
        var tv_description:TextView?=null
        var tv_quotes:TextView?=null
        var tv_sign:TextView?=null
        init {
            img_management=itemview.findViewById(R.id.img_management)
            tv_message=itemview.findViewById(R.id.tv_message)
            tv_quotes=itemview.findViewById(R.id.tv_quotes)
            tv_description=itemview.findViewById(R.id.tv_description)
            tv_sign=itemview.findViewById(R.id.tv_sign)
        }


    }
}
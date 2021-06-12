package com.example.mce_pro

import android.content.Context
import android.text.Html
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide


class Adapter_chat(var context: Context?, var chatupload: ArrayList<ChatUpload>):RecyclerView.Adapter<Adapter_chat.Viewholder>() {
    companion object {
        const val VIEW_TYPE_ONE = 1
        const val VIEW_TYPE_TWO = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_chat.Viewholder{
                val v:View=LayoutInflater.from(parent.context).inflate(R.layout.chat_items,parent,false)
                return Viewholder(v)


    }

    override fun getItemCount(): Int {
        return chatupload.size

    }

    override fun onBindViewHolder(holder: Adapter_chat.Viewholder, position: Int) {
        var chatupload=chatupload[position]
        holder.chat_name!!.text=chatupload.name

      holder.msg!!.movementMethod=LinkMovementMethod.getInstance()
        holder.msg!!.text=Html.fromHtml(chatupload.msg)
        Linkify.addLinks(holder.msg!!, Linkify.WEB_URLS or Linkify.PHONE_NUMBERS)
        Linkify.addLinks(holder.msg!!, Linkify.ALL );

        holder.posttime!!.text=chatupload.time
        Glide.with(context!!).load(chatupload.imgurl).into(holder.img!!)


    }
    inner class Viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        var img:ImageView?=null
        var chat_name:TextView?=null
        var msg:TextView?=null
        var posttime:TextView?=null
        init {
            img=itemview.findViewById(R.id.person_img)
            chat_name=itemview.findViewById(R.id.chat_name)
            msg=itemview.findViewById(R.id.msg_text)
            posttime=itemview.findViewById(R.id.tv_postdate)

        }
    }
    inner class Viewholder2(itemview: View):RecyclerView.ViewHolder(itemview){
        var img:ImageView?=null
        var chat_name:TextView?=null
        var msg:TextView?=null
        var posttime:TextView?=null
        init {
            img=itemview.findViewById(R.id.person_img)
            chat_name=itemview.findViewById(R.id.chat_name)
            msg=itemview.findViewById(R.id.msg_text)
            posttime=itemview.findViewById(R.id.tv_postdate)

        }
    }


}
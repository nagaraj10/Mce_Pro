package com.example.mce_pro

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class Adapter_placementrecord(
  var   context: Context?,
    var uploads: ArrayList<PlacementRecordUpload>
) :RecyclerView.Adapter<Adapter_placementrecord.Viewholder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): Adapter_placementrecord.Viewholder {
    val v: View =LayoutInflater.from(parent.context).inflate(R.layout.item_placementrecord,parent,false)
        return Viewholder(v)
    }

    override fun getItemCount(): Int {
        return uploads.size

    }

    override fun onBindViewHolder(holder: Adapter_placementrecord.Viewholder, position: Int) {
    var placerecord=uploads[position]
        holder.tv_placementyear!!.text=placerecord.year


        if (placerecord.imageurl!=null || placerecord.imageurl!=""){
            holder.studimage!!.visibility=View.VISIBLE
            Glide.with(context).load(placerecord.imageurl).into(holder.studimage)
        }
        else{
            holder.studimage!!.visibility=View.GONE
        }
        holder.pimg_download!!.setOnClickListener {

            var imageName:String=placerecord.sheeturl!!
            val downloadManager = context!!.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri: Uri = Uri.parse(imageName)
            val request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val reference = downloadManager.enqueue(request)
            if (reference.toInt()>0){

                Toast.makeText(context,"download completed",Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class Viewholder(itemview:View):RecyclerView.ViewHolder(itemview){
        var tv_placementyear:TextView?=null
        var studimage:ImageView?=null
        var pimg_download:ImageView?=null

        init {
            tv_placementyear=itemview.findViewById(R.id.tv_placeyear)
            studimage=itemview.findViewById(R.id.stud_place)
        pimg_download=itemview.findViewById(R.id.p_imgdownload)
        }
    }

}
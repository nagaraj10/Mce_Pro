package com.example.mce_pro

import android.content.Context
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import java.util.*


class Adapter_homefeed(
  var  context: Context?,
    var uploads: ArrayList<HomeFeed>
) :RecyclerView.Adapter<Adapter_homefeed.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Adapter_homefeed.Viewholder {
        val v:View=LayoutInflater.from(parent.context).inflate(R.layout.homefeed_item,parent,false)
        return Viewholder(v)

    }

    override fun getItemCount(): Int {
return uploads.size
    }

    override fun onBindViewHolder(holder: Adapter_homefeed.Viewholder, position: Int) {
        var huploads=uploads[position]
holder.tvfeed!!.text=Html.fromHtml(huploads.name)

/*
        val random = Random()

        val color = Color.argb(
            255,
            random.nextInt(256),
            random.nextInt(256),
            random.nextInt(256)
        )

        holder.tvfeed!!.setTextColor(color)
*/
        val androidColors: IntArray = context!!.resources.getIntArray(R.array.androidcolors)
        val randomAndroidColor = androidColors[Random().nextInt(androidColors.size)]
        holder.tvfeed!!.setTextColor(randomAndroidColor)


        holder.tvfeed!!.movementMethod=LinkMovementMethod.getInstance()


        holder.tvfeed!!.setOnClickListener {
            val bundle = Bundle()
            bundle.putString("feedlinks",huploads.links)
            var fragwebhome=Fragment_webhome()
            fragwebhome.arguments=bundle

            val fragmentManager: FragmentManager =
                (it.getContext() as FragmentActivity).supportFragmentManager
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.frag1,fragwebhome,"Fragweb_home")
            fragmentTransaction.disallowAddToBackStack()
            fragmentTransaction.commit()
        }
    }


    inner class Viewholder(itemView: View):RecyclerView.ViewHolder(itemView){
        var tvfeed:TextView?=null
        init {
            tvfeed=itemView.findViewById(R.id.tv_hfeed)
        }
    }
}
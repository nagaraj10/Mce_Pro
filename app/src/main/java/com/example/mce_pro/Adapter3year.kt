package com.example.mce_pro

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView


class Adapter3year(var context: Context?, var dataList1: List<UserData>?):RecyclerView.Adapter<Adapter3year.Viewholder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):Viewholder {
        val v: View =LayoutInflater.from(parent.context).inflate(R.layout.msgrecycler,parent,false)
        return Viewholder(v)
         

    }

    override fun getItemCount(): Int {
        return dataList1!!.size
    }

    override fun onBindViewHolder(holder: Viewholder, position: Int) {

        holder.stuindex!!.text=(dataList1!!.get(position).id)
        holder.stuname!!.text=(dataList1!!.get(position).subject)
        holder.stunumber!!.text=(dataList1!!.get(position).description)

        holder.delimg!!.setOnClickListener {
            val dh = DatabaseHelper(context)
            /////Alert dialog///
            val alertDialogBuilder =
                    AlertDialog.Builder(context!!)
            alertDialogBuilder.setMessage("Are you sure, You wanted to delete")
            alertDialogBuilder.setPositiveButton(
                    "yes"
            ) { arg0, arg1 ->
                /////sql delete operations performing /////
                dh.delete((dataList1!!.get(position).id))

                var fragment_msg3: Fragment?
                val fragmentmanager: FragmentManager = (it.getContext() as FragmentActivity).supportFragmentManager


                fragment_msg3 = Fragment_msg_service3()
                val m=fragmentmanager!!.beginTransaction()
                m.replace(R.id.frag1, fragment_msg3,"frag_msg3")
                m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                m.disallowAddToBackStack()
                m.commit()
                Toast.makeText(context,"deleted",Toast.LENGTH_SHORT).show()



            }
            alertDialogBuilder.setNegativeButton(
                    "No"
            ) { dialog, which -> }
            val alertDialog = alertDialogBuilder.create()
            alertDialog.show()

        }

        holder.itemView.setOnClickListener {


           var ids=(dataList1!!.get(position).id)
         var names=(dataList1!!.get(position).subject)
            var depart=(dataList1!!.get(position).description)
            //to pass the data from fragement to fragment


          val i=Intent(context,Modify_Activity::class.java)
            i.putExtra("id",ids)
            i.putExtra("name",names)
            i.putExtra("depart",depart)
     context!!.startActivity(i)


        }



    }
     inner class Viewholder(itemView:View):RecyclerView.ViewHolder(itemView){
        var stuindex:TextView?=null
        var stuname:TextView?=null
        var stunumber:TextView?=null
         var delimg:ImageView?=null
        init {
            stuindex=itemView.findViewById(R.id.studentindex)
            stuname=itemView.findViewById(R.id.studentname)
            stunumber=itemView.findViewById(R.id.studentnumber)
            delimg=itemView.findViewById(R.id.del)
        }

    }
}
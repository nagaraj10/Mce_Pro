package com.example.mce_pro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.firebase.database.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class Community : Fragment() {
    var btn_snd:Button?=null
    var et_snd:EditText?=null
    var mDatabase: DatabaseReference? = null
    private var uploads: MutableList<ChatUpload>? = null
    var recyc_chats:RecyclerView?=null
    var swipe:SwipeRefreshLayout?=null
    var fragment_community: Fragment?=null
    var adapterChat:Adapter_chat?=null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.activity_community,container,false)
        btn_snd=v.findViewById(R.id.chat_snd)
        et_snd=v.findViewById(R.id.chat_et)
        recyc_chats=v.findViewById(R.id.recyc_chats)



        fragment_community = Community()
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        layoutManager.stackFromEnd = true
        recyc_chats!!.layoutManager = layoutManager
         swipe=v.findViewById<SwipeRefreshLayout>(R.id.swipe)


        val database = FirebaseDatabase.getInstance()
        uploads = ArrayList()

        mDatabase = database.getReference(Constants().chatfolder)

        et_snd!!.setOnClickListener {
            recyc_chats!!.smoothScrollToPosition(adapterChat!!.itemCount-1)
        }



        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(snapshot: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children) {
                    val upload = postSnapshot.getValue(ChatUpload::class.java)!!
                    uploads!!.add(upload)
                   /* if (postSnapshot.ref.child("chats").orderByChild("name").equals(Shared_pref(context).getname())){

                    }
                    else {


                      *//*  val Intent = Intent(context, MainActivity::class.java)
                        Intent.putExtra("menuFragment", "favoritesMenuItem")*//*
                        try{
                            val Intent= Intent(activity,MainActivity::class.java)
                            val pendingIntent = PendingIntent.getActivity(
                                context,
                                100,
                                Intent,
                                PendingIntent.FLAG_CANCEL_CURRENT
                            )
                            val mBuilder =
                                NotificationCompat.Builder(context!!, MainActivity.CHANNEL_ID)
                                    .setSmallIcon(R.mipmap.mce1)
                                    .setColor(ContextCompat.getColor(context!!, R.color.blue))
                                    .setContentTitle("You have an new message")
                                    .setContentText("Click to view")
                                    .setContentIntent(pendingIntent)
                                    .setAutoCancel(true)
                                    .setPriority(NotificationCompat.PRIORITY_HIGH)
                            val notificationmgr = NotificationManagerCompat.from(context!!)
                            notificationmgr.notify(1, mBuilder.build())
                        }catch (e:Exception){
                            e.printStackTrace()
                        }

                    }*/


                }





                adapterChat=Adapter_chat(context,uploads as ArrayList<ChatUpload>)
                recyc_chats!!.adapter=adapterChat
                adapterChat!!.notifyDataSetChanged()



            }




        })



        val query = FirebaseDatabase.getInstance()
            .reference
            .child("chats")
            .limitToLast(50)

        var childEventListener:ChildEventListener= object : ChildEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onChildChanged(p0: DataSnapshot, p1: String?) {

                    if (p0.value != null) {
                       Toast.makeText(context,"hhhh",Toast.LENGTH_SHORT).show()

                        var m=fragmentManager!!.beginTransaction()
                        m.replace(R.id.frag1, fragment_community!!)
                        // m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        m.disallowAddToBackStack()
                        m.commit()




            }
            }

            override fun onChildAdded(p0: DataSnapshot, p1: String?) {

                var adapter=Adapter_chat(context,uploads as ArrayList<ChatUpload>)
                recyc_chats!!.adapter=adapter
                adapter.notifyDataSetChanged()


                   Log.d("child added",p0.key)




            }

            override fun onChildRemoved(p0: DataSnapshot) {
                if (p0.hasChildren()){
                    var m=childFragmentManager!!.beginTransaction()
                    m.replace(R.id.frag1, fragment_community!!)
                    // m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                    m.disallowAddToBackStack()
                    m.commit()

                }

            }

        }

        query.addChildEventListener(childEventListener)




        swipe!!.setOnRefreshListener(object : SwipeRefreshLayout.OnRefreshListener {
            override fun onRefresh() {


                var m=fragmentManager!!.beginTransaction()
                m.replace(R.id.frag1, fragment_community!!)
               // m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                m.disallowAddToBackStack()
                m.commit()

            }

        })

        btn_snd!!.setOnClickListener {
            sndmessage()
        }

        return v
    }

    fun sndmessage(){
        var msg=et_snd!!.text.toString()
        val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm aa")
        val ctime: String = sdf.format(Date())
        if (msg.isEmpty()||msg.length==0||msg.equals("")||msg==null) {
            Toast.makeText(context,"Please snd some msg",Toast.LENGTH_SHORT).show()

        }
        else{
            val chatupload = ChatUpload(
                Shared_pref(context).getname(),
                Shared_pref(context).getphoto(),
                msg,
                ctime,
                    Shared_pref(context).getid()
            )
            val uploadId = mDatabase!!.push().key
            mDatabase!!.child(uploadId!!).setValue(chatupload)
            et_snd!!.setText("")

            var m=fragmentManager!!.beginTransaction()
            m.replace(R.id.frag1, fragment_community!!)
           // m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            m.disallowAddToBackStack()
            m.commit()
        }
    }


}
package com.example.mce_pro

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*

class FragmentPlacement_Records:Fragment() {
    var recyclerview_records:RecyclerView?=null
    var mDatabase: DatabaseReference? = null
    private var uploads: MutableList<PlacementRecordUpload>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View=inflater.inflate(R.layout.fragmentplacement_records,container,false)
recyclerview_records=v.findViewById(R.id.recyc_placementrecord)

        val database = FirebaseDatabase.getInstance()
        mDatabase=database.getReference(Constants().placementrecords_folder)
       uploads=ArrayList()



        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
for (postsnapshot in snapshot.children){
    val upload = postsnapshot.getValue(PlacementRecordUpload::class.java)!!
    uploads!!.add(upload)

    Log.d("data",upload.sheeturl)
}

                var lm=LinearLayoutManager(context)
                lm.orientation=LinearLayoutManager.VERTICAL
                lm.stackFromEnd=true
                lm.reverseLayout=true
                recyclerview_records!!.layoutManager=lm
                recyclerview_records!!.adapter=Adapter_placementrecord(context,uploads as ArrayList<PlacementRecordUpload>)
            }

        })


        return v
    }


}
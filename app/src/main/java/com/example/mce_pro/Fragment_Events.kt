package com.example.mce_pro


import android.app.ProgressDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


class Fragment_Events : Fragment() {
    var recycler:RecyclerView?=null
    var storage: FirebaseStorage?=null
    var storageReference: StorageReference?=null
    var databaseReference:DatabaseReference?=null


    //adapter object
    private var adapter: RecyclerView.Adapter<*>? = null
    //database reference
    private var mDatabase: DatabaseReference? = null
    //list to hold all the uploaded images
    private var uploads: MutableList<Upload>? = null



    // Creating Progress dialog
    var progressDialog: ProgressDialog? = null
    // Root Database Name for Firebase Database.
    val  Database_Path:String = "All_Image_Uploads_Database"
    var img_ex:ImageView?=null


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v:View= inflater.inflate(R.layout.fragment_event,container,false)
        img_ex=v.findViewById(R.id.im_ex)



        var ex=v.findViewById<TextView>(R.id.ex)
        recycler=v.findViewById(R.id.recycler)

        recycler!!.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(context)
        layoutManager.orientation=LinearLayoutManager.VERTICAL
        layoutManager.stackFromEnd = true
        layoutManager.reverseLayout=true
        recycler!!.layoutManager = layoutManager
      



        progressDialog = ProgressDialog(context)
        uploads = ArrayList()
        //displaying progress dialog while fetching images
        progressDialog!!.setMessage("Please wait...")
        progressDialog!!.show()
        progressDialog!!.setCancelable(false)
        progressDialog!!.setCanceledOnTouchOutside(false)
        mDatabase = FirebaseDatabase.getInstance().getReference(Constants().DATABASE_PATH_UPLOADS)
        //adding an event listener to fetch values
        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) { //dismissing the progress dialog
                progressDialog!!.dismiss()
                //iterating through all the values in database
                for (postSnapshot in snapshot.children) {
                    val upload = postSnapshot.getValue(Upload::class.java)!!
                    uploads!!.add(upload)
                }
                //creating adapter
                adapter = context?.let { MyAdapter(it, uploads as ArrayList<Upload>) }
                //adding adapter to recyclerview
                recycler!!.adapter = adapter

            }

            override fun onCancelled(databaseError: DatabaseError) {
                progressDialog!!.dismiss()
            }
        })








        return v
    }


}

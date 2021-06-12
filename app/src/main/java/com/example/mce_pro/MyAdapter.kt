package com.example.mce_pro



import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnLongClickListener
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*


internal class MyAdapter(context: Context, var uploads: List<Upload>) :
        RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    var context: Context



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v: View = LayoutInflater.from(parent.context).inflate(R.layout.layout_images, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var upload = uploads[position]
        holder.textViewName.text = upload.name
        holder.currentime.text=upload.ctime

        Glide.with(context).load(upload.url).into(holder.imageView1)
        holder.itemView.setOnClickListener {

            /*  Toast.makeText(context,String.valueOf(sportnames.get(position)), Toast.LENGTH_SHORT).show();*/
            val i=Intent(context,Activity_Image::class.java)
            i.putExtra("name", upload.name) // Put anything what you want
            i.putExtra("ctime", upload.ctime)
            i.putExtra("image", upload.url)
            i.putExtra("desc",upload?.etdesc)

            context.startActivity(i)


        }
        holder.itemView.setOnLongClickListener(OnLongClickListener {


            val options = arrayOf<CharSequence>("Delete",  "Cancel")
            val b = AlertDialog.Builder(context)
            b.setTitle("Are you sure want to delete?")

            b.setItems(options) { dialog, item ->
                if (options[item] == "Delete") {
                    val ref = FirebaseDatabase.getInstance().reference
                    val applesQuery: Query = ref.child("uploads").orderByChild("ctime").equalTo(upload.ctime)

                    applesQuery.addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            for (appleSnapshot in dataSnapshot.children) {
                                appleSnapshot.ref.removeValue()

                                val fragment: Fragment = Fragment_Events()
                                val fragmentManager: FragmentManager = (context as FragmentActivity).supportFragmentManager
                                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                                fragmentTransaction.replace(R.id.frag1, fragment)
                                fragmentTransaction.disallowAddToBackStack()
                                fragmentTransaction.commit()

                                Toast.makeText(context, "Successfully deleted ", Toast.LENGTH_SHORT).show()
                            }
                        }

                        override fun onCancelled(databaseError: DatabaseError) {
                            Log.e(TAG, "onCancelled", databaseError.toException())
                        }
                    })





                } else if (options[item] == "Cancel") {
                    dialog.dismiss()
                }
            }
            b.show()

            true // returning true instead of false, works for me
        })
    }

    override fun getItemCount(): Int {
        return uploads.size
    }

    internal inner class ViewHolder(itemView: View) :
            RecyclerView.ViewHolder(itemView) {
        var textViewName: TextView
        var imageView1: ImageView
        var currentime:TextView

        init {
            textViewName = itemView.findViewById(R.id.textViewName)
            imageView1 = itemView.findViewById(R.id.imageView1)
            currentime=itemView.findViewById(R.id.currenttime)
        }
    }

    init {
        this.context = context
    }


}
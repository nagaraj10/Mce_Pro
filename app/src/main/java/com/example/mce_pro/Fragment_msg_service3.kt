package com.example.mce_pro

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.SimpleCursorAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment__fabdata.*

class Fragment_msg_service3 : Fragment() {
    var mydb: DatabaseHelper? = null

    private val adapter: SimpleCursorAdapter? = null
    private var et_sql_name: EditText? = null
    private  var et_sql_depart:EditText? = null
    private var btn_sql_add: Button? = null
    private  var btn_sql_display:Button?= null


    var index= arrayOf(DatabaseHelper.ID)
    var name= arrayOf(DatabaseHelper.SUBJECT)
    var number = arrayOf(DatabaseHelper.DESCRIPTION)

    var fab:FloatingActionButton?=null



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val v:View= inflater.inflate(R.layout.fragment_msg_service3,container,false)
        fab=v.findViewById(R.id.fabbb)

   var recyclerdata=v.findViewById<RecyclerView>(R.id.recycledata)


        recyclerdata.addOnScrollListener(object : RecyclerView.OnScrollListener() {
           override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView!!, dx, dy)
                if (dy > 0 &&fab!!.getVisibility() === View.VISIBLE) {
                    fab!!.hide()
                } else if (dy < 0 && fab!!.getVisibility() !== View.VISIBLE) {
                    fab!!.show()
                }
            }
            
        })
        ////creating a class object///
        mydb = DatabaseHelper(context)


        var datas =DatabaseHelper(context).getAllUser()

        //recycler//
        var gm=GridLayoutManager(context,1)
        recyclerdata.layoutManager=gm
        var adapter=Adapter3year(context,datas)
        recyclerdata.adapter=adapter
       adapter.notifyDataSetChanged()




      /*  btn_sql_add!!.setOnClickListener(View.OnClickListener {
            val name = et_sql_name!!.text.toString()
            val department = et_sql_depart!!.text.toString()
            if (!name.isEmpty() && !department.isEmpty()) {
                mydb!!.insertContact(name, department)
                Toast.makeText(context, "details added", Toast.LENGTH_SHORT).show()
                et_sql_name!!.text = null
                et_sql_depart!!.text = null
            } else {
                Toast.makeText(context, "Please enter data to insert", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        ///////////////////////////////
        btn_sql_display!!.setOnClickListener(View.OnClickListener {


            val m=fragmentManager!!.beginTransaction()
            m.replace(R.id.frag1,Fragment_my_listview()!!,"frag_my_list")
            m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            ///m.addToBackStack("frag_msg3")
            m.commit()
        })
*/

        ////


        fab!!.setOnClickListener {


           /* var viewGroup: ViewGroup = v.findViewById(android.R.id.content)
            var dialogView: View = LayoutInflater.from(context).inflate(R.layout.fragment__fabdata, viewGroup, false)
            builder=AlertDialog.Builder(context!!)
            builder!!.setView(dialogView)
            val alertDialog: AlertDialog = builder!!.create()
            alertDialog.show()*/
            /////alert dialog is used for oly information operations cant be done to do operation use dialog///
            var   dialog:Dialog = Dialog(context!!);
            dialog.setContentView(R.layout.fragment__fabdata);
            dialog.setCancelable(false)
            dialog.close.setOnClickListener {
                var fragment_msg3: Fragment?

                fragment_msg3 = Fragment_msg_service3()
                var m=fragmentManager!!.beginTransaction()
                m.replace(R.id.frag1, fragment_msg3)
                m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                m.disallowAddToBackStack()
                m.commit()
                dialog.dismiss()
            }
            dialog.btn_sqladd.setOnClickListener {
                var userData = UserData()
                userData.subject = dialog.sql_name!!.text.toString()
                userData.description = dialog.sql_depart!!.text.toString()
                if (!userData.subject!!.isEmpty() && !userData.description!!.isEmpty()) {
                    mydb!!.insertUserDetail(userData)
                    dialog.sql_name!!.setText("")
                    dialog.sql_depart!!.setText("")



                    Toast.makeText(context, "details added", Toast.LENGTH_SHORT).show()

                } else {
                    Toast.makeText(context, "Please enter data to insert", Toast.LENGTH_SHORT)
                            .show()
                }
            }


            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog.show()
        }



        return v
    }
}

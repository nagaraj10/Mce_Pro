package com.example.mce_pro


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.telephony.SmsManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText

import android.widget.ImageView
import android.widget.Toast

import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import java.text.SimpleDateFormat

import java.util.*


class Modify_Activity:AppCompatActivity() {

    private var et_updatename: EditText? = null
    private var et_updatedepartment: EditText? = null

    private var btn_update: Button? = null
    private var btn_clear: Button? = null
    private var ids: Long = 0
    private var btn_absent: Button? = null
    private var btn_cusmsg: Button? = null
    private var et_cusmsg: EditText? = null
    private var btn_late: Button? = null
    private val snd_sms = 10
    private val late_sms = 12
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_modify)
        et_updatename = findViewById(R.id.sql_upname)
        et_updatedepartment = findViewById(R.id.sql_updepart)

        btn_update = findViewById(R.id.sql_update)
        btn_clear = findViewById(R.id.clear)
       btn_absent = findViewById(R.id.btn_absent)
        btn_late = findViewById(R.id.btn_late)
        btn_cusmsg = findViewById(R.id.btn_cusmsg)
        et_cusmsg = findViewById(R.id.et_cusmsg)

        val dh = DatabaseHelper(this)

        btn_cusmsg!!.setOnClickListener {
            var e = et_cusmsg!!.text.toString()
            val intent: Intent = getIntent()
            val id = intent.getStringExtra("id")
            val name = intent.getStringExtra("name")
            val desc = intent.getStringExtra("depart")
            val currentTime: Date = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val s: String = sdf.format(Date())

            var smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(desc, null, e + "\n$s", null, null)
            Toast.makeText(this, "SMS sent.", Toast.LENGTH_LONG).show()
        }

        btn_absent!!.setOnClickListener {
            checkpermission(Manifest.permission.SEND_SMS, snd_sms)
        }
        btn_late!!.setOnClickListener {
            checkpermission(Manifest.permission.SEND_SMS, late_sms)
        }
        val id = intent.getStringExtra("id")
        val name = intent.getStringExtra("name")
        val desc = intent.getStringExtra("depart")
        ids = id!!.toLong()

        et_updatename!!.setText(name)
        et_updatedepartment!!.setText(desc)



        btn_update!!.setOnClickListener {

            var name = et_updatename!!.text.toString()
            var desc = et_updatedepartment!!.text.toString()

            dh.update(ids, name, desc)
            Toast.makeText(this,"Updated",Toast.LENGTH_SHORT).show()
        }


        btn_clear!!.setOnClickListener {
            et_updatename!!.setText("")
            et_updatedepartment!!.setText("")
        }

        var back=findViewById<ImageView>(R.id.back_img)
        back.setOnClickListener {
            returnHome()
        }



    }
    fun returnHome() {
        finish()

    }














    fun checkpermission(permission: String, requestcode: Int) {
        if (ContextCompat.checkSelfPermission(this!!, permission) == PackageManager.PERMISSION_DENIED) {
            requestPermissions(this, arrayOf(permission), requestcode)
        } else {
            if (requestcode==snd_sms){
                val intent: Intent = getIntent()
                val name = intent.getStringExtra("name")
                val desc = intent.getStringExtra("depart")
            val currentTime: Date = Calendar.getInstance().time
            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
            val s: String = sdf.format(Date())

            var smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(desc, null,"From Meenakshi college of Engineering:\n$s\nRegarding: $name is absent today,please give a explanation..", null, null)
            Toast.makeText(this ,"SMS sent.",
                Toast.LENGTH_LONG
            ).show()}
            if (requestcode==late_sms){
                val intent: Intent = getIntent()
                val name = intent.getStringExtra("name")
                val desc = intent.getStringExtra("depart")
                val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                val s: String = sdf.format(Date())

                var smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(desc, null, "From Meenakshi college of Engineering:\n$s\nRegarding: $name is Late today,please give a explanation..", null, null)
                Toast.makeText(this, "SMS sent.", Toast.LENGTH_LONG).show()


            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)


        if (requestCode ==snd_sms) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent: Intent = getIntent()
                val name = intent.getStringExtra("name")
                val desc = intent.getStringExtra("depart")
                val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                val s: String = sdf.format(Date())

                var smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(
                    desc,
                    null,
                    "From Meenakshi college of Engineering:\n$s\nRegarding: $name is absent today..",
                    null,
                    null
                )
                Toast.makeText(
                    this, "SMS sent.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

            else if (requestCode==late_sms) {

            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val intent: Intent = getIntent()
                val name = intent.getStringExtra("name")
                val desc = intent.getStringExtra("depart")
                val sdf = SimpleDateFormat("dd-MM-yyyy  hh:mm:ss aa")
                val s: String = sdf.format(Date())

                var smsManager = SmsManager.getDefault()
                smsManager.sendTextMessage(
                    desc,
                    null,
                    "From Meenakshi college of Engineering:\n$s\nRegarding: $name is Late today..",
                    null,
                    null
                )
                Toast.makeText(this, "SMS sent.", Toast.LENGTH_LONG).show()
            }
        }

            else {
                Toast.makeText(this, "SMS faild, please try again.", Toast.LENGTH_LONG).show()
            }
        }



    }







package com.example.mce_pro

import android.content.Context
import android.content.SharedPreferences

class Shared_pref(var context:Context?) {

    fun savelogindetails(photo:String,name:String,email:String,id:String){
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("LoginDetails",Context.MODE_PRIVATE)
        var editor:SharedPreferences.Editor=sharedPreferences.edit()
        editor.putString("gphoto",photo)
        editor.putString("gname",name)
        editor.putString("gemail",email)
        editor.putString("gid",id)
        //editor.putBoolean("userlogin",false)
        editor.commit()

    }
    fun getname(): String? {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        var name= sharedPreferences.getString("gname", "No name defined")
        return name
    }
    fun getemail(): String? {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        var email= sharedPreferences.getString("gemail", "No name defined")
        return email
    }
    fun getid(): String? {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        var id= sharedPreferences.getString("gid", "No name defined")
        return id
    }
    fun getphoto(): String? {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        var photo= sharedPreferences.getString("gphoto", "No name defined")
        return photo
    }

    // Check for login
    fun isUserLoggedIn(): Boolean {
        val sharedPreferences: SharedPreferences = context!!.getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("userlogin", false)
    }

////////
    fun addswitchdata(switch:Boolean){
    val sharedPreferences:SharedPreferences=context!!.getSharedPreferences("switchData",Context.MODE_PRIVATE)
    var editor:SharedPreferences.Editor=sharedPreferences.edit()
    editor.putBoolean("switchon",switch)

    fun getswitch():Boolean?{
        val sharedPreferences:SharedPreferences=context!!.getSharedPreferences("switchData",Context.MODE_PRIVATE)
        return sharedPreferences.getBoolean("switchon",true)
    }
}


}
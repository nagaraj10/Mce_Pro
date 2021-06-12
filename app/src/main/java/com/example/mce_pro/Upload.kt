package com.example.mce_pro


import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Upload {
    var name: String? = null
    var url: String? = null
    var ctime:String?=null
    var etdesc:String?=null

    // Default constructor required for calls to
// DataSnapshot.getValue(User.class)
    constructor() {}

    constructor(name: String?, url: String?,ctime:String?,etdes:String?) {
        this.name = name
        this.url = url
        this.ctime=ctime
        this.etdesc=etdes
    }

}


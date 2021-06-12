package com.example.mce_pro

import com.google.firebase.database.IgnoreExtraProperties
@IgnoreExtraProperties
class ChatUpload {

    var name: String? = null
    var imgurl: String? = null
    var msg:String?=null
    var time:String?=null
    var googleid:String?=null

        // Default constructor required for calls to
// DataSnapshot.getValue(User.class)
        constructor(){}

    constructor(name: String?, url: String?,msg:String?,time:String?,googleid:String?) {
            this.name = name
            this.imgurl = url
            this.msg=msg
            this.time=time
            this.googleid=googleid
        }

    }



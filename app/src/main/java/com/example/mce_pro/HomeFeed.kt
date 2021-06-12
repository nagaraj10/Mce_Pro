package com.example.mce_pro

import com.google.firebase.database.IgnoreExtraProperties
import java.net.URL

@IgnoreExtraProperties
class HomeFeed {

    var name: String? = null
    var links:String?=null


    // Default constructor required for calls to
// DataSnapshot.getValue(User.class)
    constructor(){}

    constructor(name: String?, links: String) {
        this.name = name
        this.links=links

    }

}

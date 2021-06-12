package com.example.mce_pro

import com.google.firebase.database.IgnoreExtraProperties


@IgnoreExtraProperties
class PlacementRecordUpload {
    var year: String? = null
    var sheeturl: String? = null
    var imageurl: String? = null

    // Default constructor required for calls to
// DataSnapshot.getValue(User.class)
    constructor() {}

    constructor(year: String?, sheeturl: String?, imageurl: String?) {
        this.year = year
        this.sheeturl =sheeturl
        this.imageurl = imageurl
    }
}

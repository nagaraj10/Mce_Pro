package com.example.mce_pro

import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log



class DatabaseHelper(context: Context?) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_TABLE)
    }

    override fun onUpgrade(
        db: SQLiteDatabase,
        oldVersion: Int,
        newVersion: Int
    ) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        Log.d("Database", "created")
        onCreate(db)
    }

    val alldata: Cursor
        get() {
            val db = this.readableDatabase
            return db.rawQuery("select* from $TABLE_NAME", null)
        }


    fun insertContact(name: String?, department: String?): Boolean {
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(SUBJECT, name)
        contentValues.put(DESCRIPTION, department)
        Log.d("value", "inserted")
        db.insert(TABLE_NAME, null, contentValues)
        return true
    }

    fun update(id: Long, name: String?, department: String?) {
        val db = this.writableDatabase
        val cvUpdate = ContentValues()
        cvUpdate.put(ID, id)
        cvUpdate.put(SUBJECT, name)
        cvUpdate.put(DESCRIPTION, department)
        db.update(
            TABLE_NAME,
            cvUpdate,
            "$ID=$id",
            null
        )
    }

    fun delete(id: String?) {
        val db = this.writableDatabase
        db.delete(TABLE_NAME, "$ID=$id", null)
    }


    fun insertUserDetail(userData: UserData) {
        val db = writableDatabase
        db.beginTransaction()
        try {
            val values = ContentValues()
            values.put(SUBJECT, userData.subject)
            values.put(DESCRIPTION, userData.description)

            db.insertOrThrow(TABLE_NAME, null, values)
            db.setTransactionSuccessful()
        } catch (e: SQLException) {
            e.printStackTrace()
            Log.d(TAG, "Error while trying to add post to database")
        } finally {
            db.endTransaction()
        }
    }

    fun getAllUser(): List<UserData>? {
        val usersdetail: MutableList<UserData> = ArrayList()
        val USER_DETAIL_SELECT_QUERY = "SELECT * FROM $TABLE_NAME"
        val db = readableDatabase
        val cursor = db.rawQuery(USER_DETAIL_SELECT_QUERY, null)
        try {
            if (cursor!!.moveToFirst()) {
                do {
                    val userData = UserData()
                    userData.id = cursor.getString(cursor.getColumnIndex(ID))
                    userData.subject = cursor.getString(cursor.getColumnIndex(SUBJECT))
                    userData.description = cursor.getString(cursor.getColumnIndex(DESCRIPTION))
                    usersdetail.add(userData)
                } while (cursor.moveToNext())
            }
        } catch (e: Exception) {
            Log.d(TAG, "Error while trying to get posts from database")
        } finally {
            if (cursor != null && !cursor.isClosed) {
                cursor.close()
            }
        }
        return usersdetail
    }





    companion object {
        ////table columns we must mention here//
        const val TABLE_NAME = "Example"
        ///next table columns///
        const val ID = "_id"
        const val SUBJECT = "subject"
        const val DESCRIPTION = "descr"
        ////next the database name///
        const val DB_NAME = "mce"
        /////next the version //
        const val DB_VERSION = 1
        ///ceating a table //
        private const val CREATE_TABLE =
            "create table $TABLE_NAME($ID INTEGER PRIMARY KEY AUTOINCREMENT,$SUBJECT TEXT NOT NULL,$DESCRIPTION TEXT);"
    }
}
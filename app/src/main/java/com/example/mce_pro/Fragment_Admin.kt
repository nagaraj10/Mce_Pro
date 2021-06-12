package com.example.mce_pro

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.MimeTypeMap
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class Fragment_Admin : Fragment() {

    var tv_scrolltype: TextView?=null
    var btn_upload: Button?=null
    var img_view: ImageView?=null
    var btn_select: Button?=null
    var img_name: EditText?=null
    var filePath: Uri? = null
    var btn_3year: Button?=null
    var et_desc: EditText?=null
    var et_homefeed:EditText?=null
    var et_homelinks:EditText?=null
    var btn_hfeed:Button?=null
    var btn_placement_testo:Button?=null
    var et_testominal:EditText?=null
    var btn_placerecord:Button?=null
    var et_placeyear:EditText?=null
    var et_placesheet:EditText?=null
    var et_placestud:EditText?=null


    // request code
    private val PICK_IMAGE_REQUEST = 22


    //firebase objects
    var storage: FirebaseStorage?=null
    var storageReference: StorageReference?=null
    var mDatabase: DatabaseReference? = null
    var mdatabasehome:DatabaseReference?=null
    var mdatabaseplacetesto:DatabaseReference?=null
    var mdatabaseplacementrecord:DatabaseReference?=null



    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v:View= inflater.inflate(R.layout.fragment__admin,container,false)
        img_name=v.findViewById(R.id.img_name)

        btn_3year=v.findViewById(R.id.btn_3year)
       
btn_placerecord=v.findViewById(R.id.btn_placerecord)
        btn_upload=v.findViewById(R.id.upload)
        btn_select=v.findViewById(R.id.select)
        img_view=v.findViewById(R.id.img_view)
        et_desc=v.findViewById(R.id.img_descriptions)
        et_homefeed=v.findViewById(R.id.et_feed)
        et_homelinks=v.findViewById(R.id.et_links)
et_placeyear=v.findViewById(R.id.et_yearplacement)
        et_placesheet=v.findViewById(R.id.et_datasheet)
        et_placestud=v.findViewById(R.id.et_placestud)

        btn_hfeed=v.findViewById(R.id.btnhome_feed)
        storage = FirebaseStorage.getInstance()
        storageReference = storage!!.reference
        val database = FirebaseDatabase.getInstance()
        mDatabase = database.getReference(Constants().DATABASE_PATH_UPLOADS)

        ///////homefeeds/////////


        mdatabasehome = database.getReference(Constants().homefeedfolder)

        btn_hfeed!!.setOnClickListener {
            sendfeed()
        }
        /////
        /////////testominal placement/////
        mdatabaseplacetesto=database.getReference(Constants().placementtesto_folder)
        et_testominal=v.findViewById(R.id.et_placement)
        btn_placement_testo=v.findViewById(R.id.btn_placement)

        btn_placement_testo!!.setOnClickListener {
            var et_testo=et_testominal!!.text.toString()
            if (et_testo.trim().isNotEmpty() && et_testo!!.contains("//")){
                val testominal=Placement_Testominals(et_testo)
                var uploadid=mdatabaseplacetesto!!.push().key
                mdatabaseplacetesto!!.child(uploadid!!).setValue(testominal)
                et_testominal!!.setText("")
                Toast.makeText(context,"uploaded",Toast.LENGTH_SHORT).show()


            }
            else {
                et_testominal!!.setError("please fill a valid url")
                et_testominal!!.requestFocus()
            }
        }


        //////////btnplacement records/////////////
        mdatabaseplacementrecord=database.getReference(Constants().placementrecords_folder)
        btn_placerecord!!.setOnClickListener {
            var et_year=et_placeyear!!.text.toString()
            var et_datasheet=et_placesheet!!.text.toString()
            var et_studimage=et_placestud!!.text.toString()
            if (validate(et_placeyear)&&validate(et_placesheet)&&validate(et_placestud)){
val placementrecord=PlacementRecordUpload(et_year,et_datasheet,et_studimage)

                var uploadid=mdatabaseplacementrecord!!.push().key
                mdatabaseplacementrecord!!.child(uploadid!!).setValue(placementrecord)
                et_placeyear!!.setText("")
                et_placesheet!!.setText("")
                et_placestud!!.setText("")
                Toast.makeText(context,"uploaded",Toast.LENGTH_SHORT).show()
            }

        }




        btn_select!!.setOnClickListener {
            SelectImage()
        }
        btn_upload!!.setOnClickListener {

            if(validate(img_name)&&validate(et_desc)) {
                uploadImage()
            }
        }

        btn_3year!!.setOnClickListener {

            var fragment_msg3: Fragment?

            fragment_msg3 = Fragment_msg_service3()
            val m=fragmentManager!!.beginTransaction()
            m.replace(R.id.frag1, fragment_msg3,"frag_msg3")
            m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            m.disallowAddToBackStack()
            m.commit()
        }



        return v
    }

    fun sendfeed(){
        var etfeed=et_homefeed!!.text.toString()
        var links=et_homelinks!!.text.toString()
        if(etfeed.trim().isNotEmpty() && links.trim().isNotEmpty()){
            val homeupload=HomeFeed(etfeed,links)

            val uploadid=mdatabasehome!!.push().key
            mdatabasehome!!.child(uploadid!!).setValue(homeupload)
            et_homefeed!!.setText("")
            et_homelinks!!.setText("")
            Toast.makeText(context,"uploaded",Toast.LENGTH_SHORT).show()

        }
        else{
          et_homefeed!!.error="Please enter some data"
            et_homelinks!!.error="please fill both fields"
            et_homefeed!!.requestFocus()
        }
    }

    private fun validate( editText: EditText?):Boolean{
        // check the lenght of the enter data in EditText and give error if its empty
        if (editText!!.text.toString().trim().isNotEmpty()) {
            return true // returs true if field is not empty
        }
        editText.error = "Please Fill This"
        editText.requestFocus()
        return false

    }




    private fun SelectImage() { // Defining Implicit Intent to mobile gallery
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

     val  pictureActionIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pictureActionIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(Intent.createChooser(pictureActionIntent, "Select Image from here..."), PICK_IMAGE_REQUEST)
    }
    override fun onActivityResult(
            requestCode: Int,
            resultCode: Int,
            data: Intent?
    ) {
        super.onActivityResult(
                requestCode,
                resultCode,
                data
        )
        // checking request code and result code
// if request code is PICK_IMAGE_REQUEST and
// resultCode is RESULT_OK
// then set image in the image view
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null && data.data != null) {
            filePath = data.data


            val file = File(getRealPathFromURI(filePath))
            var length: Long = file.length()
            length = length / 1024
          //  Toast.makeText(context,"$length kb",Toast.LENGTH_LONG).show()
           ///codedpath try catch is already there i used if(length<=100)///

           /* if ((length.toInt())<=100 && (length.toInt())!=0){*/
            if (filePath!=null){
                try {
                    val bitmap =
                        MediaStore.Images.Media.getBitmap(context!!.contentResolver, filePath)
                    img_view!!.setImageBitmap(bitmap)
                } catch (e: IOException) {

                    e.printStackTrace()
                }
            }
            else {
                img_view!!.setImageResource(R.drawable.imgnotvalid)
                Toast.makeText(context,"Please upload image less than or equal 100kb",Toast.LENGTH_LONG).show()
                filePath=null
            }


        }
    }


    private fun uploadImage() {


        if (filePath != null) { //displaying progress dialog while image is uploading
            val progressDialog = ProgressDialog(context)
            progressDialog.setTitle("Uploading")
            progressDialog.show()
            progressDialog.setCanceledOnTouchOutside(false)



            /*  val profileImageRef = FirebaseStorage.getInstance()
                  .getReference("uploads/" + editTextName!!.text.toString() + ".jpg")

              if (filePath != null) {
                  profileImageRef.putFile(filePath!!)
                      .addOnSuccessListener {
                          // profileImageUrl taskSnapshot.getDownloadUrl().toString(); //this is depreciated
                          //this is the new way to do it
                          profileImageRef.downloadUrl
                              .addOnCompleteListener { task ->
                                  val profileImageUrl = task.result.toString()
                                  Log.d("URL", profileImageUrl)
                              }
                      }
                      .addOnFailureListener { e ->

                          Toast.makeText(this, "aaa " + e.message, Toast.LENGTH_SHORT)
                              .show()
                      }
              }
  */



            //getting the storage reference

            var sRef = storageReference!!.child(Constants().STORAGE_PATH_UPLOADS + img_name!!.text.toString() + "." + getFileExtension(filePath
                    )
            )
            //adding the file to reference
            sRef.putFile(filePath!!)
                    .addOnSuccessListener { taskSnapshot ->


                        sRef.downloadUrl.addOnCompleteListener {
                            var profileImageUrl = it.result.toString()
                            Log.d("URL", profileImageUrl)
                            //creating the upload object to store uploaded image details

                            val sdf = SimpleDateFormat("dd-MM-yyyy hh:mm:ss aa")
                            val ctime: String = sdf.format(Date())
                            var imgdesc=et_desc?.text.toString()

                            val upload = Upload(
                                    img_name!!.text.toString().trim { it <= ' ' },
                                    // taskSnapshot.metadata?.reference?.downloadUrl.toString()
                                    profileImageUrl,ctime,imgdesc
                            )
                            //adding an upload to firebase database
                            val uploadId = mDatabase!!.push().key
                            mDatabase!!.child(uploadId!!).setValue(upload)


                        }


                        //dismissing the progress dialog
                        progressDialog.dismiss()

                        //displaying success toast
                        Toast.makeText(context, "File Uploaded ", Toast.LENGTH_LONG)
                                .show()


                    }
                    .addOnFailureListener { exception ->
                        progressDialog.dismiss()
                        Toast.makeText(
                                context,
                                exception.message,
                                Toast.LENGTH_LONG
                        ).show()
                    }
                    .addOnProgressListener { taskSnapshot ->
                        //displaying the upload progress
                        val progress =
                                100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount
                        progressDialog.setMessage("Uploaded " + progress.toInt() + "%...")
                    }
        } else { //display an error if no file is selected

            Toast.makeText(context,"Image size not valid",Toast.LENGTH_LONG).show()
        }
    }











    // Creating Method to get the selected image file Extension from File Path URI.
    fun getFileExtension(uri: Uri?): String? {
        val cR = context!!.contentResolver
        val mime = MimeTypeMap.getSingleton()
        return mime.getExtensionFromMimeType(cR.getType(uri!!))
    }


    /////for image path////
    fun getRealPathFromURI(uri: Uri?): String? {
        val cursor: Cursor? = context!!.getContentResolver().query(uri!!, null, null, null, null)
        cursor!!.moveToFirst()
        val idx: Int = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

}

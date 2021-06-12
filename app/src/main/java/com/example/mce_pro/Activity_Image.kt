package com.example.mce_pro


import android.app.ProgressDialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.opengl.Visibility
import android.os.AsyncTask
import android.os.Bundle
import android.os.Environment
import android.os.Handler
import android.text.Html
import android.text.util.Linkify
import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import java.io.*
import java.net.HttpURLConnection
import java.net.URL


class Activity_Image : AppCompatActivity() {
    var webviewmages:WebView?=null
    companion object{
        var txt:String?=null
        var tit:String?=null
        var im:String?=null
        var desc:String?=null
    }
    var progressDialog:ProgressDialog?=null
    var mHandler: Handler? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_image)
        var download=findViewById<ImageView>(R.id.download)
        var d=findViewById<TextView>(R.id.description)
        var img=findViewById<ImageView>(R.id.exe_img)
        var t=findViewById<TextView>(R.id.tv_time)



        var title=findViewById<TextView>(R.id.tv_title)
        var bundle =intent

         txt = bundle.getStringExtra("ctime") as String
        tit=bundle.getStringExtra("name")as String
        im=bundle?.getStringExtra("image")as String
        desc= bundle.getStringExtra("desc") as String
        Glide.with(this@Activity_Image).load(im).into(img)

        t.text = txt
        d.text=Html.fromHtml(desc)
        Linkify.addLinks(d!!, Linkify.WEB_URLS or Linkify.PHONE_NUMBERS)
        Linkify.addLinks(d!!, Linkify.ALL );

        title.text=tit
        var imgback=findViewById<ImageView>(R.id.back_img)
        imgback.setOnClickListener {
            finish()
        }




        download.setOnClickListener {

            var asynctask=Imagedownload()
            var imageName:String= im as String
             var url:URL=URL(imageName)

            asynctask.execute(url)
          /*  val downloadManager =
                    this.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri: Uri = Uri.parse(imageName)
            val request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val reference = downloadManager.enqueue(request)
            if (reference.toInt()>0){

                Toast.makeText(this,"download completed", Toast.LENGTH_SHORT).show()
            }*/

        }

    }
inner class Imagedownload: AsyncTask<URL, String, Bitmap>() {






    override fun onPreExecute() {
        super.onPreExecute()
progressDialog= ProgressDialog(this@Activity_Image)
        progressDialog!!.setTitle("downloading...")
        progressDialog!!.setMessage("please wait")
        progressDialog!!.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
        progressDialog!!.isIndeterminate = true
        progressDialog!!.progress = 50
        progressDialog!!.show()
        progressDialog!!.setCanceledOnTouchOutside(false)


    }
    override fun doInBackground(vararg p0: URL?): Bitmap? {
        val url = p0.get(0)

        var connection: HttpURLConnection? = null
        try {
            connection = url!!.openConnection() as HttpURLConnection
            connection.connect()
            val inputStream = connection.inputStream
            val bufferedInputStream = BufferedInputStream(inputStream)




            return BitmapFactory.decodeStream(bufferedInputStream)
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPostExecute(result: Bitmap?) {
        super.onPostExecute(result)

        if (result != null) {
            saveImage(result)
        }
        progressDialog!!.dismiss()
            Toast.makeText(applicationContext,"picture saved to Pictures/Mce/...",Toast.LENGTH_LONG).show()

    }
    fun saveImage(result: Bitmap?) {
        val createFolder = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Mce")
        createFolder.mkdir()
        val saveImage = File(createFolder, tit+".jpg")
        try {
            val outputStream: OutputStream = FileOutputStream(saveImage)
            result!!.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: FileNotFoundException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }


}



    }


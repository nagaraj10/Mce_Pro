package com.example.mce_pro







import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.ViewFlipper
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.database.*



class Fragment_A : Fragment() {

    ////animation///

    var imageView: ImageView? = null


    var animMove: Animation? = null
    ///




    ///recruit images
    var recruitimage= intArrayOf(R.drawable.one,R.drawable.two,R.drawable.three,R.drawable.four,R.drawable.six,R.drawable.five)





var recyclerview_recruit:RecyclerView?=null
    var viewFlipper: ViewFlipper?=null
    var tv_marque:TextView?=null
    var tv_download: TextView?=null
    var btn_readmore:Button?=null
    var recyc_testominal:RecyclerView?=null
    var swipeRecruit=Swipe_recruit(context,recruitimage)
////////dataclasses///
    private var uploads: MutableList<HomeFeed>? = null

    private var upload_testo:MutableList<Placement_Testominals>?=null
    ////////////////////
    var mDatabase: DatabaseReference? = null

    var recychome:RecyclerView?=null




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
         val v:View=inflater.inflate(R.layout.fragment_a,container,false)


        viewFlipper=v!!.findViewById(R.id.vf)
        btn_readmore=v!!.findViewById(R.id.btn_readmore)
        tv_marque=v!!.findViewById(R.id.tv_marque)
        tv_marque!!.isSelected = true
        var txt="<font color=#cc0029> << </font>welcome to meenakshi college of enginnering <font color=#cc0029> << </font>we provide 100 percent placement<font color=#cc0029> << </font>college of excellence."
        tv_marque!!.text=Html.fromHtml(txt)
        val imgAnimationOut: Animation =
            AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left)
        imgAnimationOut.duration = 700
        viewFlipper!!.outAnimation = imgAnimationOut
        viewFlipper!!.flipInterval = 3000

        val imgAnimationIn: Animation =
            AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right)
        imgAnimationIn.duration = 700
        viewFlipper!!.inAnimation = imgAnimationIn
        viewFlipper!!.flipInterval = 3000


        viewFlipper!!.startFlipping()

        btn_readmore!!.setOnClickListener {

           val i=Intent(context,Activity_readmore::class.java)
            startActivity(i)

        }

        /////animation
        imageView = v!!.findViewById(R.id.imgmove) as ImageView
        Glide.with(context).load(R.drawable.gif1).into(imageView);



////////


        ////recyc homefeed///
     /*   var recychfeed=v.findViewById<RecyclerView>(R.id.recyc_feeds)
        var linearmanager=LinearLayoutManager(context)
        linearmanager.orientation=LinearLayoutManager.HORIZONTAL
        recychfeed.layoutManager=linearmanager*/


        recychome=v!!.findViewById<RecyclerView>(R.id.recyc_homefeed)
        var linearmanager_vertical=LinearLayoutManager(context)
        linearmanager_vertical.orientation=LinearLayoutManager.VERTICAL
        recychome!!.layoutManager=linearmanager_vertical




        val database = FirebaseDatabase.getInstance()
        uploads = ArrayList()
        mDatabase = database.getReference(Constants().homefeedfolder)
        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
                for (postSnapshot in snapshot.children){
                    val upload = postSnapshot.getValue(HomeFeed::class.java)!!
                    uploads!!.add(upload)
                }
              //  recychfeed.adapter=Adapter_homefeed(context,uploads as ArrayList<HomeFeed>)
                recychome!!.adapter=Adapter_homefeed(context,uploads as ArrayList<HomeFeed>)
            }

        })
        autoscrollhome()
        testominalscroll()




/////////recyc_testominal///////////////////////
        recyc_testominal=v.findViewById(R.id.recyc_testominal)
        var linear=LinearLayoutManager(context)
        linear.orientation=LinearLayoutManager.HORIZONTAL
        recyc_testominal!!.layoutManager=linear
        var radius = getResources().getDimensionPixelSize(R.dimen.radius);
       var dotsHeight = getResources().getDimensionPixelSize(R.dimen.dots_height);
      val color = ContextCompat.getColor(context!!, R.color.grey);
        recyc_testominal!!.addItemDecoration(DotsIndicatorDecoration(radius,radius*4,dotsHeight,color,color))
        PagerSnapHelper().attachToRecyclerView(recyc_testominal)

        upload_testo=ArrayList()

        mDatabase=database.getReference(Constants().placementtesto_folder)
        mDatabase!!.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {

            }

            override fun onDataChange(snapshot: DataSnapshot) {
               for (p in snapshot.children){
                   val test=p.getValue(Placement_Testominals::class.java)
                   upload_testo!!.add(test!!)
               }
                recyc_testominal!!.adapter=Adapter_testominal(context,upload_testo as ArrayList<Placement_Testominals>)
            }
        })

// //////////////////////////////////////////





/*        tv_download!!.setOnClickListener {
            var imageName:String="https://encrypted-tbn0.gstatic.com/images?q=tbn%3AANd9GcQf9EdFoZa6eeADrcJwIgZKQW_rqYMnbxNH12H1tjfjSB2EsZIW"
            val downloadManager =
                v.context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            val uri: Uri = Uri.parse(imageName)
            val request = DownloadManager.Request(uri)
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            val reference = downloadManager.enqueue(request)
            if (reference.toInt()>0){

                Toast.makeText(context,"download completed",Toast.LENGTH_SHORT).show()
            }

        }*/



////////////////////////////////////////////////////////////
        recyclerview_recruit=v!!.findViewById(R.id.recyclerv_rectriut)
        var lm:LinearLayoutManager= object : LinearLayoutManager(context) {
            override fun smoothScrollToPosition(recyclerView: RecyclerView?, state: RecyclerView.State?, position: Int) {
                try{
                val smoothScroller: LinearSmoothScroller = object : LinearSmoothScroller(context) {
                    private val SPEED = 4000f // Change this                value (default=25f)
                    override fun calculateSpeedPerPixel(displayMetrics: DisplayMetrics): Float {
                        return SPEED / displayMetrics.densityDpi
                    }
                }
                smoothScroller.targetPosition = position
                startSmoothScroll(smoothScroller)
            } catch (e:Exception) {
                    e.printStackTrace()
                }
            }

        }
        autoscrollanother()
        lm.orientation=(LinearLayoutManager.HORIZONTAL)
        recyclerview_recruit!!.layoutManager=lm
  /*      recyclerview_recruit!!.smoothScrollToPosition(-1)*/

        recyclerview_recruit!!.adapter=swipeRecruit




//...



//when you want to start the counting start the thread bellow.
        //PG COUNT
        var counter = 0
        val pgtotal = 10 // the total number

        var pgcount=v!!.findViewById<TextView>(R.id.pgcount)
        Thread(Runnable {
            while (counter < pgtotal) {
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) { // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                pgcount.post(Runnable { pgcount.setText("" + counter) })
                counter++
            }
        }).start()

        //UG COUNT/////

        val ugtotal = 22 // the total number
        var counter1=0

        var ugcount=v!!.findViewById<TextView>(R.id.ugcount)
        Thread(Runnable {
            while (counter1 < ugtotal) {
                try {
                    Thread.sleep(200)
                } catch (e: InterruptedException) { // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                ugcount.post(Runnable { ugcount.setText("" + counter1) })
                counter1++
            }
        }).start()
        //ACADEMIC COUNT/////////////

        val academictotal = 256 // the total number
        var counter3=0

        var academiccount=v!!.findViewById<TextView>(R.id.facultycount)
        Thread(Runnable {
            while (counter3 < academictotal) {
                try {
                    Thread.sleep(20)
                } catch (e: InterruptedException) { // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                academiccount.post(Runnable { academiccount.setText("+" + counter3) })
                counter3++
            }
        }).start()

        //STUDENTS COUNT/////////////

        val studentstotal = 1281// the total number
        var counter4=900

        var studentscount=v!!.findViewById<TextView>(R.id.studentscount)
        Thread(Runnable {
            while (counter4 < studentstotal) {
                try {
                    Thread.sleep(20)
                } catch (e: InterruptedException) { // TODO Auto-generated catch block
                    e.printStackTrace()
                }
                studentscount.post(Runnable { studentscount.setText("+" + counter4) })
                counter4++

            }
        }).start()










        return v


    }


    fun autoscrollanother(){
        var position=-1
        var c=0

            val handler = Handler()
            val runnable = object : Runnable {
                override fun run() {
                    if(c==0||c<15){

                    recyclerview_recruit!!.smoothScrollToPosition(c++)
                    handler.postDelayed(this, 2000);
                    Log.d("count", c.toString())}

                else if(c==15){
                    recyclerview_recruit!!.smoothScrollToPosition(0)
                        handler.postDelayed(this, 10);
                        c=-13


                    Log.d("count1", c.toString())
                }

                }

            }

            handler.postDelayed(runnable, 2000);


    }


    ////latest news///

    fun autoscrollhome(){
        var a=10

        var b=0

        val handler = Handler()
        val runnable = object : Runnable {
            override fun run() {
                if(b==0||b<a){

                    recychome!!.smoothScrollToPosition(b++)
                    handler.postDelayed(this, 2000);
                    Log.d("count", b.toString())}

                else if(b==a){
                    recychome!!.smoothScrollToPosition(0)
                    handler.postDelayed(this, 10);

                    b=0



                    Log.d("count1", b.toString())
                }

            }

        }

        handler.postDelayed(runnable, 2000);



    }

    ////


    /////placement testominal////
fun testominalscroll(){
        var a=10
        var b=0
        val handler=Handler()
        val runnable= object : Runnable {
            override fun run() {
                if(b==0||b<a){
                    recyc_testominal!!.smoothScrollToPosition(b++)
                    handler.postDelayed(this,2000)
                }
                else if(b==a){
                    recyc_testominal!!.smoothScrollToPosition(0)
                    handler.postDelayed(this,10)
                    b=0
                }
            }
        }
        handler.postDelayed(runnable,2000)
    }


    ////////end of animations////










}

package com.example.mce_pro




import android.app.Dialog
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.bumptech.glide.Glide
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.auth.api.signin.GoogleSignInResult
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.material.navigation.NavigationView
import com.google.android.material.navigation.NavigationView.OnNavigationItemSelectedListener
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.profile_dialog.*


class MainActivity : AppCompatActivity(), OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {

    var drawer:DrawerLayout?=null
    var navigationview:NavigationView?= null
    var toolbar:Toolbar?=null
    var tool_image:ImageView?=null
    private var doubleBackToExitPressedOnce = false
    var coordinatorLayout: CoordinatorLayout? = null

    //////for google signin////
    private var googleApiClient: GoogleApiClient? = null
    private var gso: GoogleSignInOptions? = null

    ////////////////////////
    private var pendingIntent: PendingIntent? = null
    //////////////////////

    //Defined the required values
    companion object {
        const val CHANNEL_ID = "naga"
        private const val CHANNEL_NAME= "Naga notification"
        private const val CHANNEL_DESC = "Just a notification"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawer=findViewById(R.id.drawer_layout)


        navigationview=findViewById(R.id.nav_view)
        toolbar = findViewById(R.id.toolbar)
        tool_image=findViewById(R.id.tool_eimage)

        tool_image!!.setOnClickListener {
            var   dialog: Dialog = Dialog(this);
            dialog.setContentView(R.layout.profile_dialog);
            dialog.getWindow()!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            var sp=Shared_pref(this)
            dialog.pname.text=sp.getname()
            dialog.pemail.text=sp.getemail()
            Log.d("photo",sp.getphoto())
            Glide.with(this).load(sp.getphoto()).into(dialog.profileImage)
            dialog.puserId.text=sp.getid()
            dialog.btn_logout.setOnClickListener {
                Signin.mGoogleSignInClient!!.signOut().addOnCompleteListener {

                    val preferences: SharedPreferences =
                        getSharedPreferences("LoginDetails", Context.MODE_PRIVATE)
                    val editor: SharedPreferences.Editor = preferences.edit()
                    editor.clear()
                    editor.apply()
                    val i=Intent(this,Signin::class.java)
                    startActivity(i)
                    finish()
                }
            }
            dialog.show()

        }



        setSupportActionBar(toolbar)




        val toggle = ActionBarDrawerToggle(
            this, drawer, toolbar, R.string.navigation_open,
            R.string.navigation_close
        )
        drawer!!.addDrawerListener(toggle)
        toggle.syncState()

        navigationview!!.setNavigationItemSelectedListener(this)

        navigationview!!.getMenu().getItem(0).setChecked(true);



        var fragmentManager = supportFragmentManager
        var fragment_login: Fragment?

        ////notification checking/////
     /*   val menuFragment = intent.getStringExtra("menuFragment")
if (menuFragment!=null){
Log.d("nagaraj",menuFragment)}

        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // If menuFragment is defined, then this activity was launched with a fragment selection


            // Here we can decide what do to -- perhaps load other parameters from the intent extras such as IDs, etc
            if (menuFragment == "favoritesMenuItem") {
                val community_frag = Community()
                fragmentTransaction.replace(R.id.frag1, community_frag)
            }
*/

/////////////////////////////////notification ends////////////////////////////////////////////////////////

        fragment_login = Fragment_A()

        val transaction =
            fragmentManager.beginTransaction()
        transaction.replace(R.id.frag1, fragment_login!!, "frag_A")
        transaction.commit()




        ///////fb////
        FirebaseInstanceId.getInstance().instanceId
            .addOnCompleteListener(OnCompleteListener { task ->


                if (!task.isSuccessful) {

                    return@OnCompleteListener
                }

                var token = task.result?.token

                Log.d("Token", token)

            })


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setShowBadge(true)
            channel.description = CHANNEL_DESC
            val manager = getSystemService(NotificationManager::class.java)


            manager.createNotificationChannel(channel)
        }


        ////////this is for google signin

        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()

        googleApiClient = GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso!!)


                .build()





    }





    override fun onStart() {
        super.onStart()
        val opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient)
        if (opr.isDone) {
            val result = opr.get()
            handleSignInResult(result)
        } else {
            opr.setResultCallback { googleSignInResult -> handleSignInResult(googleSignInResult) }
        }
    }



    private fun handleSignInResult(result: GoogleSignInResult) {
        if (result.isSuccess) {
            val account = result.signInAccount
            var photo=account!!.photoUrl.toString()
            if (account.photoUrl==null){
            //   photo="https://images.squarespace-cdn.com/content/v1/5cc676d094d71a1106862fa2/1559101839166-6XM7K7IBYJ2E2ED0O6O5/ke17ZwdGBToddI8pDm48kAbkE_QvkITry6ehWLqg87BZw-zPPgdn4jUwVcJE1ZvWEtT5uBSRWt4vQZAgTJucoTqqXjS3CfNDSuuf31e0tVGOWU68gZ21o4YUOpVtb6Tzv-94tBL9rMsvKTpB_0IYYmbSd6kfRtgWHgNMDgGnmDY/james.jpeg"
            photo="https://cdn1.iconfinder.com/data/icons/website-internet/48/website_-_male_user-512.png"
            }
            var name=account!!.displayName.toString()
            var email=account!!.email.toString()
            var id=account!!.id.toString()



          var sharepref=Shared_pref(applicationContext)

            sharepref.savelogindetails(photo!!,name!!,email!!,id!!)
            try {

                Glide.with(this).load(photo).into(tool_image)
            } catch (e: NullPointerException) {
                Toast.makeText(applicationContext, "image not found", Toast.LENGTH_LONG).show()
            }
        } else {
            Toast.makeText(this,"error make occurs during signin",Toast.LENGTH_SHORT).show()
        }
    }

////////end of google signin/////

    override fun onBackPressed() {
        coordinatorLayout=  findViewById(R.id.coordinatelayout);
        if (drawer!!.isDrawerOpen(GravityCompat.START)){
            drawer!!.closeDrawer(GravityCompat.START)
        }
        else{
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed()
                return
            }

            this.doubleBackToExitPressedOnce = true
            Snackbar.make(coordinatorLayout!!,"Please click BACK again to exit",Snackbar.LENGTH_SHORT).show()


            Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)}
    }



    var fragmentManager = supportFragmentManager
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var id = item.itemId
        if (id == R.id.Home) {



            fragmentManager!!.findFragmentById(R.id.frag1)?.let { it1 ->
                fragmentManager!!.beginTransaction().remove(
                    it1
                ).commit()
            }




            var fragment_login: Fragment?

            fragment_login = Fragment_A()

            val transaction =
                fragmentManager.beginTransaction()
            transaction.add(R.id.frag1, fragment_login!!, "fragA")
            transaction.commit()




        }
        else if (id==R.id.Events){


            var fragmentManager = supportFragmentManager
            fragmentManager!!.findFragmentById(R.id.frag1)?.let { it1 ->
                fragmentManager!!.beginTransaction().remove(
                    it1
                ).commit()
            }



            var fragment_login: Fragment?

            fragment_login = Fragment_Events()

            val transaction =
                fragmentManager.beginTransaction()
            transaction.add(R.id.frag1, fragment_login!!, "frag3")
            transaction.commit()

        }

        else if (id==R.id.management){
        val i=Intent(this,Management_Activity::class.java)
            startActivity(i)
        }

        else if (id==R.id.placement){
            val fragmentmanager =  supportFragmentManager
            var fragment =  fragmentmanager.findFragmentById(R.id.frag1)
            if (fragment != null) supportFragmentManager.beginTransaction().remove(fragment)
                .commit()
            val m=fragmentmanager!!.beginTransaction()
            m.replace(R.id.frag1,Fragment_Placement(),"Frag_placement")
            m.commit()


        }


        else if (id==R.id.Admin){
            var fragmentManager = supportFragmentManager
            fragmentManager!!.findFragmentById(R.id.frag1)?.let { it1 ->
                fragmentManager!!.beginTransaction().remove(
                    it1
                ).commit()
            }
            /////



            val m=fragmentManager!!.beginTransaction()
            m.replace(R.id.frag1,Fragment_Admin(),"frag_admin")
            m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            m.commit()

        }
        else if (id==R.id.portal1){
            var fragmentmanager=supportFragmentManager
            fragmentmanager!!.findFragmentById(R.id.frag1)?.let {
                it ->fragmentmanager!!.beginTransaction().remove(it).commit()
            }
            val m=fragmentmanager!!.beginTransaction()
            m.replace(R.id.frag1,Fragmentweb(),"frag_web")
            m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }



        else if (id==R.id.portal2){
            var fragmentmanager=supportFragmentManager
            fragmentmanager!!.findFragmentById(R.id.frag1)?.let {
                    it ->fragmentmanager!!.beginTransaction().remove(it).commit()
            }
            val m=fragmentmanager!!.beginTransaction()
            m.replace(R.id.frag1,FragmentWeb_Portal2(),"frag_webportal2")
            m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }



        else if (id==R.id.community){
            var fragmentmanager=supportFragmentManager
            fragmentmanager!!.findFragmentById(R.id.frag1)?.let {
                    it ->fragmentmanager!!.beginTransaction().remove(it).commit()
            }
            val m=fragmentmanager!!.beginTransaction()
            m.replace(R.id.frag1,Community(),"frag_community")
            m.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()

        }

        else if (id==R.id.more){
            val i=Intent(this,Webview2::class.java)
            startActivity(i)
        }
        else if (id==R.id.contact){


            val intent = Intent(Intent.ACTION_DIAL)
            intent.data = Uri.parse("tel:04423643961")
            startActivity(intent)

        }

       
        val drawer: DrawerLayout = findViewById(R.id.drawer_layout)
        drawer.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        Toast.makeText(applicationContext,"Thank you for using our app",Toast.LENGTH_SHORT).show()
    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }



}

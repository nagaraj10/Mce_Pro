package com.example.mce_pro


import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.tasks.Task


class Signin : AppCompatActivity(),GoogleApiClient.OnConnectionFailedListener {
    companion object {


        var signInButton: Button? = null

        var gso: GoogleSignInOptions? = null
        var mGoogleSignInClient: GoogleSignInClient? = null
        private val RC_SIGN_IN = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        RequestMultiplePermission()
        signInButton=findViewById(R.id.sign_in_button);


        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build()
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
signInButton!!.setOnClickListener {
    signIn()
}





    }

    override fun onConnectionFailed(p0: ConnectionResult) {

    }
    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }

    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            updateUI(account)
        } catch (e: ApiException) { // The ApiException status code indicates the detailed failure reason.
// Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("show error", "signInResult:failed code=" + e.statusCode)
            updateUI(null)
        }
    }


    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        updateUI(account)

    }

    fun updateUI(googleSignInClient: GoogleSignInAccount?){
        if(googleSignInClient!=null){
            var i=Intent(this,MainActivity::class.java)
            startActivity(i)
            finish()
        }

    }
     fun RequestMultiplePermission() { // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA,Manifest.permission.CALL_PHONE,Manifest.permission.READ_CALL_LOG, Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.ACCESS_FINE_LOCATION,Manifest.permission.ACCESS_COARSE_LOCATION), 6
        )
    }
}

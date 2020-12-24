package com.taiyilin.mandarinlearning.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.observe
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.snackbar.Snackbar.make
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.taiyilin.mandarinlearning.MainActivity
import com.taiyilin.mandarinlearning.R
import com.taiyilin.mandarinlearning.databinding.ActivityLogInBinding
import com.taiyilin.mandarinlearning.ext.getVmFactory
import com.taiyilin.mandarinlearning.main.home.HomeViewModel
import com.taiyilin.mandarinlearning.pickRole.PickRoleActivity
import com.taiyilin.mandarinlearning.util.Logger


private const val RC_SIGN_IN = 20
class LogInActivity : AppCompatActivity() {
    private val TAG = this.javaClass.name
    private lateinit var binding: ActivityLogInBinding

    // FirebaseAuth
    private lateinit var auth: FirebaseAuth

    val viewModel by viewModels<LogInViewModel>{getVmFactory()}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_log_in)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        // Initialize Firebase Auth
        auth = Firebase.auth
        // Configure Google Sign In
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        // Build a GoogleSignInClient with the options specified by gso.
        val mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        binding.signInButton.setOnClickListener{
            signIn(mGoogleSignInClient)

        }


        viewModel.intentToPickType.observe(this, Observer {
            it?.let {
                if (it){
                    startActivity(Intent(this, PickRoleActivity::class.java))
                }
            }
        })


    }

    private fun signIn(mGoogleSignInClient: GoogleSignInClient) {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent,
            RC_SIGN_IN
        )
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Logger.d("firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential).addOnCompleteListener(this) { task ->
            if (task.isSuccessful) {
                // Sign in success, intent to main activity with the signed-in user's information
                Logger.d( "signInWithCredential:success")
                val user = auth.currentUser
                Logger.d("user = $user")
                if (user != null) {
                    UserManager.userUID = user.uid
                    UserManager.userName = user.displayName
                }
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                // If sign in fails, display a message to the user.
                Toast.makeText(this, "Authentication Failed.", Toast.LENGTH_SHORT).show()
                Log.w(TAG,"signInWithCredential:failure", task.exception)
            }
        }
    }
}
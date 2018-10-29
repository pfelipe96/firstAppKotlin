package com.example.paulo.appgetfood.LoginPackage

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.paulo.appgetfood.BR
import com.example.paulo.appgetfood.HomePackage.HomeActivity
import com.example.paulo.appgetfood.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider


class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mGetValueFirebase : LoginViewModel.OnResponseLogin
    lateinit var mCallbackManager: CallbackManager

    companion object {
        private const val sRC_SIGN_IN = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener  = FirebaseAuth.AuthStateListener { it ->
            val user : FirebaseUser? = it.currentUser
            user.let {
                Log.v("Entrou", "Entrou ${it?.uid}")
            }
        }

        val loginActivityViewModel = LoginViewModel(this, mAuth, this@LoginActivity)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_login)
        binding.setVariable(BR.viewModel, loginActivityViewModel)

        mGetValueFirebase = object : LoginViewModel.OnResponseLogin{
            override fun sendAuthFirebaseSingUp(email: String, password: String) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this@LoginActivity
                ) { task ->
                    if(task.isSuccessful){
                        Log.v("Sucess", "ok")
                    }else{
                        Log.v("Failed", "bad")
                    }
                }

            }

            override fun sendAuthFacebookSingUp(view: View) {
                LoginManager.getInstance().logInWithReadPermissions(this@LoginActivity, listOf("email", "public_profile"))
            }

            override fun sendAuthGoogleSingUp(view: View) {
                val mGso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.default_web_client_id))
                        .requestEmail()
                        .build()

                val mGoogleSignInClient = GoogleSignIn.getClient(this@LoginActivity, mGso)
                val signIntent = mGoogleSignInClient.signInIntent
                startActivityForResult(signIntent, sRC_SIGN_IN)
            }
        }

        loginActivityViewModel.initializeListener(mGetValueFirebase)

        mCallbackManager = CallbackManager.Factory.create()

        LoginManager.getInstance().registerCallback(mCallbackManager, object: FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                handleFacebookAcessToken(result!!.accessToken)
                Log.v("Sucess", "Facebook")
            }

            override fun onCancel() {
                Log.v("Cancel", "Facebook")

            }

            override fun onError(error: FacebookException?) {
                Log.v("Failed", error!!.message)
            }

        })


    }

    fun handleFacebookAcessToken(token: AccessToken){
        val credential = FacebookAuthProvider.getCredential(token.token)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this@LoginActivity
        ) { task ->
            if(task.isSuccessful){
                Log.v("Sucess", task.exception?.message)
            }else{
                Log.v("Failed", task.exception?.message)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == sRC_SIGN_IN){
            val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
            if(result.isSuccess) {
                val account = result.signInAccount as GoogleSignInAccount
                firebaseAuthWithGoogle(account)
            }else{
                Log.v("Failed", "bad")
            }
        }else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data)
        }

    }

    fun firebaseAuthWithGoogle(account: GoogleSignInAccount){
        Log.v("WithGoogle", account!!.id )

        val credential = GoogleAuthProvider.getCredential(account.id, null)
        mAuth.signInWithCredential(credential).addOnCompleteListener(this

        ) { task ->
            if(task.isSuccessful){
                Log.v("Sucess", task.exception?.message)
            }else{
                Log.v("Failed", task.exception?.message)
            }
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
        }
    }

    override fun onStart() {
        super.onStart()
        mAuth.addAuthStateListener { mAuthListener }
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener { mAuthListener }
    }

}
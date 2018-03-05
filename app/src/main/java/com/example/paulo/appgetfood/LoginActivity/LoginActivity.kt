package com.example.paulo.appgetfood.LoginActivity

import android.content.Intent
import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.JsonToken
import android.util.Log
import android.view.View
import com.example.paulo.appgetfood.BR
import com.example.paulo.appgetfood.R
import com.facebook.AccessToken
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mGetValueFirebase : LoginActivityViewModel.OnResponseLogin
    lateinit var mCallbackManager: CallbackManager


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        FirebaseApp.initializeApp(this)

        mAuth = FirebaseAuth.getInstance()
        mAuthListener  = FirebaseAuth.AuthStateListener {
            var user : FirebaseUser = it.currentUser!!
            user?.let {
                Log.v("Entrou", "Entrou "+user.uid)
            }
        }

        val loginActivityViewModel = LoginActivityViewModel(this, mAuth, this@LoginActivity)
        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_login)
        binding.setVariable(BR.viewModel, loginActivityViewModel)

        mGetValueFirebase = object : LoginActivityViewModel.OnResponseLogin{
            override fun sendAuthFirebaseSingUp(email: String, password: String) {
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this@LoginActivity,
                        { task ->
                            if(task.isSuccessful){
                                Log.v("Sucess", "ok")
                            }else{
                                Log.v("Failed", "bad")
                            }
                        })

            }

            override fun sendAuthFacebookSingUp(view: View) {
                LoginManager.getInstance().logInWithReadPermissions(this@LoginActivity, listOf("email", "public_profile"))
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
        mAuth.signInWithCredential(credential).addOnCompleteListener(this@LoginActivity,
                {task ->
                    if(task.isSuccessful){
                        Log.v("Sucess", "ok")
                    }else{
                        Log.v("Failed", "bad")
                    }
                }
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mCallbackManager!!.onActivityResult(requestCode, resultCode, data)
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
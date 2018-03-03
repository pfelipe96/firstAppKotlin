package com.example.paulo.appgetfood.LoginActivity

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.paulo.appgetfood.BR
import com.example.paulo.appgetfood.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    lateinit var mAuth: FirebaseAuth
    lateinit var mAuthListener: FirebaseAuth.AuthStateListener
    lateinit var mGetValueFirebase : LoginActivityViewModel.OnResponseLogin


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

        val loginActivityViewModel = LoginActivityViewModel(this, mAuth)

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
        }

        loginActivityViewModel.initializeListener(mGetValueFirebase)
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
package com.example.paulo.appgetfood.LoginPackage

import android.app.Activity
import android.content.Context
import android.view.View
import android.databinding.ObservableField
import android.os.Build
import android.support.annotation.RequiresApi
import android.text.TextWatcher
import android.text.Editable
import com.google.firebase.auth.FirebaseAuth
import java.util.*
import android.content.Intent
import com.example.paulo.appgetfood.SignUpPackage.SingUpAcitivty


/**
 * Created by paulo on 25/02/18.
 */
class LoginViewModel(var mContext: Context, var mAuth : FirebaseAuth, var activity: Activity){

    var email:ObservableField<String> = ObservableField()
    var password:ObservableField<String> = ObservableField()
    lateinit var mOnResponse: OnResponseLogin


    fun onButtonClick(view : View){
        if(!email.get().isNullOrEmpty() && !password.get().isNullOrEmpty())
            email.get()?.let { email -> password.get()?.let { password -> mOnResponse.sendAuthFirebaseSingUp(email, password) } }
    }


    fun onButtonClickSendRegister(view : View){
       mContext.startActivity(Intent(mContext, SingUpAcitivty::class.java))
    }

    fun onButtonClickFacebook(view : View){
        mOnResponse.sendAuthFacebookSingUp(view)
    }

    fun onButtonClickGoogle(view : View){
        mOnResponse.sendAuthGoogleSingUp(view)
    }

    var watcherEmail: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun afterTextChanged(s: Editable) {
            if (!Objects.equals(email.get(), s.toString())) {
                email.set(s.toString())
            }
        }
    }

    var watcherPassword: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun afterTextChanged(s: Editable) {
            if (!Objects.equals(password.get(), s.toString())) {
                password.set(s.toString())
            }
        }
    }


    interface OnResponseLogin {
        fun sendAuthFirebaseSingUp(email: String, password: String)
        fun sendAuthFacebookSingUp(view: View)
        fun sendAuthGoogleSingUp(view: View)
    }


    fun initializeListener(onResponse: OnResponseLogin){
        mOnResponse = onResponse
    }
}




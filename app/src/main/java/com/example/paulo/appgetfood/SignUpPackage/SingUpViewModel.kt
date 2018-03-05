package com.example.paulo.appgetfood.SignUpPackage

import android.app.Activity
import android.content.Context
import android.databinding.ObservableField
import android.os.Build
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import java.util.*


/**
 * Created by paulo on 02/03/18.
 */
class SingUpViewModel(var activity: Activity, var context: Context){
    lateinit var mOnResponse: OnResponseSingUp

    var emailRegister: ObservableField<String> = ObservableField()
    var passwordRegister: ObservableField<String> = ObservableField()
    var firstNameRegister: ObservableField<String> = ObservableField()
    var lastNameRegister: ObservableField<String> = ObservableField()
    var phoneRegister: ObservableField<String> = ObservableField()

    var watcherEmailRegister: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun afterTextChanged(s: Editable) {
            if (!Objects.equals(emailRegister.get(), s.toString())) {
                emailRegister.set(s.toString())
            }
        }
    }
    var watcherPasswordRegister: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun afterTextChanged(s: Editable) {
            if (!Objects.equals(passwordRegister.get(), s.toString())) {
                passwordRegister.set(s.toString())
            }
        }
    }
    var watcherFirstNameRegister: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun afterTextChanged(s: Editable) {
            if (!Objects.equals(firstNameRegister.get(), s.toString())) {
                firstNameRegister.set(s.toString())
            }
        }
    }
    var watcherLastNameRegister: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun afterTextChanged(s: Editable) {
            if (!Objects.equals(lastNameRegister.get(), s.toString())) {
                lastNameRegister.set(s.toString())
            }
        }
    }
    var watcherPhoneRegister: TextWatcher = object : TextWatcher {
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        @RequiresApi(Build.VERSION_CODES.KITKAT)
        override fun afterTextChanged(s: Editable) {
            if (!Objects.equals(phoneRegister.get(), s.toString())) {
                phoneRegister.set(s.toString())
            }
        }
    }


    fun onButtonRegisterClick(view : View){
        if(!emailRegister!!.get().isNullOrEmpty() && !passwordRegister!!.get().isNullOrEmpty())
            mOnResponse.sendAuthFirebaseCreateUser(emailRegister.get(), passwordRegister.get())
    }

    interface OnResponseSingUp{
        fun sendAuthFirebaseCreateUser(email : String, password: String)
    }

    fun initializeListener(onResponse: OnResponseSingUp){
        mOnResponse = onResponse
    }

    fun setToolbar(appCompatActivity: AppCompatActivity, toolBar: android.support.v7.widget.Toolbar){
        appCompatActivity.setSupportActionBar(toolBar)
//        appCompatActivity.supportActionBar.setDefaultDisplayHomeAsUpEnabled(true)
    }


}
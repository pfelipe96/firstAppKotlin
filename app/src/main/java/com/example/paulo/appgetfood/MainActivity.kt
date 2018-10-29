package com.example.paulo.appgetfood

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.paulo.appgetfood.LoginPackage.LoginActivity
import com.facebook.FacebookSdk
import com.facebook.appevents.AppEventsLogger
import com.google.firebase.FirebaseApp

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        FirebaseApp.initializeApp(this)

        FacebookSdk.sdkInitialize(applicationContext);
        AppEventsLogger.activateApp(this);

        startActivity(Intent(this, LoginActivity::class.java))
    }
}

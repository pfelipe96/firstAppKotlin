package com.example.paulo.appgetfood.HomePackage

import android.app.Activity
import android.content.Context
import android.support.design.widget.NavigationView
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.MenuItem
import com.example.paulo.appgetfood.R

/**
 * Created by paulo on 10/03/18.
 */
class HomeViewHolder(var activity: Activity, var context: Context): NavigationView.OnNavigationItemSelectedListener {

    lateinit var mNavDrawerLayout : DrawerLayout

    fun setToolbar(appCompatActivity: AppCompatActivity, toolBar: android.support.v7.widget.Toolbar){
        appCompatActivity.setSupportActionBar(toolBar)
    }


    fun setNavDrawer(appCompatActivity: AppCompatActivity,navDrawer: DrawerLayout, toolBar: android.support.v7.widget.Toolbar, navView: NavigationView){
        mNavDrawerLayout = navDrawer
        val toogle = ActionBarDrawerToggle(appCompatActivity, navDrawer, toolBar, R.string.open_drawer, R.string.close_drawer)
        mNavDrawerLayout.addDrawerListener(toogle)
        toogle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    fun setRecyclerView(recyclerView: RecyclerView, adapter: AdapterRecyclerHome){
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return true
    }


}
package com.example.paulo.appgetfood.HomePackage

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.paulo.appgetfood.BR
import com.example.paulo.appgetfood.R
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.toolbar.*

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val homeViewModel = HomeViewHolder(this@HomeActivity, this)

        val binding = DataBindingUtil.setContentView<ViewDataBinding>(this, R.layout.activity_home)
        binding.setVariable(BR.viewModel, homeViewModel)

        homeViewModel.setToolbar(this@HomeActivity, toolbarID)
        homeViewModel.setNavDrawer(this@HomeActivity, drawer_layout, toolbarID, navView)

    }
}

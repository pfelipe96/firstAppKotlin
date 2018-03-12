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

        var mDataSet =
                arrayListOf(
                        ConstructorSetCard("http://2.bp.blogspot.com/-IaVdp4HLbQY/Uuqk01E-C1I/AAAAAAABOjM/xs-M3CSonWA/s1600/wendys+logo.jpg", "Wendy's", "Vila Olimpia"),
                        ConstructorSetCard("https://i.pinimg.com/originals/37/d1/db/37d1db27632f1df71b90dd85888a4358.jpg", "McDonald's", "Brooklin"),
                        ConstructorSetCard("https://www.festisite.com/static/partylogo/img/logos/burger-king.png", "Buger King", "Moema"))

        val adapterRecyclerHome = AdapterRecyclerHome(this, this, mDataSet)

        homeViewModel.setRecyclerView(recycler_view_home, adapterRecyclerHome)

    }

}

class ConstructorSetCard(var imageURl: String, var nameBusiness: String, var adressBusiness: String)


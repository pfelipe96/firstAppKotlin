package com.example.paulo.appgetfood.HomePackage

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.paulo.appgetfood.R
import com.example.paulo.appgetfood.model.ConstructorSetCard
import kotlinx.android.synthetic.main.card_restaurant.view.*

/**
 * Created by paulo on 11/03/18.
 */

class AdapterRecyclerHome
(private val mActivity: Activity,
 val mContext: Context,
 private val mDataSet: ArrayList<ConstructorSetCard>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class SetCardView(v: View) : RecyclerView.ViewHolder(v) {
        val cardView = v.cardView
        val imageBusiness = v.image_card
        val nameBusiness = v.name_business
        val adressBusiness = v.adress_business
        val buttonSendMap = v.send_map

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SetCardView {
        val view = LayoutInflater.from(mActivity).inflate(R.layout.card_restaurant, parent, false)
        return SetCardView(view)
    }

    override fun getItemCount(): Int {
        return mDataSet.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = holder as SetCardView
        val constructorSetCard = mDataSet[position]

        item.nameBusiness.text = constructorSetCard.nameBusiness
        item.adressBusiness.text = constructorSetCard.adressBusiness
        setImageIntoView(constructorSetCard.imageURl, item.imageBusiness)

    }

    private fun setImageIntoView(path: String, imageView: ImageView) {
        Glide.with(mActivity)
                .load(path)
                .into(imageView)
    }
}
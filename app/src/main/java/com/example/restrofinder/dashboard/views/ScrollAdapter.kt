package com.example.restrofinder.dashboard.views

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.BitmapEncoder
import com.example.restrofinder.R
import com.example.restrofinder.dashboard.dataModel.Venues
import kotlinx.android.synthetic.main.row_item_restaurant_list.view.*
import java.util.*
import kotlin.collections.ArrayList


class ScrollAdapter(private val list : ArrayList<Venues>?, private val itemSelected: ItemSelected, private val context : Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int {
        return list?.size?:0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        Log.i("tag::","Onbindviewcalled")
        if (holder is ChildAdapterViewHolder){
            holder.apply {
                list?.let {
                    name.text = it[position].name
                    loc.text = it[position].location?.city
                    country.text = it[position].location?.country?.toUpperCase(Locale.ROOT)
                    val url = "${it[position].categories?.get(0)?.icon?.prefix}64${it[position].categories?.get(
                            0
                        )?.icon?.suffix}"

                    Glide.with(context)
                        .load(url)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .fitCenter()
                        .dontTransform()
                        .crossFade(20)
                        .placeholder(android.R.drawable.stat_notify_error)
                        .into(img)

                    holder.thumbsDown.setOnClickListener {
                        list?.let {it1-> itemSelected.onItemSelected(true,it1[position])}
                    }
                    holder.review.setOnClickListener {
                        list?.let {it1-> itemSelected.onItemSelected(false,it1[position])}
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_item_restaurant_list, parent, false)
        return ChildAdapterViewHolder(
            view
        )
    }

    fun updateAdapter(updatedList : ArrayList<Venues>?){
        updatedList?.let {
            list?.clear()
            list?.addAll(updatedList)
            notifyDataSetChanged() }
    }

    class ChildAdapterViewHolder(item: View) : RecyclerView.ViewHolder(item) {
        val name = item.tv_restro_name
        val loc = item.tv_restro_loc
        val img = item.iv_restro
        val country = item.tv_restro_country
        val thumbsDown = item.thumbs_down
        val review = item.rate
    }

}

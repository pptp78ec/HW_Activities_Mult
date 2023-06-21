package com.example.hw_activities_mult

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Objects

class ModelListAdapter(
    context: Context,
    private var resourse: Int,
    var objects: MutableList<ModelAuto>
) : ArrayAdapter<ModelAuto>(context, resourse, objects) {
    private var inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
         super.getView(position, convertView, parent)
        var localConvertView = convertView
        val viewHolder: ViewHolder
        if(localConvertView == null){
            localConvertView = inflater.inflate(this.resourse, parent, false)
            viewHolder = ViewHolder(localConvertView)
            localConvertView.tag = viewHolder
        }
        else{
            viewHolder = localConvertView.tag as ViewHolder
        }
        val model = objects[position]
        viewHolder.imageView?.setImageResource(model.image)
        viewHolder.brandView?.text = model.brand
        viewHolder.priceView?.text = model.cost.toString()
        viewHolder.yearView?.text = model.year.year.toString()
        viewHolder.descrView?.text = model.description
        viewHolder.modelView?.text = model.model

        return localConvertView!!

    }
    class ViewHolder {
        var imageView: ImageView? = null
        var brandView: TextView? = null
        var modelView: TextView? = null
        var yearView: TextView? = null
        var descrView: TextView? = null
        var priceView: TextView? = null
        constructor(view: View){
            this.imageView = view.findViewById(R.id.image)
            this.brandView = view.findViewById(R.id.brand)
            this.modelView = view.findViewById(R.id.model)
            this.yearView = view.findViewById(R.id.year)
            this.descrView = view.findViewById(R.id.description)
            this.priceView = view.findViewById(R.id.price)
        }
    }
}
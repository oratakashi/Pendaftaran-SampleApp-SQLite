package com.oratakashi.pendaftaran.ui.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oratakashi.pendaftaran.R
import com.oratakashi.pendaftaran.data.model.Students
import com.oratakashi.pendaftaran.utils.Converter
import kotlinx.android.synthetic.main.adapter_main.view.*

class MainAdapter(val data : List<Students>) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.also {
            it.tvName.text = data[position].name
            it.tvGanre.text = data[position].gender
            it.tvTTL.text = "${data[position].tmplahir}, " +
                    "${Converter.toIndoFormat(data[position].tgllahir)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder = ViewHolder(
        LayoutInflater.from(parent.context).inflate(R.layout.adapter_main, parent, false)
    )

    override fun getItemCount(): Int = data.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}
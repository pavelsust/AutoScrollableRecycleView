package com.example.testrecycleview

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NewsAdapter(var dataArray : Array<String?>) : RecyclerView.Adapter<NewsAdapter.NewsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder =
        NewsViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_adapter, parent, false))

    override fun getItemCount(): Int = Integer.MAX_VALUE

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        holder.bind()
    }

    inner class NewsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        var name = itemView.findViewById<TextView>(R.id.tvNews)

        fun bind() {
            dataArray[layoutPosition%dataArray.size]?.let { model->
                name.setOnClickListener(this)
                name.text = model
            }
        }

        override fun onClick(v: View?) {
//            v?.setTag(R.id.model ,dataArray[layoutPosition%dataArray.size])
//            listener.onClick(v)
        }
    }
}
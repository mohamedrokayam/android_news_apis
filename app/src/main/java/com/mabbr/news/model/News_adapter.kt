package com.mabbr.news.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mabbr.news.ArticlesItem
import com.mabbr.news.R

class News_adapter(var item: List<ArticlesItem>?) :
    RecyclerView.Adapter<News_adapter.ViewHolder>() {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val items = item?.get(position)
        holder.title.setText(items?.title)
        holder.outhar.setText(items?.author)
        holder.date_time.setText(items?.publishedAt)
        Glide.with(holder.itemView)
            .load(items?.urlToImage)
            .into(holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_news, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {

        return item?.size ?: 0;
    }

    fun changeData(data: List<ArticlesItem?>?) {
        item = data as List<ArticlesItem>?;
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val outhar: TextView = itemView.findViewById(R.id.author)
        val date_time: TextView = itemView.findViewById(R.id.date_time)
        val image: ImageView = itemView.findViewById(R.id.image)


    }
}
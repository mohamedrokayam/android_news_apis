package com.mabbr.news.ui.categories

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.card.MaterialCardView
import com.mabbr.news.R
import com.mabbr.news.ui.category

class CategoriesAdapter(val categories: List<category>) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            if (viewType == LEFT_SIDE_VIEW_TYPE)
                R.layout.left_sided_category
            else R.layout.right_sided_category, parent, false
        )
        return ViewHolder(view)

    }

    val LEFT_SIDE_VIEW_TYPE = 10;
    val RIGHT_SIDE_VIEW_TYPE = 20;
    override fun getItemViewType(position: Int): Int {
        return if (position % 2 == 0) LEFT_SIDE_VIEW_TYPE
        else RIGHT_SIDE_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val item = categories[position]
        holder.title.setText(item.titleId)
        holder.image.setImageResource(item.imageId)
        holder.materialCardView.setCardBackgroundColor(
            ContextCompat.getColor(
                holder.itemView.context,
                item.backgroundId
            )
        )
        onItemClickListener?.let {
            holder.itemView.setOnClickListener {
                onItemClickListener?.onItemClick(position, item)
            }
        }

    }

    var onItemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(pos: Int, category: category)
    }

    override fun getItemCount(): Int = categories.size


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.findViewById(R.id.title)
        val image: ImageView = itemView.findViewById(R.id.Image)
        val materialCardView: MaterialCardView = itemView.findViewById(R.id.material_cardview)
    }

}

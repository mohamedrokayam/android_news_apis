package com.mabbr.news.ui.categories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.mabbr.news.R
import com.mabbr.news.ui.category

class categore_fragment : Fragment() {

    val categorylist = listOf(
        category(
            "sports", R.drawable.ball,
            R.string.sports, R.color.red
        ), category(
            "technology", R.drawable.politics,
            R.string.technology, R.color.blue
        ), category(
            "health", R.drawable.health,
            R.string.health, R.color.pink
        ), category(
            "business", R.drawable.bussines,
            R.string.business, R.color.brown_orange
        ), category(
            "general", R.drawable.environment,
            R.string.general, R.color.baby_blue
        ), category(
            "science", R.drawable.science,
            R.string.science, R.color.yellow
        )

    )
    val adapter = CategoriesAdapter(categorylist)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_categories, container, false)
    }

    lateinit var recyclerView: RecyclerView
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = view.findViewById(R.id.Recycle_view)
        recyclerView.adapter = adapter

        adapter.onItemClickListener = object : CategoriesAdapter.OnItemClickListener {
            override fun onItemClick(pos: Int, category: category) {
                onCategoryClickListener?.oncategoryClick(category)
            }

        }
    }

    var onCategoryClickListener: OnCategoryClickListener? = null

    interface OnCategoryClickListener {
        fun oncategoryClick(category: category)
    }

}





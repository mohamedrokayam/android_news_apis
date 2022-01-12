package com.mabbr.news.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.mabbr.news.NewsResponse
import com.mabbr.news.R
import com.mabbr.news.api.api_Manger
import com.mabbr.news.constant
import com.mabbr.news.model.News_adapter
import com.mabbr.news.model.SourceResponse
import com.mabbr.news.model.SourcesItem
import com.mabbr.news.ui.category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class newsFragment : Fragment() {
    companion object {


        fun getInstance(category: category): newsFragment {
            val fragment = newsFragment()
            fragment.category = category
            return fragment
        }

    }

    lateinit var category: category
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    lateinit var tablayout: TabLayout
    lateinit var progress_bar: ProgressBar
    lateinit var RecyclerView: RecyclerView


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial_item()
        getNewsSources()
    }

    val adapter = News_adapter(null)

    fun initial_item() {
        tablayout = requireView().findViewById(R.id.tab_layout)
        progress_bar = requireView().findViewById(R.id.progress_bar)
        RecyclerView = requireView().findViewById(R.id.Recycle_view)
        RecyclerView.adapter = adapter
    }

    fun getNewsSources() {
        api_Manger.getApis()
            .getSources(constant.apiKey, category.id)
            .enqueue(object : Callback<SourceResponse> {
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {
                    progress_bar.isVisible = false
                    addSourcesTOTabLayout(response.body()?.sources)

                }

                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                    Log.e("error", t.localizedMessage)
                }

            })
    }

    fun addSourcesTOTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            val tab = tablayout.newTab()
            tab.setText(it?.name);
            tab.tag = it
            tablayout.addTab(tab);
        }
        tablayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    getNewsBySource(source)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    getNewsBySource(source)

                }

            }
        )
        tablayout.getTabAt(0)?.select()

    }

    fun getNewsBySource(source: SourcesItem) {
        progress_bar.isVisible = true

        api_Manger.getApis().getNews(constant.apiKey, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progress_bar.isVisible = false
                    adapter.changeData(response.body()?.articles)

                }

                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    progress_bar.isVisible = false
                }

            })

    }
}
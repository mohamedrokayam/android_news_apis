package com.mabbr.news

import android.os.Bundle
import android.util.Log
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.mabbr.news.api.api_Manger
import com.mabbr.news.model.News_adapter
import com.mabbr.news.model.SourceResponse
import com.mabbr.news.model.SourcesItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var tablayout: TabLayout
    lateinit var progress_bar: ProgressBar
    lateinit var RecyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tablayout = findViewById(R.id.tab_layout)
        progress_bar = findViewById(R.id.progress_bar)
        initial_item()
        getNewsSources()
    }

    val adapter = News_adapter(null)

    fun initial_item() {
        tablayout = findViewById(R.id.tab_layout)
        progress_bar = findViewById(R.id.progress_bar)
        RecyclerView = findViewById(R.id.Recycle_view)
        RecyclerView.adapter = adapter
    }

    fun getNewsSources() {
        api_Manger.getApis()
            .getSources(constant.apiKey, "")
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
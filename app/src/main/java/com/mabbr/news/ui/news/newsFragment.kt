package com.mabbr.news.ui.news

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayout
import com.mabbr.news.ArticlesItem
import com.mabbr.news.NewsResponse
import com.mabbr.news.R
import com.mabbr.news.api.api_Manger
import com.mabbr.news.constant
import com.mabbr.news.databinding.FragmentNewsBinding
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
    lateinit var viewdataBinding:FragmentNewsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.fragment_news, container, false)
        viewdataBinding=DataBindingUtil.inflate(inflater,R.layout.fragment_news,container,false)
        return viewdataBinding.root
    }


    lateinit var viewModel: newsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel=ViewModelProvider(this).get(newsViewModel::class.java)

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initial_item()
        subscribeToLiveData()
        viewModel.getNewsSources(category)
    }

    private fun subscribeToLiveData() {
        viewModel.sourcesLivedata.observe(viewLifecycleOwner,{
            addSourcesTOTabLayout(it)
        })
        viewModel.newsLiveData.observe(viewLifecycleOwner,{
            showNews(it)
        })
        viewModel.progressVisible.observe(viewLifecycleOwner,{isvisible->
            viewdataBinding.progressBar.isVisible=isVisible
//            if(isvisible)
//            progress_bar.isVisible=true
//            else
//                progress_bar.isVisible=false

        })
        viewModel.messageLiveData.observe(viewLifecycleOwner,{message->
            Toast.makeText(activity,message,Toast.LENGTH_LONG).show()

        })


    }

    private fun showNews(newList: List<ArticlesItem?>?) {
        adapter.changeData(newList)

    }

    val adapter = News_adapter(null)
   // ui matters

    fun initial_item() {
//        tablayout = requireView().findViewById(R.id.tab_layout)
//        progress_bar = requireView().findViewById(R.id.progress_bar)
//        RecyclerView = requireView().findViewById(R.id.Recycle_view)
        viewdataBinding.RecycleView.adapter = adapter
    }


    fun addSourcesTOTabLayout(sources: List<SourcesItem?>?) {
        sources?.forEach {
            val tab = viewdataBinding.tabLayout.newTab()
            tab.setText(it?.name);
            tab.tag = it
            viewdataBinding.tabLayout.addTab(tab);
        }
        viewdataBinding.tabLayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                    viewModel.getNewsBySource(source)

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {

                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    val source = tab?.tag as SourcesItem
                   viewModel.getNewsBySource(source)

                }

            }
        )
        viewdataBinding.tabLayout.getTabAt(0)?.select()

    }

}
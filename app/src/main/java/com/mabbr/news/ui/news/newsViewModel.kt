package com.mabbr.news.ui.news

import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mabbr.news.ArticlesItem
import com.mabbr.news.NewsResponse
import com.mabbr.news.api.api_Manger
import com.mabbr.news.constant
import com.mabbr.news.model.SourceResponse
import com.mabbr.news.model.SourcesItem
import com.mabbr.news.ui.category
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class newsViewModel:ViewModel() {

    val sourcesLivedata=MutableLiveData<List<SourcesItem?>?>()
    val newsLiveData=MutableLiveData<List<ArticlesItem?>?>()
    val progressVisible=MutableLiveData<Boolean>()
    val messageLiveData=MutableLiveData<String>()


    fun getNewsSources(category: category) {
        progressVisible.value=true
        api_Manger.getApis()
            .getSources(constant.apiKey, category.id)
            .enqueue(object : Callback<SourceResponse> {
                override fun onResponse(
                    call: Call<SourceResponse>,
                    response: Response<SourceResponse>
                ) {

                    progressVisible.value=false


                    //progress_bar.isVisible = false
                    //addSourcesTOTabLayout(response.body()?.sources)
                    sourcesLivedata.value=response.body()?.sources


                }



                override fun onFailure(call: Call<SourceResponse>, t: Throwable) {
                    progressVisible.value=false

//                    Log.e("error", t.localizedMessage)
                    messageLiveData.value=t.localizedMessage

                }

            })
    }
    fun getNewsBySource(source: SourcesItem) {
      //  progress_bar.isVisible = true
        progressVisible.value=true


        api_Manger.getApis().getNews(constant.apiKey, source.id ?: "")
            .enqueue(object : Callback<NewsResponse> {
                override fun onResponse(
                    call: Call<NewsResponse>,
                    response: Response<NewsResponse>
                ) {
                    progressVisible.value=false

                    newsLiveData.value=response.body()?.articles

                }


                override fun onFailure(call: Call<NewsResponse>, t: Throwable) {
                    // progress_bar.isVisible = false
                    progressVisible.value=false
                    messageLiveData.value=t.localizedMessage

                }

            })

    }


}
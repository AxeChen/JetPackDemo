package com.axe.paging

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val articleViewMode by lazy {
        PagingViewModel()
    }

    private val listAdapter by lazy {
        ArticleListAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvList.run {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = listAdapter
        }
        getArticle()
    }

    private fun getArticle() {
        articleViewMode.getArticleData2().observe(this, Observer {
            lifecycleScope.launchWhenCreated {
                listAdapter.submitData(it)
            }
        })
    }

}
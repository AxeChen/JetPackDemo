package com.axe.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.axe.network.viewmode.CoroutinesViewModel
import com.axe.network.viewmode.OnelyRetrofitViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(OnelyRetrofitViewModel::class.java) }
    private val viewModel2 by lazy { ViewModelProvider(this).get(CoroutinesViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startObserver()
        btnRequest.setOnClickListener {
            viewModel.getArticles(1)
        }

        btnCoroutinesRequest.setOnClickListener {
            viewModel2.getArticles(1)
        }
    }

    private fun startObserver() {
        viewModel2.articlesLiveData.observe(this, Observer {
            it.run {
                if (this.size > 0) {
                    // 显示出数据
                    val text = StringBuilder()
                    this.forEach {
                        text.append(it.title)
                    }
                    tvShowData.setText(text.toString())
                }
            }
        })

        viewModel.articlesLiveData.observe(this, Observer {
            it.run {
                if (this.size > 0) {
                    // 显示出数据
                    val text = StringBuilder()
                    this.forEach {
                        text.append(it.title)
                    }
                    tvShowData.setText(text.toString())
                }
            }
        })
    }
}
package com.axe.network

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.axe.network.viewmode.CoroutinesViewModel
import com.axe.network.viewmode.OnlyRetrofitViewModel
import com.axe.network.viewmode.RxJavaViewModel
import com.axe.network.viewmode2.CoroutinesViewModel2
import com.axe.network.viewmode2.OnlyRetrofitViewModel2
import com.axe.network.viewmode2.RxJavaViewModel2
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    private val viewModel by lazy { ViewModelProvider(this).get(OnlyRetrofitViewModel2::class.java) }
    private val viewModel2 by lazy { ViewModelProvider(this).get(CoroutinesViewModel2::class.java) }
    private val viewModel3 by lazy { ViewModelProvider(this).get(RxJavaViewModel2::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        startObserver()

        btnRequest.setOnClickListener {
            viewModel.getArticles(1)
        }

        btnCoroutinesRequest.setOnClickListener {
            viewModel2.getArticles2(1)
        }
        btnRxJavaRequest.setOnClickListener {
            viewModel3.getArticles(1)
        }
    }

    private fun startObserver() {


        viewModel3.apiError.observe(this, errorObserver)
        viewModel3.articlesLiveData.observe(this, Observer {
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


        viewModel2.apiError.observe(this, errorObserver)
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

        viewModel.apiError.observe(this,errorObserver)
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
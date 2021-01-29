package com.axe.viewmodel

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), ViewModelStoreOwner {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 1、初始化ViewModel
        var myViewModel = ViewModelProvider(this).get(MyViewModel::class.java)
//        var myViewModel = ViewModelProvider(viewModelStore,ViewModelProvider.NewInstanceFactory()).get(MyViewModel::class.java)

        Log.i("myViewModel", myViewModel.hashCode().toString())

        // 2、当Application做共享数据时使用这个。
//        val myViewModel = ViewModelProvider(BaseApplicaiton.instance!!).get(MyViewModel::class.java)
        // 2、注册Observer
        myViewModel.liveData.observe(this, Observer {
            tvContent.setText(it)
            Log.i("myViewModel", it)
        })

        btnGetData.setOnClickListener {
            // 3、获取数据
            myViewModel.getStringData()
        }

        btnToFragment.setOnClickListener {
            var fragment = OneFragment()
            supportFragmentManager.beginTransaction().replace(R.id.flContent, fragment)
                .commitAllowingStateLoss()
        }

        btnToActivity.setOnClickListener {
            startActivity(Intent(this, ActivityTwo::class.java))
        }

    }


}
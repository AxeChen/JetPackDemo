package com.axe.livedata

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

class MainActivity : AppCompatActivity() {

    var liveData1: MutableLiveData<Int> = MutableLiveData()
    var liveData2: MutableLiveData<String> = MutableLiveData()


    var mediatorLiveData: MediatorLiveData<Any> = MediatorLiveData()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // 基本的使用方式
        // 注册观察者
        startObserver()
        // 数据发生改变
        liveData1.postValue(1000)
        liveData2.postValue("33333")
        liveData2.postValue("666")
        liveData2.postValue("7777")
        liveData2.postValue("8888")

        // addSource将所有的MutableLiveData统一观察
        mediatorLiveData.addSource(liveData1,dataObserver3)
        mediatorLiveData.addSource(liveData2,dataObserver4)

        // 这个需要重写，否则mediatorLiveData.postValue("xxxx")没效果
        mediatorLiveData.observe(this, Observer {
            Log.i("liveData4",it.toString())
        })
        mediatorLiveData.postValue("xxxx")
    }


    private var dataObserver3 = object :Observer<Int>{
        override fun onChanged(t: Int?) {
            Log.i("liveData4",t.toString())
        }
    }
    private var dataObserver4 =object :Observer<Any> {
        override fun onChanged(t: Any?) {
            Log.i("liveData3",t.toString())
        }
    }

    private fun startObserver() {
        liveData1.observe(this, Observer {
            Log.i("liveData1",it.toString())
        })
        liveData2.observe(this, Observer {
            Log.i("liveData2",it.toString())
        })
    }
}
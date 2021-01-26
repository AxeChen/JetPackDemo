package com.axe.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MyViewModel : ViewModel() {

    var liveData = MutableLiveData<String>()

    fun getStringData() {
        liveData.postValue("ViewMode的示例代码！！")
    }
}
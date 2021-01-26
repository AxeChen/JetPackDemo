package com.axe.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

class BaseApplicaiton : Application(), ViewModelStoreOwner {

    companion object{
        var instance:BaseApplicaiton? = null
    }

    private val appViewModelStore: ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }
}
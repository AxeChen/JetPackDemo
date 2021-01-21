package com.axe.lifecycle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.*
import org.jetbrains.annotations.NotNull

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        lifecycle.addObserver(MyLifecycleObserver())
        lifecycle.addObserver(MyLifecycleObserver2())

    }
}

// 第一种，通过注解的方式
class MyLifecycleObserver : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(@NotNull owner: LifecycleOwner) {
        Log.i("MyLifecycleObserver", "activity onStart")
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
    fun onStop() {
        Log.i("MyLifecycleObserver", "activity onStop")
    }
}

// 2、继承LifecycleEventObserver
class MyLifecycleObserver2 : LifecycleEventObserver {
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_START -> {
                Log.i("MyLifecycleObserver2", "activity onStart: $event")
            }
            Lifecycle.Event.ON_STOP -> {
                Log.i("MyLifecycleObserver2", "activity onStop: $event")
            }
        }
    }
}
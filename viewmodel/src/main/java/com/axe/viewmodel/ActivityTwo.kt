package com.axe.viewmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.activity_two.*

class ActivityTwo : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_two)
        val viewModel = ViewModelProvider(BaseApplicaiton.instance!!).get(MyViewModel::class.java)

        viewModel.liveData.observe(this, Observer {
            tvContent.setText(it)
        })

        btnGetData.setOnClickListener {
            viewModel.getStringData()
        }
    }
}
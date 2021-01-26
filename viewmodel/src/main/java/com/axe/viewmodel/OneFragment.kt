package com.axe.viewmodel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlinx.android.synthetic.main.fragment_one.*

class OneFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_one, container, false)

    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        var viewModel = ViewModelProvider(this).get(MyViewModel::class.java)
        var viewModel = ViewModelProvider(requireActivity()).get(MyViewModel::class.java)
        viewModel.liveData.observe(this, {
            tvContent.text = it
        })

    }
}
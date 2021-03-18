package com.axe.network

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.axe.network.exception.ApiException
import com.axe.network.exception.CustomException

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    // 公共的异常处理方法
    val errorObserver by lazy {
        Observer<ApiException> {
            it?.let {
                when (it.code) {
                    CustomException.RE_LOGIN -> {
                        // 去重新登陆
                    }
                    CustomException.OTHER -> {
                        // 其他的一些错误
                    }
                    CustomException.ERROR_TEXT -> {
                        Toast.makeText(this, it.displayMessage, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
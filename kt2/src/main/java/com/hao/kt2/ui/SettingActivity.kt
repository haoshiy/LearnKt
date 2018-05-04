package com.hao.kt2.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.hao.kt2.R

class SettingActivity : AppCompatActivity() {
    companion object {
        const val ZIP_CODE = "zipCode"
        const val DEFAULT_ZIP = 94043L
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
    }
}

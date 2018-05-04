package com.hao.kt2.ui

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import com.hao.kt2.R
import com.hao.kt2.adapter.ForecastListAdapter
import com.hao.kt2.domain.commands.RequestForecastCommand
import com.hao.kt2.domain.model.ForecastList
import com.hao.kt2.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.find
import org.jetbrains.anko.startActivity

class MainActivity : AppCompatActivity(), ToolbarManager {

    private val TAG = "MainActivity"

    private val zipCode: Long by DelegatesExt.preference(this, SettingActivity.ZIP_CODE, SettingActivity.DEFAULT_ZIP)

    override val toolbar by lazy {
        find<Toolbar>(R.id.toolbar)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initToolbar()
        attachToScroll(recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()
        loadData()
    }

    private fun loadData() = async(UI) {
        Log.d(TAG, "UI = " + Thread.currentThread().name)
        val result = bg {
            Log.d(TAG, "bg = " + Thread.currentThread().name)
            RequestForecastCommand(zipCode).execute() }
        Log.d(TAG, "update bef = " + Thread.currentThread().name)
        updateUI(result.await())
    }

    private fun updateUI(weekForecast: ForecastList) {
        Log.d(TAG, "updateUI = " + Thread.currentThread().name)
        val adapter = ForecastListAdapter(weekForecast) {
            startActivity<DetailsActivity>(
                    DetailsActivity.ID to it.id,
                    DetailsActivity.CITY_NAME to weekForecast.city)
        }

        recyclerView.adapter = adapter
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
    }
}

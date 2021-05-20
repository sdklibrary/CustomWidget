package com.pretty.demo.widget

import android.app.Application
import com.pretty.library.widget.pager.PagerStatusView

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        PagerStatusView.setLoadingLayout(R.layout.pager_status_loading)
        PagerStatusView.setEmptyLayout(R.layout.pager_status_empty)
        PagerStatusView.setErrorLayout(R.layout.pager_status_error)
    }
}
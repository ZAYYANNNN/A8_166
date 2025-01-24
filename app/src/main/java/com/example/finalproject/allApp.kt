package com.example.finalproject

import android.app.Application
import com.example.finalproject.ui.Container.AppContainer
import com.example.finalproject.ui.Container.StayContainer

class StayApplications: Application()
{ lateinit var container: AppContainer
    override fun onCreate() {
        super.onCreate()
        container = StayContainer()
    }
}

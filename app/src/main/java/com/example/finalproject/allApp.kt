package com.example.finalproject

import android.app.Application
import com.example.finalproject.ui.Container.BangunanContainer
import com.example.finalproject.ui.Container.BgnContainer
import com.example.finalproject.ui.Container.KamarContainer
import com.example.finalproject.ui.Container.KmrContainer
import com.example.finalproject.ui.Container.MahasiswaContainer
import com.example.finalproject.ui.Container.MhsContainer
import com.example.finalproject.ui.Container.PayContainer
import com.example.finalproject.ui.Container.SewaContainer

class MahasiswaApplications: Application()
{ lateinit var container: MhsContainer
    override fun onCreate() {
        super.onCreate()
        container = MahasiswaContainer()
    }
}

class KamarApplications: Application()
{ lateinit var container: KmrContainer
    override fun onCreate() {
        super.onCreate()
        container = KamarContainer()
    }
}
class BangunanApplications: Application()
{ lateinit var container: BgnContainer
    override fun onCreate() {
        super.onCreate()
        container = BangunanContainer()
    }
}
class SewaApplications: Application()
{ lateinit var container: PayContainer
    override fun onCreate() {
        super.onCreate()
        container = SewaContainer()
    }
}
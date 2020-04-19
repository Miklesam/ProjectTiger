package com.miklesam.dotamanager

import android.app.Application
import com.miklesam.dotamanager.utils.PrefsHelper
import com.miklesam.dotamanager.utils.TypefaceUtil

class ApplicationDota :Application(){
    override fun onCreate() {
        super.onCreate()
        PrefsHelper.init(this)
        TypefaceUtil.overrideFont(applicationContext, "SANS_SERIF", "fonts/16606.ttf")
    }
}
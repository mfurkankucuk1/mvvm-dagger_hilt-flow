package com.example.mvvm_daggerhilt_flow

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by M.Furkan KÜÇÜK on 8.08.2022
 **/
@HiltAndroidApp
class MyApplication: Application() {

    override fun onCreate() {
        super.onCreate()
    }
}
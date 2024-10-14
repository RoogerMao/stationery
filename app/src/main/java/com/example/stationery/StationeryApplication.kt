package com.example.stationery

import android.app.Application
import com.example.stationery.data.StationeryContainer
import com.example.stationery.data.StationeryDataContainer

class StationeryApplication : Application() {

    // centralizes dependencies into one container
    lateinit var container: StationeryContainer

    override fun onCreate() {
        super.onCreate()
        container = StationeryDataContainer(this)
    }

}
package com.axe.room

import android.app.Application
import com.axe.room.db.AppDataBase

class RoomDemoApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppDataBase.getInstance(this)
    }
}
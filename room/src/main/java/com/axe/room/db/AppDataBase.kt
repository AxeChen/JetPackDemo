package com.axe.room.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.axe.room.bean.User
import com.axe.room.dao.UserDao


@Database(entities = [User::class], version = 1)
abstract class AppDataBase : RoomDatabase() {

    abstract fun userDao(): UserDao

    companion object {
        private var instance: AppDataBase? = null

        fun getInstance(context: Context): AppDataBase {
            if (instance == null) {
                synchronized(AppDataBase::class.java) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDataBase::class.java,
                        "app_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return instance!!
        }
    }




}

package com.bignerdranch.android.testapplicationvariant3.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GoodsEntity::class],
    version = 1,
    exportSchema = false
)
abstract class GoodsDatabase : RoomDatabase() {
    abstract val goodsDao: GoodsDao
}

private lateinit var instance: GoodsDatabase

fun getDatabase(context: Context): GoodsDatabase {
    synchronized(GoodsDatabase::class.java) {
        if (!::instance.isInitialized) {
            instance = Room.databaseBuilder(
                context.applicationContext,
                GoodsDatabase::class.java,
                "goods-db"
            ).build()
        }
    }
    return instance
}
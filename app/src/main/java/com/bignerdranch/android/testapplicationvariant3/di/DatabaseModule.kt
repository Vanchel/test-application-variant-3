package com.bignerdranch.android.testapplicationvariant3.di

import android.content.Context
import com.bignerdranch.android.testapplicationvariant3.database.GoodsDao
import com.bignerdranch.android.testapplicationvariant3.database.GoodsDatabase
import com.bignerdranch.android.testapplicationvariant3.database.getDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {
    @Singleton
    @Provides
    fun provideGoodsDatabase(@ApplicationContext context: Context): GoodsDatabase {
        return getDatabase(context)
    }

    @Provides
    fun provideGoodsDao(goodsDatabase: GoodsDatabase): GoodsDao {
        return goodsDatabase.goodsDao
    }
}
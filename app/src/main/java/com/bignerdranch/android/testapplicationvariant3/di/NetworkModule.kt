package com.bignerdranch.android.testapplicationvariant3.di

import com.bignerdranch.android.testapplicationvariant3.network.GoodsService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Provides
    fun provideGoodsService(): GoodsService {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder()
            .baseUrl("http://94.127.67.113:8099/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(GoodsService::class.java)
    }
}
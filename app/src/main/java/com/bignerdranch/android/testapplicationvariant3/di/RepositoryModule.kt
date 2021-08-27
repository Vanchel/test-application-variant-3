package com.bignerdranch.android.testapplicationvariant3.di

import com.bignerdranch.android.testapplicationvariant3.repository.GoodsRepository
import com.bignerdranch.android.testapplicationvariant3.repository.TestRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@InstallIn(SingletonComponent::class)
@Module
abstract class RepositoryModule {
    @Binds
    abstract fun bindTestRepository(goodsRepository: GoodsRepository): TestRepository
}
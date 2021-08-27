package com.bignerdranch.android.testapplicationvariant3.repository

import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import kotlinx.coroutines.flow.Flow

interface TestRepository {
    val goods: Flow<List<Goods>>
    val goodsInCart: Flow<List<Goods>>

    fun getGoodsById(id: String): Flow<Goods>

    suspend fun updateGoods(goods: Goods)

    suspend fun refreshGoods()
}
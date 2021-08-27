package com.bignerdranch.android.testapplicationvariant3.repository

import com.bignerdranch.android.testapplicationvariant3.database.GoodsDao
import com.bignerdranch.android.testapplicationvariant3.database.GoodsEntity
import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.bignerdranch.android.testapplicationvariant3.network.GoodsService
import com.bignerdranch.android.testapplicationvariant3.network.NetworkGoods
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GoodsRepository @Inject constructor(
    private val goodsService: GoodsService,
    private val goodsDao: GoodsDao
) : TestRepository {
    override val goods: Flow<List<Goods>> = goodsDao.getAllGoods().map {
        it.map(GoodsEntity::toDomainModel)
    }

    override val goodsInCart: Flow<List<Goods>> = goodsDao.getGoodsInCart().map {
        it.map(GoodsEntity::toDomainModel)
    }

    override fun getGoodsById(id: String): Flow<Goods> {
        return goodsDao.getGoodsById(id).map(GoodsEntity::toDomainModel)
    }

    override suspend fun updateGoods(goods: Goods) = withContext(Dispatchers.IO) {
        goodsDao.updateGoods(GoodsEntity.fromDomainModel(goods))
    }

    override suspend fun refreshGoods() = withContext(Dispatchers.IO) {
        val goods = goodsService.getGoods().map(NetworkGoods::toDomainModel)
        goodsDao.insertAll(goods.map(GoodsEntity.Companion::fromDomainModel))
    }
}
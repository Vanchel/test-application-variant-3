package com.bignerdranch.android.testapplicationvariant3.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface GoodsDao {
    @Query("SELECT * FROM goods")
    fun getAllGoods(): Flow<List<GoodsEntity>>

    @Query("SELECT * FROM goods WHERE count > 0")
    fun getGoodsInCart(): Flow<List<GoodsEntity>>

    @Query("SELECT * FROM goods WHERE goods_id = :id LIMIT 1")
    fun getGoodsById(id: String): Flow<GoodsEntity>

    @Update
    suspend fun updateGoods(goods: GoodsEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(goods: List<GoodsEntity>)
}
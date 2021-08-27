package com.bignerdranch.android.testapplicationvariant3.network

import retrofit2.http.GET

interface GoodsService {
    @GET("getGoods")
    suspend fun getGoods(): List<NetworkGoods>
}
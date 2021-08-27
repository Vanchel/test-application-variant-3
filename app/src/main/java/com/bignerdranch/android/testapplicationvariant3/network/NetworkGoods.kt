package com.bignerdranch.android.testapplicationvariant3.network

import com.bignerdranch.android.testapplicationvariant3.domain.Goods
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class NetworkGoods(
    @Json(name = "desc") val description: String,
    val id: String,
    @Json(name = "image") val imageUrl: String,
    val name: String,
    val price: Double,
    val weight: Double
) {
    fun toDomainModel() = Goods(
        id = id,
        name = name,
        description = description,
        count = 0,
        imageUrl = imageUrl,
        price = price,
        weight = weight
    )
}
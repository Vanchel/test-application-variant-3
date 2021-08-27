package com.bignerdranch.android.testapplicationvariant3.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.bignerdranch.android.testapplicationvariant3.domain.Goods

@Entity(tableName = "goods")
data class GoodsEntity(
    @PrimaryKey
    @ColumnInfo(name = "goods_id") val id: String,
    val name: String,
    val description: String,
    val count: Int,
    @ColumnInfo(name = "image_url") val imageUrl: String,
    val price: Double,
    val weight: Double
) {
    fun toDomainModel() = Goods(
        id = id,
        name = name,
        description = description,
        count = count,
        imageUrl = imageUrl,
        price = price,
        weight = weight
    )

    companion object {
        fun fromDomainModel(goods: Goods) = GoodsEntity(
            id = goods.id,
            name = goods.name,
            description = goods.description,
            count = goods.count,
            imageUrl = goods.imageUrl,
            price = goods.price,
            weight = goods.weight
        )
    }
}
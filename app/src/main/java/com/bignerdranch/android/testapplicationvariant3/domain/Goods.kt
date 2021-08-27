package com.bignerdranch.android.testapplicationvariant3.domain

data class Goods(
    val id: String,
    val name: String,
    val description: String,
    val count: Int,
    val imageUrl: String,
    val price: Double,
    val weight: Double
)
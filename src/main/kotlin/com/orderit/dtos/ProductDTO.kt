package com.orderit.dtos

import kotlinx.serialization.Serializable

@Serializable
data class ProductRequest(
    val name: String,
    val description: String = "",
    val imageUrl: String = "",
    val categoryId: Int,
    val type: String = "individual",
    val price: Double,
    val salePrice: Double? = null,
    val saleStart: String? = null,
    val saleEnd: String? = null,
    val stock: Int = 0,
    val available: Boolean = true,
    val featured: Boolean = false
)

@Serializable
data class ProductResponse(
    val id: Int,
    val name: String,
    val description: String,
    val imageUrl: String,
    val categoryId: Int,
    val type: String,
    val price: Double,
    val salePrice: Double?,
    val saleStart: String?,
    val saleEnd: String?,
    val stock: Int,
    val available: Boolean,
    val featured: Boolean
)

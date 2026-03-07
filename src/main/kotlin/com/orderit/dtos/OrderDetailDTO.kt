package com.orderit.dtos

import kotlinx.serialization.Serializable

@Serializable
data class OrderDetailRequest(
    val orderId: Int,
    val productId: Int,
    val quantity: Int
)

@Serializable
data class OrderDetailResponse(
    val id: Int,
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val unitPrice: Double,
    val subtotal: Double
)

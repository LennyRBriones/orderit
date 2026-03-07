package com.orderit.dtos

import kotlinx.serialization.Serializable

@Serializable
data class OrderRequest(
    val tableId: Int,
    val waiterId: Int,
    val guestCount: Int
)

@Serializable
data class OrderResponse(
    val id: Int,
    val tableId: Int,
    val waiterId: Int,
    val guestCount: Int,
    val status: String,
    val total: Double,
    val tip: Double,
    val openedAt: String,
    val closedAt: String?
)

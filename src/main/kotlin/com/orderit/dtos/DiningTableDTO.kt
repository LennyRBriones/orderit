package com.orderit.dtos

import kotlinx.serialization.Serializable

@Serializable
data class DiningTableRequest(
    val number: Int,
    val capacity: Int,
    val status: String = "free"
)

@Serializable
data class DiningTableResponse(
    val id: Int,
    val number: Int,
    val capacity: Int,
    val status: String
)

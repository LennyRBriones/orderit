package com.orderit.dtos

import kotlinx.serialization.Serializable

@Serializable
data class WaiterRequest(
    val name: String,
    val username: String,
    val password: String
)

@Serializable
data class WaiterResponse(
    val id: Int,
    val name: String,
    val username: String
)

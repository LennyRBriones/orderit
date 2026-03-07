package com.orderit.dtos

import kotlinx.serialization.Serializable

@Serializable
data class CategoryRequest(
    val name: String
)

@Serializable
data class CategoryResponse(
    val id: Int,
    val name: String
)

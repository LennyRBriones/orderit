package com.orderit.services

import com.orderit.dbQuery
import com.orderit.dtos.ProductRequest
import com.orderit.dtos.ProductResponse
import com.orderit.models.Products
import kotlinx.datetime.LocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class ProductService {

    private fun resultRowToProduct(row: ResultRow) = ProductResponse(
        id = row[Products.id],
        name = row[Products.name],
        description = row[Products.description],
        imageUrl = row[Products.imageUrl],
        categoryId = row[Products.categoryId],
        type = row[Products.type],
        price = row[Products.price].toDouble(),
        salePrice = row[Products.salePrice]?.toDouble(),
        saleStart = row[Products.saleStart]?.toString(),
        saleEnd = row[Products.saleEnd]?.toString(),
        stock = row[Products.stock],
        available = row[Products.available],
        featured = row[Products.featured]
    )

    suspend fun getAll(): List<ProductResponse> = dbQuery {
        Products.selectAll().map(::resultRowToProduct)
    }

    suspend fun getById(id: Int): ProductResponse? = dbQuery {
        Products.selectAll()
            .where { Products.id eq id }
            .map(::resultRowToProduct)
            .singleOrNull()
    }

    suspend fun getByCategoryId(categoryId: Int): List<ProductResponse> = dbQuery {
        Products.selectAll()
            .where { Products.categoryId eq categoryId }
            .map(::resultRowToProduct)
    }

    suspend fun create(request: ProductRequest): ProductResponse = dbQuery {
        val insertStatement = Products.insert {
            it[name] = request.name
            it[description] = request.description
            it[imageUrl] = request.imageUrl
            it[categoryId] = request.categoryId
            it[type] = request.type
            it[price] = request.price.toBigDecimal()
            it[salePrice] = request.salePrice?.toBigDecimal()
            it[saleStart] = request.saleStart?.let { date -> LocalDateTime.parse(date) }
            it[saleEnd] = request.saleEnd?.let { date -> LocalDateTime.parse(date) }
            it[stock] = request.stock
            it[available] = request.available
            it[featured] = request.featured
        }
        resultRowToProduct(insertStatement.resultedValues!!.first())
    }

    suspend fun update(id: Int, request: ProductRequest): Boolean = dbQuery {
        Products.update({ Products.id eq id }) {
            it[name] = request.name
            it[description] = request.description
            it[imageUrl] = request.imageUrl
            it[categoryId] = request.categoryId
            it[type] = request.type
            it[price] = request.price.toBigDecimal()
            it[salePrice] = request.salePrice?.toBigDecimal()
            it[saleStart] = request.saleStart?.let { date -> LocalDateTime.parse(date) }
            it[saleEnd] = request.saleEnd?.let { date -> LocalDateTime.parse(date) }
            it[stock] = request.stock
            it[available] = request.available
            it[featured] = request.featured
        } > 0
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        Products.deleteWhere { Products.id eq id } > 0
    }
}

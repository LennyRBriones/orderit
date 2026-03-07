package com.orderit.services

import com.orderit.dbQuery
import com.orderit.dtos.OrderDetailRequest
import com.orderit.dtos.OrderDetailResponse
import com.orderit.models.OrderDetails
import com.orderit.models.Products
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class OrderDetailService {

    private fun resultRowToOrderDetail(row: ResultRow) = OrderDetailResponse(
        id = row[OrderDetails.id],
        orderId = row[OrderDetails.orderId],
        productId = row[OrderDetails.productId],
        quantity = row[OrderDetails.quantity],
        unitPrice = row[OrderDetails.unitPrice].toDouble(),
        subtotal = row[OrderDetails.subtotal].toDouble()
    )

    suspend fun getByOrderId(orderId: Int): List<OrderDetailResponse> = dbQuery {
        OrderDetails.selectAll()
            .where { OrderDetails.orderId eq orderId }
            .map(::resultRowToOrderDetail)
    }

    suspend fun getById(id: Int): OrderDetailResponse? = dbQuery {
        OrderDetails.selectAll()
            .where { OrderDetails.id eq id }
            .map(::resultRowToOrderDetail)
            .singleOrNull()
    }

    suspend fun create(request: OrderDetailRequest): OrderDetailResponse = dbQuery {
        val product = Products.selectAll()
            .where { Products.id eq request.productId }
            .single()

        val unitPrice = product[Products.price]
        val subtotal = unitPrice * request.quantity.toBigDecimal()

        val insertStatement = OrderDetails.insert {
            it[orderId] = request.orderId
            it[productId] = request.productId
            it[quantity] = request.quantity
            it[OrderDetails.unitPrice] = unitPrice
            it[OrderDetails.subtotal] = subtotal
        }
        resultRowToOrderDetail(insertStatement.resultedValues!!.first())
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        OrderDetails.deleteWhere { OrderDetails.id eq id } > 0
    }
}

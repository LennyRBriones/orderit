package com.orderit.services

import com.orderit.dbQuery
import com.orderit.dtos.OrderRequest
import com.orderit.dtos.OrderResponse
import com.orderit.models.Orders
import kotlinx.datetime.Clock
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class OrderService {

    private fun resultRowToOrder(row: ResultRow) = OrderResponse(
        id = row[Orders.id],
        tableId = row[Orders.tableId],
        waiterId = row[Orders.waiterId],
        guestCount = row[Orders.guestCount],
        status = row[Orders.status],
        total = row[Orders.total].toDouble(),
        tip = row[Orders.tip].toDouble(),
        openedAt = row[Orders.openedAt].toString(),
        closedAt = row[Orders.closedAt]?.toString()
    )

    suspend fun getAll(): List<OrderResponse> = dbQuery {
        Orders.selectAll().map(::resultRowToOrder)
    }

    suspend fun getById(id: Int): OrderResponse? = dbQuery {
        Orders.selectAll()
            .where { Orders.id eq id }
            .map(::resultRowToOrder)
            .singleOrNull()
    }

    suspend fun create(request: OrderRequest): OrderResponse = dbQuery {
        val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
        val insertStatement = Orders.insert {
            it[tableId] = request.tableId
            it[waiterId] = request.waiterId
            it[guestCount] = request.guestCount
            it[openedAt] = now
        }
        resultRowToOrder(insertStatement.resultedValues!!.first())
    }

    suspend fun update(id: Int, request: OrderRequest): Boolean = dbQuery {
        Orders.update({ Orders.id eq id }) {
            it[tableId] = request.tableId
            it[waiterId] = request.waiterId
            it[guestCount] = request.guestCount
        } > 0
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        Orders.deleteWhere { Orders.id eq id } > 0
    }
}

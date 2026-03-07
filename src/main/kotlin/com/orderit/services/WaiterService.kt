package com.orderit.services

import com.orderit.dbQuery
import com.orderit.dtos.WaiterRequest
import com.orderit.dtos.WaiterResponse
import com.orderit.models.Waiters
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class WaiterService {

    private fun resultRowToWaiter(row: ResultRow) = WaiterResponse(
        id = row[Waiters.id],
        name = row[Waiters.name],
        username = row[Waiters.username]
    )

    suspend fun getAll(): List<WaiterResponse> = dbQuery {
        Waiters.selectAll().map(::resultRowToWaiter)
    }

    suspend fun getById(id: Int): WaiterResponse? = dbQuery {
        Waiters.selectAll()
            .where { Waiters.id eq id }
            .map(::resultRowToWaiter)
            .singleOrNull()
    }

    suspend fun create(request: WaiterRequest): WaiterResponse = dbQuery {
        val insertStatement = Waiters.insert {
            it[name] = request.name
            it[username] = request.username
            it[password] = request.password
        }
        resultRowToWaiter(insertStatement.resultedValues!!.first())
    }

    suspend fun update(id: Int, request: WaiterRequest): Boolean = dbQuery {
        Waiters.update({ Waiters.id eq id }) {
            it[name] = request.name
            it[username] = request.username
            it[password] = request.password
        } > 0
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        Waiters.deleteWhere { Waiters.id eq id } > 0
    }
}

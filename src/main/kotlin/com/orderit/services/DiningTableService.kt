package com.orderit.services

import com.orderit.dbQuery
import com.orderit.dtos.DiningTableRequest
import com.orderit.dtos.DiningTableResponse
import com.orderit.models.DiningTables
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class DiningTableService {

    private fun resultRowToDiningTable(row: ResultRow) = DiningTableResponse(
        id = row[DiningTables.id],
        number = row[DiningTables.number],
        capacity = row[DiningTables.capacity],
        status = row[DiningTables.status]
    )

    suspend fun getAll(): List<DiningTableResponse> = dbQuery {
        DiningTables.selectAll().map(::resultRowToDiningTable)
    }

    suspend fun getById(id: Int): DiningTableResponse? = dbQuery {
        DiningTables.selectAll()
            .where { DiningTables.id eq id }
            .map(::resultRowToDiningTable)
            .singleOrNull()
    }

    suspend fun create(request: DiningTableRequest): DiningTableResponse = dbQuery {
        val insertStatement = DiningTables.insert {
            it[number] = request.number
            it[capacity] = request.capacity
            it[status] = request.status
        }
        resultRowToDiningTable(insertStatement.resultedValues!!.first())
    }

    suspend fun update(id: Int, request: DiningTableRequest): Boolean = dbQuery {
        DiningTables.update({ DiningTables.id eq id }) {
            it[number] = request.number
            it[capacity] = request.capacity
            it[status] = request.status
        } > 0
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        DiningTables.deleteWhere { DiningTables.id eq id } > 0
    }
}

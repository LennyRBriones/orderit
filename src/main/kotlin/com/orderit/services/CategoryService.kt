package com.orderit.services

import com.orderit.dbQuery
import com.orderit.dtos.CategoryRequest
import com.orderit.dtos.CategoryResponse
import com.orderit.models.Categories
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq

class CategoryService {

    private fun resultRowToCategory(row: ResultRow) = CategoryResponse(
        id = row[Categories.id],
        name = row[Categories.name]
    )

    suspend fun getAll(): List<CategoryResponse> = dbQuery {
        Categories.selectAll().map(::resultRowToCategory)
    }

    suspend fun getById(id: Int): CategoryResponse? = dbQuery {
        Categories.selectAll()
            .where { Categories.id eq id }
            .map(::resultRowToCategory)
            .singleOrNull()
    }

    suspend fun create(request: CategoryRequest): CategoryResponse = dbQuery {
        val insertStatement = Categories.insert {
            it[name] = request.name
        }
        resultRowToCategory(insertStatement.resultedValues!!.first())
    }

    suspend fun update(id: Int, request: CategoryRequest): Boolean = dbQuery {
        Categories.update({ Categories.id eq id }) {
            it[name] = request.name
        } > 0
    }

    suspend fun delete(id: Int): Boolean = dbQuery {
        Categories.deleteWhere { Categories.id eq id } > 0
    }
}

package com.orderit

import com.orderit.models.*
import io.ktor.server.application.*
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction

fun Application.configureDatabaseFactory() {
    val url = environment.config.property("database.url").getString()
    val driver = environment.config.property("database.driver").getString()
    val user = environment.config.property("database.user").getString()
    val password = environment.config.property("database.password").getString()

    Database.connect(
        url = url,
        driver = driver,
        user = user,
        password = password
    )

    val tables = arrayOf(OrderDetails, Orders, Products, Categories, DiningTables, Waiters)

    transaction {
        SchemaUtils.drop(*tables)
        SchemaUtils.create(Waiters, Categories, Products, DiningTables, Orders, OrderDetails)
    }

    log.info("Database connected and tables created successfully")
}

suspend fun <T> dbQuery(block: suspend () -> T): T =
    newSuspendedTransaction(Dispatchers.IO) { block() }

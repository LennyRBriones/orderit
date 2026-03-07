package com.orderit.models

import org.jetbrains.exposed.sql.Table
import org.jetbrains.exposed.sql.kotlin.datetime.datetime

object Waiters : Table("waiters") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100)
    val username = varchar("username", 50).uniqueIndex()
    val password = varchar("password", 255)

    override val primaryKey = PrimaryKey(id)
}

object Categories : Table("categories") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 100).uniqueIndex()

    override val primaryKey = PrimaryKey(id)
}

object Products : Table("products") {
    val id = integer("id").autoIncrement()
    val name = varchar("name", 150)
    val description = text("description").default("")
    val imageUrl = varchar("image_url", 500).default("")
    val categoryId = integer("category_id").references(Categories.id)
    val type = varchar("type", 20).default("individual")
    val price = decimal("price", 10, 2)
    val salePrice = decimal("sale_price", 10, 2).nullable()
    val saleStart = datetime("sale_start").nullable()
    val saleEnd = datetime("sale_end").nullable()
    val stock = integer("stock").default(0)
    val available = bool("available").default(true)
    val featured = bool("featured").default(false)

    override val primaryKey = PrimaryKey(id)
}

object DiningTables : Table("dining_tables") {
    val id = integer("id").autoIncrement()
    val number = integer("number").uniqueIndex()
    val capacity = integer("capacity")
    val status = varchar("status", 20).default("free")

    override val primaryKey = PrimaryKey(id)
}

object Orders : Table("orders") {
    val id = integer("id").autoIncrement()
    val tableId = integer("table_id").references(DiningTables.id)
    val waiterId = integer("waiter_id").references(Waiters.id)
    val guestCount = integer("guest_count")
    val status = varchar("status", 30).default("new")
    val total = decimal("total", 10, 2).default(java.math.BigDecimal.ZERO)
    val tip = decimal("tip", 10, 2).default(java.math.BigDecimal.ZERO)
    val openedAt = datetime("opened_at")
    val closedAt = datetime("closed_at").nullable()

    override val primaryKey = PrimaryKey(id)
}

object OrderDetails : Table("order_details") {
    val id = integer("id").autoIncrement()
    val orderId = integer("order_id").references(Orders.id)
    val productId = integer("product_id").references(Products.id)
    val quantity = integer("quantity")
    val unitPrice = decimal("unit_price", 10, 2)
    val subtotal = decimal("subtotal", 10, 2)

    override val primaryKey = PrimaryKey(id)
}

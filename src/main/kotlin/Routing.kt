package com.orderit

import com.orderit.routes.*
import com.orderit.services.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    install(StatusPages) {
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }

    val waiterService = WaiterService()
    val categoryService = CategoryService()
    val productService = ProductService()
    val diningTableService = DiningTableService()
    val orderService = OrderService()
    val orderDetailService = OrderDetailService()

    routing {
        get("/") {
            call.respondText("OrderIt API is running")
        }

        waiterRoutes(waiterService)
        categoryRoutes(categoryService)
        productRoutes(productService)
        diningTableRoutes(diningTableService)
        orderRoutes(orderService)
        orderDetailRoutes(orderDetailService)
    }
}

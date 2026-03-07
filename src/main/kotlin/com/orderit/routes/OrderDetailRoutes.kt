package com.orderit.routes

import com.orderit.dtos.OrderDetailRequest
import com.orderit.services.OrderDetailService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderDetailRoutes(service: OrderDetailService) {
    route("/orders/{orderId}/details") {
        get {
            val orderId = call.parameters["orderId"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid order ID")

            call.respond(service.getByOrderId(orderId))
        }

        post {
            val orderId = call.parameters["orderId"]?.toIntOrNull()
                ?: return@post call.respond(HttpStatusCode.BadRequest, "Invalid order ID")

            val request = call.receive<OrderDetailRequest>()

            if (request.orderId != orderId) {
                return@post call.respond(HttpStatusCode.BadRequest, "Order ID in body does not match URL")
            }

            val created = service.create(request)
            call.respond(HttpStatusCode.Created, created)
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = service.delete(id)

            if (deleted) call.respond(HttpStatusCode.OK, "Order detail deleted")
            else call.respond(HttpStatusCode.NotFound, "Order detail not found")
        }
    }
}

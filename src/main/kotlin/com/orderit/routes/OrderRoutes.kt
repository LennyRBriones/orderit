package com.orderit.routes

import com.orderit.dtos.OrderRequest
import com.orderit.services.OrderService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.orderRoutes(service: OrderService) {
    route("/orders") {
        get {
            call.respond(service.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val order = service.getById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Order not found")

            call.respond(order)
        }

        post {
            val request = call.receive<OrderRequest>()
            val created = service.create(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val request = call.receive<OrderRequest>()
            val updated = service.update(id, request)

            if (updated) call.respond(HttpStatusCode.OK, "Order updated")
            else call.respond(HttpStatusCode.NotFound, "Order not found")
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = service.delete(id)

            if (deleted) call.respond(HttpStatusCode.OK, "Order deleted")
            else call.respond(HttpStatusCode.NotFound, "Order not found")
        }
    }
}

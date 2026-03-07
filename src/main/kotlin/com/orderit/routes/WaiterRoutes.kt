package com.orderit.routes

import com.orderit.dtos.WaiterRequest
import com.orderit.services.WaiterService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.waiterRoutes(service: WaiterService) {
    route("/waiters") {
        get {
            call.respond(service.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val waiter = service.getById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Waiter not found")

            call.respond(waiter)
        }

        post {
            val request = call.receive<WaiterRequest>()
            val created = service.create(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val request = call.receive<WaiterRequest>()
            val updated = service.update(id, request)

            if (updated) call.respond(HttpStatusCode.OK, "Waiter updated")
            else call.respond(HttpStatusCode.NotFound, "Waiter not found")
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = service.delete(id)

            if (deleted) call.respond(HttpStatusCode.OK, "Waiter deleted")
            else call.respond(HttpStatusCode.NotFound, "Waiter not found")
        }
    }
}

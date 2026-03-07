package com.orderit.routes

import com.orderit.dtos.DiningTableRequest
import com.orderit.services.DiningTableService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.diningTableRoutes(service: DiningTableService) {
    route("/tables") {
        get {
            call.respond(service.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val table = service.getById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Table not found")

            call.respond(table)
        }

        post {
            val request = call.receive<DiningTableRequest>()
            val created = service.create(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val request = call.receive<DiningTableRequest>()
            val updated = service.update(id, request)

            if (updated) call.respond(HttpStatusCode.OK, "Table updated")
            else call.respond(HttpStatusCode.NotFound, "Table not found")
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = service.delete(id)

            if (deleted) call.respond(HttpStatusCode.OK, "Table deleted")
            else call.respond(HttpStatusCode.NotFound, "Table not found")
        }
    }
}

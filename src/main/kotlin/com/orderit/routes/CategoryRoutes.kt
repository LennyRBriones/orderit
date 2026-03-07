package com.orderit.routes

import com.orderit.dtos.CategoryRequest
import com.orderit.services.CategoryService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.categoryRoutes(service: CategoryService) {
    route("/categories") {
        get {
            call.respond(service.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val category = service.getById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Category not found")

            call.respond(category)
        }

        post {
            val request = call.receive<CategoryRequest>()
            val created = service.create(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val request = call.receive<CategoryRequest>()
            val updated = service.update(id, request)

            if (updated) call.respond(HttpStatusCode.OK, "Category updated")
            else call.respond(HttpStatusCode.NotFound, "Category not found")
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = service.delete(id)

            if (deleted) call.respond(HttpStatusCode.OK, "Category deleted")
            else call.respond(HttpStatusCode.NotFound, "Category not found")
        }
    }
}

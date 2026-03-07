package com.orderit.routes

import com.orderit.dtos.ProductRequest
import com.orderit.services.ProductService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.productRoutes(service: ProductService) {
    route("/categories/{categoryId}/products") {
        get {
            val categoryId = call.parameters["categoryId"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid category ID")

            call.respond(service.getByCategoryId(categoryId))
        }
    }

    route("/products") {
        get {
            call.respond(service.getAll())
        }

        get("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@get call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val product = service.getById(id)
                ?: return@get call.respond(HttpStatusCode.NotFound, "Product not found")

            call.respond(product)
        }

        post {
            val request = call.receive<ProductRequest>()
            val created = service.create(request)
            call.respond(HttpStatusCode.Created, created)
        }

        put("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@put call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val request = call.receive<ProductRequest>()
            val updated = service.update(id, request)

            if (updated) call.respond(HttpStatusCode.OK, "Product updated")
            else call.respond(HttpStatusCode.NotFound, "Product not found")
        }

        delete("/{id}") {
            val id = call.parameters["id"]?.toIntOrNull()
                ?: return@delete call.respond(HttpStatusCode.BadRequest, "Invalid ID")

            val deleted = service.delete(id)

            if (deleted) call.respond(HttpStatusCode.OK, "Product deleted")
            else call.respond(HttpStatusCode.NotFound, "Product not found")
        }
    }
}

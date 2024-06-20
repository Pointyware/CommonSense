package org.pointyware.commonsense.api.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get
import io.ktor.server.routing.post

/**
 *
 */
fun Routing.foo() {
    get("/foo") {
        call.respondNullable<String?>("Fooey!")
    }
}

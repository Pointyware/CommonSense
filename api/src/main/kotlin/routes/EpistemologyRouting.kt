package org.pointyware.commonsense.api.routes

import io.ktor.server.application.call
import io.ktor.server.response.respondNullable
import io.ktor.server.routing.Routing
import io.ktor.server.routing.get

/**
 *
 */
fun Routing.epistemology() {
    get("/epistemology") {
        call.respondNullable<String?>("Fooey!")
    }
}

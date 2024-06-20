package org.pointyware.commonsense.api

import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.routing.routing
import org.pointyware.commonsense.api.routes.auth
import org.pointyware.commonsense.api.routes.bar
import org.pointyware.commonsense.api.routes.foo

fun main() {
    val port = System.getenv("PORT")?.toInt() ?: 8080
    embeddedServer(Netty, port) {
        routing {
            auth()

            foo()
            bar()

//            ads()
//            analytics()
        }
    }.start(wait = true)
}

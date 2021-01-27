package examples.kuiz

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.request.receiveParameters
import io.ktor.response.respondFile
import io.ktor.response.respondRedirect
import io.ktor.response.respondText
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.getValue
import kotlinx.html.*
import java.io.File


var currentQuestion: Int = 0
val answers: MutableList<Answer> = mutableListOf()

fun main() {
    embeddedServer(Netty, port = 8080) {

        routing {
            get("/") {
                call.respondText("Website is under construction")
            }

            get("/question/{questionId}") {

            }

            post("/question/{questionId}") {

            }

            get("/answers/{questionId}") {

            }

            get("/setquestion/{questionId}") {

            }

            get("/style.css") {
                call.respondFile(File("resources/style.css"))
            }
        }

    }.start()
}


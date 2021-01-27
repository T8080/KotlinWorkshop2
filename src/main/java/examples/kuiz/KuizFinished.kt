package examples.kuiz

import io.ktor.application.call
import io.ktor.html.respondHtml
import io.ktor.request.receiveParameters
import io.ktor.response.respondFile
import io.ktor.response.respondRedirect
import io.ktor.routing.Routing
import io.ktor.routing.get
import io.ktor.routing.post
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.util.getValue
import kotlinx.html.*
import java.io.File


/*


var currentQuestion: Int? = 0
val answers: MutableList<Answer> = mutableListOf()

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toIntOrNull() ?: 8080) {
        routing {
            homePage()
            questionPage()
            answersPage()
            setQuestionPage()
            stylePage()
        }

    }.start()
}

private fun Routing.answersPage() {
    get("/answers/{questionId}") {
        val questionId: Int by call.parameters
        val question = questions[questionId]

        val answerCount = question.answers
            .mapIndexed { answerId, answer ->
                val answerCount = answers.count { it.answerId == answerId && it.questionId == questionId }
                Pair(answer, answerCount)
            }
            .sortedByDescending { (_, count) -> count }

        call.respondHtml {
            defaultHtml {
                h1 { +question.description }
                for ((answer, count) in answerCount) {
                    p { +"$answer: $count" }
                }
                a("/") { +"Next question" }
            }
        }
    }
}

private fun Routing.questionPage() {
    get("/question/{questionId}") {
        val questionId: Int by call.parameters
        val question = questions[questionId]

        call.respondHtml {
            defaultHtml {
                h1 { +question.description }
                form {
                    method = FormMethod.post

                    for ((i, answer) in question.answers.withIndex()) {
                        radioInput {
                            name = "answerId"
                            value = i.toString()
                            +answer
                        }
                        br()
                    }
                    br()

                    submitInput {
                        value = "Send Answer"
                    }
                }
            }
        }
    }

    post("/question/{questionId}") {
        val questionId: Int by call.parameters
        val answerId: Int by call.receiveParameters()

        answers.add(Answer(questionId, answerId))

        call.respondRedirect("/answers/$questionId")
    }
}

private fun Routing.stylePage() {
    get("/style.css") {
        call.respondFile(File("resources/style.css"))
    }
}

private fun Routing.homePage() {
    get("/") {
        if (currentQuestion != null) {
            call.respondRedirect("/question/$currentQuestion")
        } else {
            call.respondHtml {
                defaultHtml {
                    h1 { +"Waiting for Paul..." }
                }
            }
        }
    }
}

private fun Routing.setQuestionPage() {
    get("/setquestion/{questionId}") {
        val questionId: Int by call.parameters
        currentQuestion = questionId
        call.respondRedirect("/")
    }
}

fun HTML.defaultHtml(builder: BODY.() -> Unit) {

    head {
        title("Kuizz")
        styleLink("/style.css")
    }
    body {
        builder()
    }
}


*/

package examples.kuiz

data class Question(
    val description: String,
    val answers: List<String>
)

data class Answer(
    val questionId: Int,
    val answerId: Int
)

val questions: List<Question> = listOf(
    Question(
        "Which of the Kotlin functions on the right is equivalent to the Java function on the left?",
        listOf("A", "B", "C")
    ),
    Question(
        "Which of these is the correct way to call the function from the previous question?",
        listOf("A", "B", "C")
    ),
    Question(
        "What does this program output?",
        listOf(
            "[1, 2, 3, 2, 3, 4]",
            "[1, 2, 3, 4]",
            "[2, 3]"
        )
    ),
    Question(
        "Which of these programs will compile?",
        listOf(
            "main",
            "main1",
            "main2",
            "main3"
        )
    ),
    Question(
        "What does this program output?",
        listOf(
            "5",
            "1",
            "Run-time Exception",
            "Compile-time exception"
        )
    )
)
package examples.neuralnetwork

import math.Vector
import math.vectorOf

fun main() {
    val network = loadNetwork("network")
    testNetwork(network)
}

fun trainNetwork() {
    val network = Network(
        listOf(
            Layer(28 * 28, 30),
            Layer(30, 10)
        )
    )

    val trainData = readMnistDataset()

    network.train(trainData, 30, 10, 0.5)

    testNetwork(network)

    saveNetwork("network", network)
}


fun testNetwork(network: Network) {
    val testData = readMnistDataset(testData = true)

    val testCount = 100
    var accuracy = 0.0

    for (i in 0 until testCount) {
        val (x, y) = testData[i]
        val predicted = network.feedforward(x)

        if (y.maxIndex() == predicted.maxIndex()) accuracy += 1.0

        println(x.imageString())
        println("correct: ${y.maxIndex()}: $y")
        println("predicted: ${predicted.maxIndex()}: $predicted")
    }

    accuracy /= testCount
    println("accuracy: $accuracy")
}

fun Vector.maxIndex(): Int {
    return elements.withIndex().maxByOrNull { it.value }!!.index
}

fun xorNetwork() {
    val network = Network(
        listOf(
            Layer(2, 2),
            Layer(2, 1)
        )
    )

    val trainData = List(1000) { xorTestSet() }

    network.train(trainData, 100, 100, 1.0)

    println(network.feedforward(vectorOf(0.0, 0.0)))
    println(network.feedforward(vectorOf(1.0, 0.0)))
    println(network.feedforward(vectorOf(0.0, 1.0)))
    println(network.feedforward(vectorOf(1.0, 1.0)))
}

fun xorTestSet(): Pair<Vector, Vector> {
    val input = vectorOf(
        setOf(0.0, 1.0).random(),
        setOf(0.0, 1.0).random()
    )

    val output = if (input[0] != input[1]) vectorOf(1.0) else vectorOf(0.0)

    return Pair(input, output)
}
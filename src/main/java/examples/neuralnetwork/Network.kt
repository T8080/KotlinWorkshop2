package examples.neuralnetwork

import math.Matrix
import math.Vector
import math.randomMatrix
import math.randomVector
import kotlin.math.exp

typealias Dataset = List<Pair<Vector, Vector>>

data class Layer(
    val weights: Matrix,
    val biases: Vector
) {
    constructor(inSize: Int, outSize: Int) : this(
        randomMatrix(outSize, inSize),
        randomVector(outSize)
    )
}

data class Network(
    val layers: List<Layer>
) {
    fun feedforward(input: Vector): Vector =
        layers.fold(input) { activation, layer ->
            activation(layer.weights * activation + layer.biases)
        }

    fun train(
        trainData: List<Pair<Vector, Vector>>,
        epochs: Int,
        batchSize: Int,
        learningRate: Double
    ) {
        for (e in 0 until epochs) {
            println("epoch: $e")

            val batches: List<Dataset> = trainData.shuffled().windowed(batchSize)

            for (batch in batches) {
                val biasGradientSum = layers.map { Vector(it.biases.dimension) }
                val weightGradientSum = layers.map { Matrix(it.weights.rows, it.weights.columns) }

                for ((x, y) in batch) {
                    val (biasGradients, weightGradients) = backPropagateGradients(x, y)

                    for (l in layers.indices) {
                        biasGradientSum[l] += biasGradients[l]
                        weightGradientSum[l] += weightGradients[l]
                    }
                }

                for (l in layers.indices) {
                    layers[l].weights -= weightGradientSum[l] * (learningRate / batch.size)
                    layers[l].biases -= biasGradientSum[l] * (learningRate / batch.size)
                }
            }
        }
    }

    private fun backPropagateGradients(input: Vector, correct: Vector): Pair<List<Vector>, List<Matrix>> {
        val biasGradients: MutableList<Vector> = mutableListOf()
        val weightGradients: MutableList<Matrix> = mutableListOf()

        var activation = input
        val activations = mutableListOf(input)
        val zs = mutableListOf<Vector>()

        for ((w, b) in layers) {
            val z = w * activation + b
            zs.add(z)
            activation = activation(z)
            activations.add(activation)
        }

        val delta = costDerivative(activations.last(), correct) elementProduct activationDerivative(zs.last())

        biasGradients.add(0, delta)
        weightGradients.add(delta * activations[activations.size - 2].transposed())

        for (l in layers.size - 2 downTo 0) {
            val z = zs[l]
            val sp = activationDerivative(z)

            val delta = (layers[l + 1].weights.transposed() * biasGradients[0]) elementProduct sp

            biasGradients.add(0, delta)
            weightGradients.add(0, delta * activations[l].transposed())
        }

        return biasGradients to weightGradients
    }
}

fun costDerivative(output: Vector, correct: Vector): Vector =
    output.combineElements(correct) { x, y -> -(y / x) + (1.0 - y) / (1.0 - x) }

fun activation(z: Double): Double =
    1.0 / (1.0 + exp(-z))

fun activation(z: Vector): Vector =
    z.mapElements(::activation)

fun activationDerivative(z: Double): Double =
    activation(z) * (1 - activation(z))

fun activationDerivative(z: Vector): Vector =
    z.mapElements(::activationDerivative)
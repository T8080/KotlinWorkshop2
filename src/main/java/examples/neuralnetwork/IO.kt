package examples.neuralnetwork

import math.*
import math.Vector
import java.io.*
import java.util.*
import java.util.zip.*

const val trainImages = "datasets/train-images-idx3-ubyte.gz"
const val trainLabels = "datasets/train-labels-idx1-ubyte.gz"
const val testImages = "datasets/t10k-images-idx3-ubyte.gz"
const val testLabels = "datasets/t10k-labels-idx1-ubyte.gz"

fun readImages(path: String): Sequence<Vector> = sequence {
    val reader = DataInputStream(GZIPInputStream(FileInputStream(path)))

    with(reader) {
        val magic = readInt()
        val count = readInt()
        val rows = readInt()
        val columns = readInt()
        val size = rows * columns
        val buffer = ByteArray(size)

        repeat(count) {
            reader.readFully(buffer)
            val image = DoubleArray(buffer.size) { i -> buffer[i].toUByte().toDouble() / 255.0 }
            yield(Vector(image))
        }
    }
}

fun readLabels(path: String): Sequence<Int> = sequence {
    val reader = DataInputStream(GZIPInputStream(FileInputStream(path)))

    with(reader) {
        val magic = readInt()
        val count = readInt()

        repeat(count) {
            yield(readUnsignedByte())
        }
    }
}

fun readMnistDataset(testData: Boolean = false): Dataset {
    val images: Sequence<Vector> = readImages(if (testData) testImages else trainImages)

    val labels: Sequence<Vector> = readLabels(if (testData) testLabels else trainLabels)
        .map { label ->
            Vector(10) { i -> if (i == label) 1.0 else 0.0 }
        }

    return (images zip labels).toList()
}

fun test() {
    val w = File("").writer()

}

fun OutputStreamWriter.writeVector(vector: Vector) {
    write(vector.elements.size.toString())
    write(" ")
    for (value in vector.elements) {
        write(value.toString())
        write(" ")
    }
    write("\n")
}

fun Scanner.readVector(): Vector {
    val size = nextInt()
    val elements = DoubleArray(size)
    for (i in 0 until size) {
        elements[i] = nextDouble()
    }
    return Vector(elements)
}

fun OutputStreamWriter.writeMatrix(matrix: Matrix) {
    write(matrix.rows.toString())
    write(" ")
    write(matrix.columns.toString())
    write(" ")
    for (element in matrix.elements) {
        write(element.toString())
        write(" ")
    }
    write("\n")
}

fun Scanner.readMatrix(): Matrix {
    val rows = nextInt()
    val columns = nextInt()
    val elements = DoubleArray(rows * columns)

    for (i in elements.indices) {
        elements[i] = nextDouble()
    }

    return Matrix(rows, columns, elements)
}

fun OutputStreamWriter.writeLayer(layer: Layer) {
    writeMatrix(layer.weights)
    writeVector(layer.biases)
}

fun Scanner.readLayer() = Layer(
    readMatrix(),
    readVector()
)

fun OutputStreamWriter.writeNetwork(network: Network) {
    write(network.layers.size.toString())
    write(" ")
    for (layer in network.layers) {
        writeLayer(layer)
    }
}

fun Scanner.readNetwork(): Network {
    val layerCount = nextInt()
    val layers = List(layerCount) { readLayer() }
    return Network(layers)
}

fun saveNetwork(filename: String, network: Network) {
    File(filename).writer().use {
        it.writeNetwork(network)
    }
}

fun loadNetwork(filename: String): Network {
    return Scanner(File(filename)).use { it.readNetwork() }
}

fun main2() {
    val img = readImages(trainImages).toList()[2]
    val l = readLabels(trainLabels).toList()[2]
    println(img.imageString())
    println(l)

    val data = readMnistDataset()
    val (img1, label1) = data[10]

    println(img.imageString())
    println(label1)
}

fun Vector.imageString() = buildString {

    for (y in 0 until 28) {
        for (x in 0 until 28) {

            append("%2.0f".format((this@imageString[y * 28 + x] * 10.0)))
        }
        appendLine()
    }
}
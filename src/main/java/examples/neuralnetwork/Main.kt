package examples.neuralnetwork

import math.Matrix
import math.matrixOf
import math.toVector
import java.awt.*
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import java.awt.event.MouseMotionListener
import javax.swing.*
import kotlin.math.roundToInt

fun main() {
    val network = loadNetwork("network")

    val label = JLabel().apply {
        preferredSize = Dimension(80, 270)
        font = font.deriveFont(18.0f)
    }

    fun onDraw(image: Matrix) {
        val prediction = network.feedforward(image.elements.toVector())
        label.text = prediction.elements.withIndex()
            .sortedByDescending { it.value }
            .joinToString("\n", "<html><pre>", "</pre></html>") {
                "${it.index}: ${(it.value*100.0).roundToInt()}%"
            }
    }

    JFrame().apply {
        title = "Digit Classifier"
        layout = BoxLayout(contentPane, BoxLayout.LINE_AXIS)

        add(DrawPanel(::onDraw))
        add(Box.createHorizontalStrut(10))
        add(label)

        pack()
        isResizable = true
        isVisible = true
    }
}


class DrawPanel(
    val onDraw: (Matrix) -> Unit
) : JPanel() {
    private val pixelSize = 10
    private var image = Matrix(28, 28)
    private val brushKernel = matrixOf(
        3, 3,
        0.05, 0.2, 0.05,
        0.2,  0.3, 0.2,
        0.05, 0.2, 0.05
    )

    init {
        preferredSize = Dimension(27 * pixelSize, 27 * pixelSize)

        addMouseListener(object : MouseListener {
            override fun mousePressed(e: MouseEvent?) {
                image = Matrix(28, 28)
            }
            override fun mouseClicked(e: MouseEvent?) {}
            override fun mouseReleased(e: MouseEvent?) {}
            override fun mouseEntered(e: MouseEvent?) {}
            override fun mouseExited(e: MouseEvent?) {}
        })

        addMouseMotionListener(object : MouseMotionListener {
            override fun mouseDragged(e: MouseEvent) {
                draw(e.y, e.x)
            }
            override fun mouseMoved(e: MouseEvent) {}
        })

        repaint()
    }

    private fun draw(pixelX: Int, pixelY: Int) {
        val kernelWidth = brushKernel.columns
        val kernelHeight = brushKernel.rows
        val x = (pixelX.toDouble() / pixelSize).roundToInt()
        val y = (pixelY.toDouble() / pixelSize).roundToInt()

        for (kx in 0 until kernelWidth) {
            for (ky in 0 until kernelHeight) {
                val ix = x + kx - kernelWidth/2
                val iy = y + ky - kernelHeight/2

                if (ix in 0 until 28 && iy in 0 until 28) {
                    val kernelVal = brushKernel[ky, kx]
                    image[ix, iy] = minOf(1.0, image[ix, iy] + kernelVal)
                }
            }
        }

        repaint()
        onDraw(image)
    }

    override fun paintComponent(graphics: Graphics) {
        super.paintComponent(graphics)
        with(graphics) {
            color = Color.WHITE
            fillRect(0, 0, height, width)

            for (y in 0 until 28) {
                for (x in 0 until 28) {
                    val darkness = 1.0 - image[y, x]
                    val rgb = (darkness * 255).roundToInt()

                    color = Color(rgb, rgb, rgb)
                    fillRect(x * pixelSize, y * pixelSize, pixelSize, pixelSize)
                }
            }
        }
    }
}
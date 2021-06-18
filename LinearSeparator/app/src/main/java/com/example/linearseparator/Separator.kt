/*
*
*
*/

import java.util.*
import kotlin.collections.ArrayList
import kotlin.math.pow

class Separator(private val numFeatures: Int) {

    // Private variables
    private var weights: DoubleArray
    private var bias = 0.0
    private var learningRate = 0.001

    // Initialization
    init {
        val random = Random(0)
        val weights = DoubleArray(numFeatures)
        for (x in 0 until numFeatures) {
            weights[x] = random.nextFloat().toDouble()
        }
        this.weights = weights
    }

    // Loop to find hyperplane parameters
    fun fit(x: Array<DoubleArray>, y: DoubleArray, epochs: Int, batchSize: Int) {
        val batches = batch(x, y, batchSize)
        for (e in 0 until epochs) {
            for (batch in batches) {
                val gradients = ArrayList<Array<Any>>()
                for (pair in batch) {
                    val predictions = forwardPropagate(pair.first)
                    gradients.add(calculateGradients(pair.first, predictions, pair.second))
                    println((predictions - pair.second).pow(2))
                }
                optimizeParameters(gradients, learningRate)
            }
        }
    }

    // Perform prediction
    private fun forwardPropagate(x: DoubleArray): Double {
        return MathOps.dot(this.weights, x) + bias
    }

    // Compute gradients
    private fun calculateGradients(
            inputs: DoubleArray,
            predY: Double,
            targetY: Double
    ): Array<Any> {
        val dJ_dPred = meanSquaredErrorDerivative(predY, targetY)
        val dPred_dW = inputs
        val dJ_dW = MathOps.multiplyScalar(dPred_dW, dJ_dPred)
        val dJ_dB = dJ_dPred
        return arrayOf(dJ_dW, dJ_dB)
    }

    // Apply mini-batch gradient descent
    private fun optimizeParameters(gradients: ArrayList<Array<Any>>, learningRate: Double) {
        val weightGradientsList = ArrayList<DoubleArray>()
        for (gradient in gradients) {
            weightGradientsList.add(gradient[0] as DoubleArray)
        }
        val weightGradients =
                MathOps.multidimMean(weightGradientsList.toTypedArray()).toDoubleArray()

        val biasGradientsList = ArrayList<Double>()
        for (gradient in gradients) {
            biasGradientsList.add(gradient[1] as Double)
        }
        val biasGradients = (biasGradientsList.toTypedArray()).average()
        this.weights =
                MathOps.subtract(this.weights, MathOps.multiplyScalar(weightGradients, learningRate))
        this.bias = this.bias - (biasGradients * learningRate)
    }

    // Compute the least squares derivative
    private fun meanSquaredErrorDerivative(predY: Double, targetY: Double): Double {
        return 2 * (predY - targetY)
    }

    // Retrieve batch
    private fun batch(
            x: Array<DoubleArray>,
            y: DoubleArray,
            batchSize: Int
    ): List<List<Pair<DoubleArray, Double>>> {
        val data = x.zip(y.toTypedArray())
        return data.chunked(batchSize)
    }

}

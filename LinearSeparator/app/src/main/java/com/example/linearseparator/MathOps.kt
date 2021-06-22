class MathOps {
    companion object {

        // Compute dot product
        fun dot(a: DoubleArray, b: DoubleArray): Double {
            var dotProduct = 0.0
            for (i in 0 until a.size) {
                dotProduct += (a[i] * b[i])
            }
            return dotProduct
        }

        // Compute the difference between two arrays of double
        fun subtract(a: DoubleArray, b: DoubleArray): DoubleArray {
            val difference = DoubleArray(a.size)
            for (i in 0 until a.size) {
                difference[i] = (a[i] - b[i])
            }
            return difference
        }

        // Compute multiplication between scalar and array
        fun multiplyScalar(a: DoubleArray, k: Double): DoubleArray {
            val results = DoubleArray(a.size)
            for (i in 0 until a.size) {
                results[i] = a[i] * k
            }
            return results
        }

        // Compute multidimensional mean
        fun multidimMean(x: Array<DoubleArray>): Array<Double> {
            val mean = ArrayList<Double>()
            for (i in 0 until x[0].size) {
                var sum = 0.0
                for (array in x) {
                    sum += array[i]
                }
                mean.add(sum / x.size)
            }
            return mean.toTypedArray()
        }
    }
}

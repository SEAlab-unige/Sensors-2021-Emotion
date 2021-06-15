package com.example.linearseparator

import Separator
import android.annotation.SuppressLint
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    // Private constant
    private val TAG = "MainActivity"

    // Private variables
    private lateinit var meanTimeTextView: TextView
    private lateinit var stdDevTimeTextView: TextView
    private lateinit var featuresTextView: TextView
    private lateinit var samplesTextView: TextView
    private lateinit var classesTextView: TextView
    private lateinit var epochsTextView: TextView
    private lateinit var batchTextView: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Connect variables to widgets
        meanTimeTextView = findViewById<TextView>(R.id.avg_time_txtv)
        stdDevTimeTextView = findViewById<TextView>(R.id.std_time_txtv)
        featuresTextView = findViewById<TextView>(R.id.num_features_txtv)
        samplesTextView = findViewById<TextView>(R.id.num_samples_txtv)
        classesTextView = findViewById<TextView>(R.id.num_classes_txtv)
        epochsTextView = findViewById<TextView>(R.id.num_epochs_txtv)
        batchTextView = findViewById<TextView>(R.id.batch_size_txtv)
    }

    // Create random data matrix
    private fun createData(samplesNum: Int, featuresNum: Int) : Array<DoubleArray> {
        val random = Random(0)
        val x = Array(samplesNum) { DoubleArray(featuresNum) }
        for (i in 0 until samplesNum) {
            for (j in 0 until featuresNum)
                x[i][j] = random.nextDouble()
        }
        return x
    }

    // Create random labels vector
    private fun createLabels(samplesNum: Int, classesNum: Int) : DoubleArray {
        val random = Random(0)
        val y = DoubleArray(samplesNum)
        for (i in 0 until samplesNum) {
            y[i] = random.nextInt(classesNum).toDouble()
        }
        return y
    }

    // Compute solution of the optimization problem
    fun compute(view: View){
        // Retrieve values from widgets
        val samplesNum:Int = samplesTextView.text.toString().toInt()
        val featuresNum:Int = featuresTextView.text.toString().toInt()
        val classesNum:Int = classesTextView.text.toString().toInt()
        val epochs = epochsTextView.text.toString().toInt()
        val batchSize = batchTextView.text.toString().toInt()
        // Set number of trials
        val counter = 30
        val timeArray  = Array(counter) { i -> i * 0L }
        for (i in 0 until counter) {
            // Create data matrix
            val x = createData(samplesNum, featuresNum)
            // Create labels vector
            val y = createLabels(samplesNum, classesNum)
            // Create separator object
            val separator = Separator(featuresNum)
            // Start timer
            val startTime = SystemClock.uptimeMillis()
            // Fit separator
            separator.fit(x, y, epochs, batchSize)
            // Stop timer
            val endTime = SystemClock.uptimeMillis()
            // Fill time array
            timeArray[i] = (endTime - startTime)
        }
        // Display average time
        meanTimeTextView.setText("%.3f ms".format(timeArray.average()))
        // Display standard deviation
        stdDevTimeTextView.setText("%.3f ms".format(computeStandardDeviation(timeArray, timeArray.average())))
    }

    // Compute standard deviation
    private fun computeStandardDeviation(timeArray: Array<Long>, mean:Double): Double {
        var standardDeviation = 0.0
        for (num in timeArray) {
            standardDeviation += Math.pow(num - mean, 2.0)
        }
        return Math.sqrt(standardDeviation / timeArray.size)
    }
}
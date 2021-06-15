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
    private val TAG = "MainActivity"

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

        meanTimeTextView = findViewById<TextView>(R.id.avg_time_txtv)
        stdDevTimeTextView = findViewById<TextView>(R.id.std_time_txtv)
        featuresTextView = findViewById<TextView>(R.id.num_features_txtv)
        samplesTextView = findViewById<TextView>(R.id.num_samples_txtv)
        classesTextView = findViewById<TextView>(R.id.num_classes_txtv)
        epochsTextView = findViewById<TextView>(R.id.num_epochs_txtv)
        batchTextView = findViewById<TextView>(R.id.batch_size_txtv)
    }

    private fun createData(samplesNum: Int, featuresNum: Int) : Array<DoubleArray> {
        val random = Random(0)
        val x = Array(samplesNum) { DoubleArray(featuresNum) }
        for (i in 0 until samplesNum) {
            for (j in 0 until featuresNum)
                x[i][j] = random.nextDouble()
        }
        return x
    }

    private fun createLabels(samplesNum: Int, classesNum: Int) : DoubleArray {
        val random = Random(0)
        val y = DoubleArray(samplesNum)
        for (i in 0 until samplesNum) {
            y[i] = random.nextInt(classesNum).toDouble()
        }
        return y
    }

    fun compute(view: View){
        val samplesNum:Int = samplesTextView.text.toString().toInt()
        val featuresNum:Int = featuresTextView.text.toString().toInt()
        val classesNum:Int = classesTextView.text.toString().toInt()
        val epochs = epochsTextView.text.toString().toInt()
        val batchSize = batchTextView.text.toString().toInt()
        val counter = 30
        val timeArray  = Array(counter) { i -> i * 0L }
        for (i in 0 until counter) {
            val x = createData(samplesNum, featuresNum)
            val y = createLabels(samplesNum, classesNum)
            val regressor = Separator(featuresNum)
            val startTime = SystemClock.uptimeMillis()
            regressor.fit(x, y, epochs, batchSize)
            val endTime = SystemClock.uptimeMillis()
            timeArray[i] = (endTime - startTime)
        }
        meanTimeTextView.setText("%.3f ms".format(timeArray.average()))
        stdDevTimeTextView.setText("%.3f ms".format(computeStandardDeviation(timeArray, timeArray.average())))
    }

    fun computeStandardDeviation(timeArray: Array<Long>, mean:Double): Double {
        var standardDeviation = 0.0

        for (num in timeArray) {
            standardDeviation += Math.pow(num - mean, 2.0)
        }

        return Math.sqrt(standardDeviation / timeArray.size)
    }
}
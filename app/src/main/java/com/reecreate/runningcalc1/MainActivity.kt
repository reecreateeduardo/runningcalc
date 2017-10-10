package com.reecreate.runningcalc1

import android.annotation.SuppressLint
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.NumberPicker
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val time = findViewById<NumberPicker>(R.id.time) as TextView
        val distance = findViewById<NumberPicker>(R.id.distance) as TextView
        val pace = findViewById<NumberPicker>(R.id.pace) as TextView
        val pickerMinutes = numberPicker as NumberPicker
        val pickerSeconds = numberPickerSeconds as NumberPicker

        pickerMinutes.minValue = 0
        pickerMinutes.maxValue = 59
        pickerMinutes.wrapSelectorWheel = false

        pickerSeconds.minValue = 0
        pickerSeconds.maxValue = 60
        pickerSeconds.wrapSelectorWheel = false





        calculate.setOnClickListener {

            when {

                time.text.isEmpty() && (distance.text.isNotEmpty() && pace.text.isNotEmpty()) ->
                    calculatePace(null, distance.text.toString(), pace.text.toString())

                distance.text.isEmpty() && (time.text.isNotEmpty() && pace.text.isNotEmpty()) ->
                    calculatePace(time.text.toString(), null, pace.text.toString())

                pace.text.isEmpty() && (time.text.isNotEmpty() && distance.text.isNotEmpty()) ->
                    calculatePace(time.text.toString(), distance.text.toString(), null)

                else -> {
                    Toast.makeText(this, "Please check all fields",
                            Toast.LENGTH_SHORT).show()
                }
            }
        }

    }


    fun Double.format(digits: Int) = java.lang.String.format("%.${digits}f", this)


    @SuppressLint("SetTextI18n")
    private fun calculatePace(time: String?, distance: String?, pace: String?) {


        // KM Distance and KM Pace Button
        if (kmDistanceRadioBtn.isChecked && kmPaceRadioBtn.isChecked)
            when {
            // TIME: To calculate your time, fill in your distance and pace
                time == null -> {
                    val calculatedTime = distance!!.toLong() * timeToSeconds(pace.toString())
                    result.text = "The runner's time is ${secondsToTime(calculatedTime)}"
                }
            // DISTANCE: To calculate your distance, fill in your time and pace
                distance == null -> {
                    val calculatedDistance = (timeToSeconds(time).toDouble().div(timeToSeconds
                    (pace.toString()).toDouble())).format(2)
                    result.text = "Distance is $calculatedDistance KM"
                }
            // PACE: To calculate your pace, fill in your time and distance
                pace == null -> {
                    // Calculate Pace
                    val calculatedPace: Long = timeToSeconds(time).toLong() / distance.toLong()

                    Log.i("PaceSeconds", calculatedPace.toString() +
                            secondsToTime(calculatedPace))


                    result.text = "The runner's pace in KM is ${secondsToTime(calculatedPace)}"
                }
            }

        // Miles Distance and Miles Pace Button
        else if (milesDistanceRadioBtn.isChecked && milesPaceRadioBtn.isChecked)
            when {
            // TIME: To calculate your time, fill in your distance and pace
                time == null -> {
                    val calculatedTime = distance!!.toLong() * timeToSeconds(pace.toString())
                    result.text = "The runner's time is ${secondsToTime(calculatedTime)}"
                }
            // DISTANCE: To calculate your distance, fill in your time and pace
                distance == null -> {
                    val calculatedDistance = (timeToSeconds(time).toDouble().div(timeToSeconds
                    (pace.toString()).toDouble())).format(2)
                    result.text = "Distance is $calculatedDistance Miles"
                }
            // PACE: To calculate your pace, fill in your time and distance
                pace == null -> {
                    // Calculate Pace
                    val calculatedPace: Long = timeToSeconds(time).toLong() / distance.toLong()

                    Log.i("PaceSeconds", calculatedPace.toString() +
                            secondsToTime(calculatedPace))


                    result.text = "The runner's pace in miles is ${secondsToTime(calculatedPace)}"
                }

                // KM Distance and Miles Pace Button
            } else if(kmDistanceRadioBtn.isChecked && milesPaceRadioBtn.isChecked) {
            when {
            // TIME: To calculate your time, fill in your distance and pace
                time == null -> {
                    val calculatedTime = distance!!.toLong() * timeToSeconds(pace.toString())
                    result.text = "The runner's time is ${secondsToTime(calculatedTime)}"
                }
            // DISTANCE: To calculate your distance, fill in your time and pace
                distance == null -> {
                    val calculatedDistance = (timeToSeconds(time).toDouble().div(timeToSeconds
                    (pace.toString()).toDouble()) * 1.60934).format(2)
                    result.text = "Distance is $calculatedDistance Miles"
                }
            // PACE: To calculate your pace, fill in your time and distance
                pace == null -> {
                    // Calculate Pace
                    val calculatedPace: Long = timeToSeconds(time).toLong() / distance.toLong()

                    Log.i("PaceSeconds", calculatedPace.toString() +
                            secondsToTime(calculatedPace))


                    result.text = "The runner's pace in miles is ${secondsToTime(calculatedPace)}"
                }
            }

            // Miles Distance and KM Pace Button
        } else if(milesDistanceRadioBtn.isChecked && kmPaceRadioBtn.isChecked) {
            when {
            // TIME: To calculate your time, fill in your distance and pace
                time == null -> {
                    val calculatedTime = distance!!.toLong() * timeToSeconds(pace.toString())
                    result.text = "The runner's time is ${secondsToTime(calculatedTime)}"
                }
            // DISTANCE: To calculate your distance, fill in your time and pace
                distance == null -> {
                    val calculatedDistance = (timeToSeconds(time).toDouble().div(timeToSeconds
                    (pace.toString()).toDouble()) * 0.621371).format(2)
                    result.text = "Distance is $calculatedDistance Miles"
                }
            // PACE: To calculate your pace, fill in your time and distance
                pace == null -> {
                    // Calculate Pace
                    val calculatedPace: Long = timeToSeconds(time).toLong() / distance.toLong()

                    Log.i("PaceSeconds", calculatedPace.toString() +
                            secondsToTime(calculatedPace))


                    result.text = "The runner's pace in miles is ${secondsToTime(calculatedPace)}"
                }
            }


        } else{
            Toast.makeText(this, "Please choose one unit", Toast.LENGTH_SHORT).show()
        }


    }

    // Convert Time to Seconds
    @SuppressLint("SimpleDateFormat")
    private fun timeToSeconds(time: String): Long {
        val dateFormat = SimpleDateFormat("mm:ss")
        val reference = dateFormat.parse("00:00")
        val date = dateFormat.parse(time)

        return (date.time - reference.time) / 1000
    }

    // Convert Seconds to Time
    private fun secondsToTime(seconds: Long): String {
        val hours = seconds / 3600
        val minutes = (seconds % 3600) / 60
        val seconds = seconds % 60

        return String.format("%02d:%02d",minutes, seconds)
    }
}
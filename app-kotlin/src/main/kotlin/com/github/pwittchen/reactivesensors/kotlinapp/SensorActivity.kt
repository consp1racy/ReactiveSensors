package com.github.pwittchen.reactivesensors.kotlinapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.github.pwittchen.reactivesensors.library.ReactiveSensors
import io.reactivex.disposables.Disposable

abstract class SensorActivity : AppCompatActivity() {
  private lateinit var subscription: Disposable
  private lateinit var sensorHelper: SensorHelper
  protected var sensorType: Int = 0
  protected var sensorName: String = ""

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_sensor_sample)
    val textView = findViewById(R.id.sensor) as TextView
    sensorHelper = SensorHelper(ReactiveSensors(this), sensorType, sensorName, textView)

  }

  override fun onResume() {
    super.onResume()
    subscription = sensorHelper.createSubscription()
  }

  override fun onPause() {
    super.onPause()
    subscription.dispose()
  }
}

package com.github.pwittchen.reactivesensors.app;

import android.hardware.SensorEvent;
import android.widget.TextView;

import com.github.pwittchen.reactivesensors.library.ReactiveSensorEvent;
import com.github.pwittchen.reactivesensors.library.ReactiveSensorFilter;
import com.github.pwittchen.reactivesensors.library.ReactiveSensors;
import com.github.pwittchen.reactivesensors.library.SensorNotFoundException;

import java.util.Locale;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SensorHelper {
  private ReactiveSensors reactiveSensors;
  private int sensorType;
  private String sensorName;
  private TextView textViewForMessage;

  public SensorHelper(ReactiveSensors sensors, int type, String name, TextView textViewForMessage) {
    this.reactiveSensors = sensors;
    this.sensorType = type;
    this.sensorName = name;
    this.textViewForMessage = textViewForMessage;
  }

  public Disposable createSubscription() {
    return reactiveSensors.observeSensor(sensorType)
        .subscribeOn(Schedulers.computation())
        .filter(ReactiveSensorFilter.filterSensorChanged())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe(new Consumer<ReactiveSensorEvent>() {
          @Override
          public void accept(final ReactiveSensorEvent reactiveSensorEvent) throws Exception {
            SensorEvent event = reactiveSensorEvent.getSensorEvent();
            assert event != null;

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            String format = "%s readings:\n x = %f\n y = %f\n z = %f";
            String message = String.format(Locale.US, format, sensorName, x, y, z);
            textViewForMessage.setText(message);
          }
        }, new Consumer<Throwable>() {
          @Override
          public void accept(final Throwable throwable) throws Exception {
            if (throwable instanceof SensorNotFoundException) {
              textViewForMessage.setText("Sorry, your device doesn't have required sensor.");
            }
          }
        });
  }
}

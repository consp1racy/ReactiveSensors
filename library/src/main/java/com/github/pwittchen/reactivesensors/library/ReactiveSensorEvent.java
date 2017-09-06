/*
 * Copyright (C) 2015 Piotr Wittchen
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.pwittchen.reactivesensors.library;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public final class ReactiveSensorEvent {
  private SensorEvent sensorEvent;
  private Sensor sensor;
  private int accuracy = -1;

  ReactiveSensorEvent(@NonNull final SensorEvent sensorEvent) {
    this.sensorEvent = sensorEvent;
  }

  ReactiveSensorEvent(@NonNull final Sensor sensor, final int accuracy) {
    this.sensor = sensor;
    this.accuracy = accuracy;
  }

  @Nullable
  public SensorEvent getSensorEvent() {
    return sensorEvent;
  }

  @Nullable
  public Sensor getSensor() {
    return sensor;
  }

  public int getAccuracy() {
    return accuracy;
  }

  public boolean isSensorChanged() {
    return sensorEvent != null;
  }

  public boolean isAccuracyChanged() {
    return sensor != null && accuracy != -1;
  }
}

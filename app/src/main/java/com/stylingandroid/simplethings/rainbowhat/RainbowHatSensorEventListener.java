package com.stylingandroid.simplethings.rainbowhat;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;

import com.stylingandroid.simplethings.Consumer;

class RainbowHatSensorEventListener implements SensorEventListener {
    private final Consumer consumer;

    RainbowHatSensorEventListener(Consumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        consumer.setValue(event.values[0]);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        //NO-OP
    }
}

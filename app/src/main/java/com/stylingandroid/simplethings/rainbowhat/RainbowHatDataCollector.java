package com.stylingandroid.simplethings.rainbowhat;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.hardware.SensorManager.DynamicSensorCallback;

import com.google.android.things.contrib.driver.bmx280.Bmx280SensorDriver;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.stylingandroid.simplethings.Consumer;
import com.stylingandroid.simplethings.DataCollector;

import java.io.IOException;

import static android.content.Context.SENSOR_SERVICE;

class RainbowHatDataCollector implements DataCollector {
    private final Context context;
    private final RainbowHatFactory factory;

    private Bmx280SensorDriver sensorDriver = null;
    private DynamicSensorCallback dynamicSensorCallback;
    private SensorEventListener temperatureListener;
    private SensorEventListener pressureListener;

    RainbowHatDataCollector(Context context, RainbowHatFactory factory) {
        this.context = context;
        this.factory = factory;
    }

    @Override
    public void register(final Consumer temperatureConsumer, final Consumer pressureConsumer) {
        registerSensorListeners(temperatureConsumer, pressureConsumer);
        registerSensors();
    }

    private void registerSensorListeners(final Consumer temperatureConsumer, final Consumer pressureConsumer) {
        final SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
        dynamicSensorCallback = createDynamicSensorCallback(temperatureConsumer, pressureConsumer, sensorManager);
        sensorManager.registerDynamicSensorCallback(dynamicSensorCallback);
    }

    private DynamicSensorCallback createDynamicSensorCallback(final Consumer temperatureConsumer,
                                                              final Consumer pressureConsumer,
                                                              final SensorManager sensorManager) {
        return new DynamicSensorCallback() {
            @Override
            public void onDynamicSensorConnected(Sensor sensor) {
                super.onDynamicSensorConnected(sensor);
                if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    temperatureListener = factory.getSensorEventListener(temperatureConsumer);
                    sensorManager.registerListener(temperatureListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
                if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                    pressureListener = factory.getSensorEventListener(pressureConsumer);
                    sensorManager.registerListener(pressureListener, sensor, SensorManager.SENSOR_DELAY_NORMAL);
                }
            }

            @Override
            public void onDynamicSensorDisconnected(Sensor sensor) {
                if (sensor.getType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
                    sensorManager.unregisterListener(temperatureListener);
                }
                if (sensor.getType() == Sensor.TYPE_PRESSURE) {
                    sensorManager.unregisterListener(pressureListener);
                }
                super.onDynamicSensorDisconnected(sensor);
            }
        };
    }

    private void registerSensors() {
        try {
            sensorDriver = RainbowHat.createSensorDriver();
            sensorDriver.registerTemperatureSensor();
            sensorDriver.registerPressureSensor();
        } catch (IOException e) {
            sensorDriver = null;
            e.printStackTrace();
        }
    }

    @Override
    public void unregister() {
        try {
            final SensorManager sensorManager = (SensorManager) context.getSystemService(SENSOR_SERVICE);
            sensorManager.unregisterDynamicSensorCallback(dynamicSensorCallback);
            sensorDriver.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

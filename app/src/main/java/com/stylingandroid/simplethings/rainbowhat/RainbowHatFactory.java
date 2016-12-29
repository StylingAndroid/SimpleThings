package com.stylingandroid.simplethings.rainbowhat;

import android.content.Context;
import android.hardware.SensorEventListener;

import com.stylingandroid.simplethings.Consumer;
import com.stylingandroid.simplethings.DataCollector;
import com.stylingandroid.simplethings.Factory;

public class RainbowHatFactory implements Factory {
    private final Context context;

    public RainbowHatFactory(Context context) {
        this.context = context;
    }

    @Override
    public DataCollector getDataCollector() {
        return new RainbowHatDataCollector(context, this);
    }

    @Override
    public Consumer getTemperatureConsumer() {
        return new RainbowHatTemperatureDisplay();
    }

    SensorEventListener getSensorEventListener(Consumer consumer) {
        return new RainbowHatSensorEventListener(consumer);
    }
}

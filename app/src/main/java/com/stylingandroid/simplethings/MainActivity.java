package com.stylingandroid.simplethings;

import android.app.Activity;
import android.os.Bundle;

import com.stylingandroid.simplethings.rainbowhat.RainbowHatFactory;

public class MainActivity extends Activity {

    private DataCollector dataCollector;
    private Consumer temperatureConsumer;
    private Consumer pressureConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RainbowHatFactory factory = new RainbowHatFactory(this);
        dataCollector = factory.getDataCollector();
        temperatureConsumer = factory.getTemperatureConsumer();
        pressureConsumer = factory.getPressureConsumer();
        temperatureConsumer.onCreate();
        pressureConsumer.onCreate();
        dataCollector.register(temperatureConsumer, pressureConsumer);
    }

    @Override
    protected void onDestroy() {
        dataCollector.unregister();
        temperatureConsumer.onDestroy();
        pressureConsumer.onDestroy();

        super.onDestroy();
    }
}

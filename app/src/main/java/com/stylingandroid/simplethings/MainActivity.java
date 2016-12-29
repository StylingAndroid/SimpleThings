package com.stylingandroid.simplethings;

import android.app.Activity;
import android.os.Bundle;

import com.stylingandroid.simplethings.rainbowhat.RainbowHatFactory;

public class MainActivity extends Activity {

    private DataCollector dataCollector;
    private Consumer temperatureConsumer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        RainbowHatFactory factory = new RainbowHatFactory(this);
        dataCollector = factory.getDataCollector();
        temperatureConsumer = factory.getTemperatureConsumer();
        temperatureConsumer.onCreate();
        dataCollector.register(temperatureConsumer);
    }

    @Override
    protected void onDestroy() {
        dataCollector.unregister(temperatureConsumer);
        temperatureConsumer.onDestroy();

        super.onDestroy();
    }
}

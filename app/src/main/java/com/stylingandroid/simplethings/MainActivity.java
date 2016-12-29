package com.stylingandroid.simplethings;

import android.app.Activity;
import android.os.Bundle;

import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.google.android.things.pio.Gpio;

import java.io.IOException;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Gpio led = RainbowHat.openLed(RainbowHat.LED_RED);
            led.setValue(true);
            led.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finish();
    }

}

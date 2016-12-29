package com.stylingandroid.simplethings.rainbowhat;

import com.google.android.things.contrib.driver.ht16k33.AlphanumericDisplay;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.stylingandroid.simplethings.Consumer;

import java.io.IOException;
import java.util.Locale;

class RainbowHatTemperatureDisplay implements Consumer {
    private static final String EMPTY_STRING = "";
    private static final String TEMPERATURE_FORMAT_STRING = "%02.0fC";
    private AlphanumericDisplay display;

    @Override
    public void onCreate() {
        try {
            display = RainbowHat.openDisplay();
            display.display(EMPTY_STRING);
            display.setEnabled(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (display != null) {
            try {
                display.display(EMPTY_STRING);
                display.setEnabled(false);
                display.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setValue(float temperature) {
        try {
            String string = String.format(Locale.UK, TEMPERATURE_FORMAT_STRING, temperature);
            display.display(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

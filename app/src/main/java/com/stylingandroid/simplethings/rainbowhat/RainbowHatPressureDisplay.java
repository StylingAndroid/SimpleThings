package com.stylingandroid.simplethings.rainbowhat;

import android.graphics.Color;

import com.google.android.things.contrib.driver.apa102.Apa102;
import com.google.android.things.contrib.driver.rainbowhat.RainbowHat;
import com.stylingandroid.simplethings.Consumer;

import java.io.IOException;

class RainbowHatPressureDisplay implements Consumer {
    private static final int LED_COUNT = 7;
    private static final int SCALE_FACTOR = 10;
    private static final int MIN_PRESSURE = 97;
    private static final int BRIGHTNESS = 1;

    private Apa102 ledStrip;

    private int[] colours = new int[LED_COUNT];

    private int lastPressure = -1;

    @Override
    public void onCreate() {
        try {
            ledStrip = new Apa102(RainbowHat.BUS_LEDSTRIP, Apa102.Mode.BGR, Apa102.Direction.REVERSED);
            ledStrip.setBrightness(BRIGHTNESS);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void setValue(float pressure) {
        int newPressure = limit(((int) pressure / SCALE_FACTOR) - MIN_PRESSURE);
        setPressure(newPressure);
        if (newPressure != lastPressure) {
            lastPressure = newPressure;
        }
    }

    private int limit(int value) {
        return Math.max(Math.min(value, LED_COUNT), 0);
    }

    private void setPressure(int newPressure) {
        resetColours();
        colours[newPressure] = Color.GREEN;
        if (newPressure > 0 && lastPressure < newPressure) {
            colours[newPressure - 1] = Color.YELLOW;
        } else if (newPressure < colours.length - 1) {
            colours[newPressure + 1] = Color.YELLOW;
        }
        setLeds();
    }

    private void resetColours() {
        for (int i = 0; i < colours.length; i++) {
            colours[i] = Color.BLACK;
        }
    }

    private void setLeds() {
        try {
            ledStrip.write(colours);
            ledStrip.write(colours);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        if (ledStrip != null) {
            try {
                resetColours();
                setLeds();
                ledStrip.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
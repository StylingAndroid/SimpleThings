package com.stylingandroid.simplethings;

public interface Factory {
    DataCollector getDataCollector();

    Consumer getTemperatureConsumer();
}

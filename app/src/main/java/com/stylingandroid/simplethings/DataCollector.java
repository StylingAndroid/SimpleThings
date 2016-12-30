package com.stylingandroid.simplethings;

public interface DataCollector {
    void register(Consumer temperatureConsumer, Consumer pressureConsumer);

    void unregister();
}

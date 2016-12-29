package com.stylingandroid.simplethings;

public interface DataCollector {
    void register(Consumer temperatureConsumer);

    void unregister(Consumer temperatureConsumer);
}

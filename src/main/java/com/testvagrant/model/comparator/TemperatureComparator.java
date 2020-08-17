package com.testvagrant.model.comparator;

import com.testvagrant.config.PropertyFileReader;
import com.testvagrant.model.WeatherInfo;

import java.io.File;
import java.util.Comparator;
import java.util.Properties;

import static java.lang.String.format;

public class TemperatureComparator implements Comparator<WeatherInfo> {
    private final Properties test_data = new PropertyFileReader(new File(format("%s/src/test/resources/test_data/data.properties",
            System.getProperty("user.dir")))).getPropertyFile();

    @Override
    public int compare(WeatherInfo o1, WeatherInfo o2) {
        float humidity1 = o1.getHumidity();
        float humidity2 = o2.getHumidity();
        float humidityVariance = Float.parseFloat(test_data.getProperty("tempVariance"));

        float absHumidity = Math.abs(humidity1 - humidity2);

        return (absHumidity >= 0 && absHumidity <= humidityVariance) ? 0 : 1;
    }
}

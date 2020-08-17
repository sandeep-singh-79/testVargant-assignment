package com.testvagrant.model.comparator;

import com.testvagrant.config.PropertyFileReader;
import com.testvagrant.model.WeatherInfo;

import java.io.File;
import java.util.Comparator;
import java.util.Properties;

import static java.lang.String.format;

public class WindSpeedComparator implements Comparator<WeatherInfo> {
    private final Properties test_data = new PropertyFileReader(new File(format("%s/src/test/resources/test_data/data.properties",
            System.getProperty("user.dir")))).getPropertyFile();

    @Override
    public int compare(WeatherInfo o1, WeatherInfo o2) {
        float windSpeed1 = o1.getWindSpeed();
        float windSpeed2 = o2.getWindSpeed();
        float windSpeedVariance = Float.parseFloat(test_data.getProperty("windVariance"));

        float absWindSpeed = Math.abs(windSpeed1 - windSpeed2);
        return 0 <= absWindSpeed && absWindSpeed <= windSpeedVariance ? 0 : 1;
    }
}

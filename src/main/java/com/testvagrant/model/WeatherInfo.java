package com.testvagrant.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class WeatherInfo {
    private String cityName;
    private List<String> condition;
    private float windSpeed;
    private float windGust;
    private float tempDegrees;
    private float tempFahrenheit;
    private int humidity;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        WeatherInfo that = (WeatherInfo) o;

        return new EqualsBuilder()
                       .append(windSpeed, that.windSpeed)
                       .append(windGust, that.windGust)
                       .append(tempDegrees, that.tempDegrees)
                       .append(tempFahrenheit, that.tempFahrenheit)
                       .append(humidity, that.humidity)
                       .append(cityName, that.cityName)
                       .append(condition, that.condition)
                       .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                       .append(cityName)
                       .append(condition)
                       .append(windSpeed)
                       .append(windGust)
                       .append(tempDegrees)
                       .append(tempFahrenheit)
                       .append(humidity)
                       .toHashCode();
    }

    @Override
    public String toString() {
        return "WeatherInfo{" +
                       "cityName='" + cityName + '\'' +
                       ", condition=" + condition +
                       ", windSpeed=" + windSpeed +
                       ", windGust=" + windGust +
                       ", tempDegrees=" + tempDegrees +
                       ", tempFahrenheit=" + tempFahrenheit +
                       ", humidity=" + humidity +
                       '}';
    }
}

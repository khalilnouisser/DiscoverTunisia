package com.discover.tunisia.discover.entities;

import java.io.Serializable;
import java.util.List;

public class Current implements Serializable {
    private int temperature;
    private int weather_code;
    private List<String> weather_icons;
    private List<String> weather_descriptions;

    public int getTemperature() {
        return temperature;
    }

    public void setTemperature(int temperature) {
        this.temperature = temperature;
    }

    public List<String> getWeather_icons() {
        return weather_icons;
    }

    public void setWeather_icons(List<String> weather_icons) {
        this.weather_icons = weather_icons;
    }

    public List<String> getWeather_descriptions() {
        return weather_descriptions;
    }

    public void setWeather_descriptions(List<String> weather_descriptions) {
        this.weather_descriptions = weather_descriptions;
    }

    public int getWeather_code() {
        return weather_code;
    }

    public void setWeather_code(int weather_code) {
        this.weather_code = weather_code;
    }
}


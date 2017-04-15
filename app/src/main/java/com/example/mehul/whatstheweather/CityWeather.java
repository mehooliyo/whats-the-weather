package com.example.mehul.whatstheweather;

import org.json.JSONException;
import org.json.JSONObject;

public class CityWeather {

    private String name;
    private String country;

    private String weatherMain;
    private String description;

    private int temp;
    private int sunrise;
    private int sunset;

    public CityWeather(JSONObject json) throws JSONException {
        name = json.getString("name");

        JSONObject main = json.getJSONObject("main");
        temp = main.getInt("temp");

        JSONObject weather = json.getJSONArray("weather").getJSONObject(0);
        weatherMain = weather.getString("main");
        description = weather.getString("description");

        JSONObject sys = json.getJSONObject("sys");
        country = sys.getString("country");
        sunrise = sys.getInt("sunrise");
        sunset = sys.getInt("sunset");
    }

    public String getName(){
        return name;
    }

    public String getCountry(){
        return country;
    }

    public String getWeather(){
        return weatherMain;
    }

    public String getTemperature(boolean isCelsius){
        Double newTemp;
        if(!isCelsius){
            newTemp = temp * (9/5) - 459.67;
        } else {
            newTemp = temp - 273.15;
        }

        char celOrFah = isCelsius ? 'C' : 'F';

        return String.valueOf(newTemp.intValue())+'°'+celOrFah;
    }

    public String getDescription(){
        return description;
    }

    public String toString(){
        return String.format("%s, %s - %s", name, country, weatherMain);
    }

}

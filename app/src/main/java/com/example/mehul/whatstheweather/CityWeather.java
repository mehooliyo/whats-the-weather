package com.example.mehul.whatstheweather;

import org.json.JSONException;
import org.json.JSONObject;

public class CityWeather {

    private String name;
    private String country;

    private int temp;
    private int sunrise;
    private int sunset;

    public CityWeather(JSONObject json) throws JSONException {
        name = json.getString("name");

        JSONObject main = json.getJSONObject("main");
        temp = main.getInt("temp");

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

}
